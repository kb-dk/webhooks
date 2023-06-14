package dk.kb.webhooks.webservice;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.URL;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import dk.kb.webhooks.config.ServiceConfig;
import dk.kb.util.BuildInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener to handle the various setups and configuration sanity checks that can be carried out at when the
 * context is deployed/initalized.
 */

public class ContextListener implements ServletContextListener {
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * On context initialisation this
     * i) Initialises the logging framework (logback).
     * ii) Initialises the configuration class.
     * @param sce context provided by the web server upon initialization.
     * @throws java.lang.RuntimeException if anything at all goes wrong.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
            if (mxBean.getInputArguments().stream().noneMatch(arg -> arg.startsWith("-Xmx"))) {
                log.warn("Java heap size (-Xmx option) is not specified. " +
                         "In stage or production this is almost always an error");
            }

            log.info("Initializing service {} {} build {} using Java {} with max heap {}MB on machine {}",
                     BuildInfoManager.getName(), BuildInfoManager.getVersion(), BuildInfoManager.getBuildTime(),
                     System.getProperty("java.version"), Runtime.getRuntime().maxMemory()/1048576,
                     InetAddress.getLocalHost().getHostName());
            InitialContext ctx = new InitialContext();
            String configFile = (String) ctx.lookup("java:/comp/env/application-config");
            //TODO this should not refer to something in template. Should we perhaps use reflection here?
            ServiceConfig.getInstance().initialize(configFile);
            String logConfig = "N/A";
            try {
                LoggerContext loggerContext = ((ch.qos.logback.classic.Logger)log).getLoggerContext();
                URL mainURL = ConfigurationWatchListUtil.getMainWatchURL(loggerContext);
                logConfig = mainURL == null ? "N/A" : mainURL.toString();
            } catch (Exception e) {
                log.warn("Unable to determing Logback configuration. Probably causes are missing logback.xml or " +
                         "third party library include of slf4j-simple");
            }

            log.info("ServiceConfig initialized with autoupdate={} from config glob '{}', Logback config in '{}'",
                     ServiceConfig.getInstance().isAutoUpdating(), configFile, logConfig);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup settings", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load settings", e);
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("contextDestroyed called: Shutting down ServiceConfig");
        ServiceConfig.getInstance().shutdown();
        log.info("Service destroyed");
    }

}
