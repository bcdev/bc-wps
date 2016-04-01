#!/bin/bash

CATALINA_HOME=/opt/tomcat
SERVICE_NAME=bc-tomcat
RUN_AS=tomcat

sudo service bc-tomcat stop
sudo rm -rf $CATALINA_HOME/webapps/bc-wps*
sudo -u $RUN_AS cp server.xml $CATALINA_HOME/conf
sudo -u $RUN_AS cp bc-wps.war $CATALINA_HOME/webapps
sudo service bc-tomcat start
