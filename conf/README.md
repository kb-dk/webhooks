# Configuration folder

The `conf/`-folder contains files intended for local `jetty:run`, Tomcat deployment as well as
OpenShift deployment.

# webhooks-base.yaml

This contains the structure and keys for all core properties of the application. Where possible, it will also contains
values, but sensitive information such as machine names and user/passwords MUST NOT be stated directly in this config. 

The behaviour config is normally controlled by developers and is part of the code repository.
It will be automatically merged with the 'environment' or 'local' configs when accessed through the
application config system.

# webhooks-environment.yaml

This config contains sensitive data: Servers, usernames, passwords etc.
It will be automatically merged with the 'base' config when accesses through the application config system.

In the code repository, this file is called `webhooks-environment.yaml.sample` and should not contain real
values, only the configuration structure. Due to the ".sample" extension, it will not be loaded by
`ServiceConfig`.

When applied to production, the file should be copied, adjusted and renamed to `webhooks-environment.yaml`
The new file `webhooks-environment.yaml` should NOT be added to the code repository!

# webhooks-local.yaml

This config contains developer specific overrides, such as read-only account credentials and test server locations.

If will be automatically merged with the `webhooks-base.yaml` upon application start.
Values in the `webhooks-local.yaml` files takes precedence over the 'base' file as the files are sorted
alphanumerically by name and applied in order.

This config is controlled by the individual developer and is not part of the code repository.

# webhooks-logback.xml

Logback configuration that outputs to file, as expected when running under Tomcat.

# ocp/ folder

Configurations intended for OpenShift.
