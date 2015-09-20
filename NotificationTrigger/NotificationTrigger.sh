#!/bin/sh

DIR="/home/msf/Broker/CacheUpdater/CacheUpdater-0.0.1-SNAPSHOT"
cd $DIR
CLASSPATH="/home/msf/Libraries/java/bin/java -classpath $DIR/CacheUpdater-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.msf.mosl.cacheUpdater.CacheUpdater "
OPT="-c  $DIR/config/config.properties -l $DIR/config/log4j.properties"

$CLASSPATH $OPT

exit 0
