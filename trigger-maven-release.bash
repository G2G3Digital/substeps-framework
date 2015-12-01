#!/usr/bin/env bash

##
## To be run on a local machine
##
mvn --batch-mode --settings travis/settings.xml release:clean release:prepare
