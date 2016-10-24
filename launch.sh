#!/usr/bin/env bash
time ( date '+%X.%3N Sub-shell is starting JVM'; java -jar target/scala-2.11/test_scala_sensor_processing-assembly-1.0.jar; date '+%X.%3N Sub-shell exiting' )
