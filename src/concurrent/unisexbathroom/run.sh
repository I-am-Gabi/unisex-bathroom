#!/bin/bash
cd src

javac unissexbathroom/*.java 

jar cfm ../UnisexBathroom.jar unissexbathroom/*.class

java -jar UnisexBathroom.jar