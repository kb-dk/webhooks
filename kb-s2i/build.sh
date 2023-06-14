#!/usr/bin/env bash

cd /tmp/src

cp -rp -- /tmp/src/target/webhooks-*.war "$TOMCAT_APPS/webhooks.war"
cp -- /tmp/src/conf/ocp/webhooks.xml "$TOMCAT_APPS/webhooks.xml"

export WAR_FILE=$(readlink -f "$TOMCAT_APPS/webhooks.war")
