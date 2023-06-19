#!/bin/sh

mvn clean package -DskipTests
mv target/webhooks*.war target/webhooks.war

scp target/webhooks.war almahook@devel12.statsbiblioteket.dk:/home/almahook/services/webapps/
scp conf/webhooks-logback.xml almahook@devel12.statsbiblioteket.dk:/home/almahook/services/conf
scp conf/webhooks-base.yaml almahook@devel12.statsbiblioteket.dk:/home/almahook/services/conf

echo "webhooks deployed to devel12"
