#!/bin/bash
cd src

javac concurrent/unisexbathroom/*.java 

jar -cfm ../unisexbathroom.jar ../Manifest.mf concurrent/unisexbathroom/*.class

java -jar ../unisexbathroom.jar
