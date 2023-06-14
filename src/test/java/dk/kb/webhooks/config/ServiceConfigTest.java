package dk.kb.webhooks.config;

import dk.kb.util.Resolver;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
class ServiceConfigTest {

    /*
     * This unit-test probably fails when the template is applied and a proper project is taking form.
     * That is okay. It is only here to serve as a temporary demonstration of unit-testing and configuration.
     */
    @Test
    void loadConfigTest() throws IOException {
        // Pretty hacky, but it is only a sample unit test
        Path knownFile = Path.of(Resolver.resolveURL("logback-test.xml").getPath());
        String projectRoot = knownFile.getParent().getParent().getParent().toString();

        Path sampleEnvironmentSetup = Path.of(projectRoot, "conf/webhooks-base.yaml");
        assertTrue(Files.exists(sampleEnvironmentSetup),
                   "The base setup is expected to be present at '" + sampleEnvironmentSetup + "'");

        ServiceConfig.getInstance().initialize(projectRoot + File.separator + "conf" + File.separator + "webhooks*.yaml");

        // The only thing we test here is that configuration loading succeeds
        // This is because each project has independent configuration entries with no required overlap

        // Defined in behaviour
        //assertEquals(10, ServiceConfig.getConfig().getInteger("config.limits.min"));

        // Real value in environment
        //assertEquals("real_dbpassword", ServiceConfig.getConfig().getString("config.backend.password"));
    }

    @Tag("slow")
    @Test
    void autoLoadTest() throws IOException, InterruptedException {
        final ServiceConfig sc = ServiceConfig.getInstance();

        final String CONF0 = "config:\n  autoupdate:\n    enabled: true\n    intervalms: 100\n  somevalue: 0";
        final String CONF1 = "config:\n  autoupdate:\n    enabled: true\n    intervalms: 100\n  somevalue: 1";
        final String CONF2 = "config:\n  autoupdate:\n    enabled: false\n    intervalms: 100\n  somevalue: 2";
        final AtomicInteger reloads = new AtomicInteger(0);
        final String VALUE_KEY = ".config.somevalue";

        // Initial state
        File conf = File.createTempFile("webhooks_config_", ".yaml");
        FileUtils.writeStringToFile(conf, CONF0, StandardCharsets.UTF_8);
        sc.registerObserver(yaml -> reloads.incrementAndGet());
        final int baseReloads = reloads.get(); // ServiceConfight might have been initialized already so keep track

        ServiceConfig.getInstance().initialize(conf.toString());
        assertTrue(sc.isAutoUpdating(), "Config should be auto updating");
        assertEquals(baseReloads+1, reloads.get(), "After init, reloads should be " + (baseReloads+1));
        assertEquals(0, ServiceConfig.getConfig().getInteger(VALUE_KEY), "Initial value should match");

        Thread.sleep(200);
        assertEquals(baseReloads+1, reloads.get(), "After first sleep, reloads should still be correct");

        // Update config file with same content: Should not trigger anything
        FileUtils.writeStringToFile(conf, CONF0, StandardCharsets.UTF_8);
        Thread.sleep(200);
        assertEquals(baseReloads+1, reloads.get(), "After second sleep, reloads should still be correct (new config is identical to old)");

        // Update config with new content
        FileUtils.writeStringToFile(conf, CONF1, StandardCharsets.UTF_8);
        Thread.sleep(200);
        assertEquals(baseReloads+2, reloads.get(), "After third sleep, reloads should still be correct");
        assertEquals(1, ServiceConfig.getConfig().getInteger(VALUE_KEY), "First change value should match");

        Thread.sleep(200);
        assertEquals(baseReloads+2, reloads.get(), "After fourth sleep, reloads should still be 1 (no change at all)");

        // Second update and disabling of auto-update
        FileUtils.writeStringToFile(conf, CONF2, StandardCharsets.UTF_8);
        Thread.sleep(200);
        assertEquals(baseReloads+3, reloads.get(), "After fifth sleep, reloads should be correct");
        assertEquals(2, ServiceConfig.getConfig().getInteger(VALUE_KEY), "Second change value should match");
        assertFalse(sc.isAutoUpdating(), "Config should have auto updating turned off");
    }

    @Tag("fast")
    @Test
    void systemProperties() throws IOException {
        initConfig();

        {
            String homeQuote = ServiceConfig.getConfig().getString("config.userhome");
            assertFalse(homeQuote.contains("$"),
                        "The value for 'config.userhome' should be expanded");
        }
        {
            Integer fallback = ServiceConfig.getConfig().getInteger("config.fallback");
            assertEquals(87, fallback,
                         "Expanding a non-existing property with fallback should yield the fallback");
        }
        {
            String dbURL = ServiceConfig.getConfig().getString("config.backend.db");
            assertEquals("http://localhost:12345/mydb", dbURL,
                         "Nested fallback should work");
        }
        {
            try {
                ServiceConfig.getConfig().getString("config.backend.password");
                fail("An exception should be thrown when a property cannot be expanded");
            } catch (Exception e) {
                // Expected
            }
        }
    }

    @Tag("fast")
    @Test
    void override() throws IOException {
        initConfig();

        {
            String altDBURL = ServiceConfig.getConfig().getString("config.alternative.db");
            // This is http://localhost:23456/mydb2 in webhooks-base.yaml and overwritten in webhooks-local.yaml
            assertEquals("http://testmachine:8090/foobar/", altDBURL,
                         "File bases overriding ov config values should work");
        }
    }

    private void initConfig() throws IOException {
        Path knownFile = Path.of(Resolver.resolveURL("logback-test.xml").getPath());
        String projectRoot = knownFile.getParent().getParent().getParent().toString();
        ServiceConfig.getInstance().initialize(projectRoot + File.separator + "conf" + File.separator + "webhooks*.yaml");
    }

}
