#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo "Usage:"
    echo "  ./run_access_module.sh PORT"
    echo
    echo "Examples:"
    echo "  ./run_access_module.sh 8001 # Listening on port 8001"
    exit
fi

PORT=$1

cd ..
mvn clean package

cd ./target || exit
RUN_JAVA="$JAVA_HOME/bin/java"
nohup "$RUN_JAVA" -jar access-module-0.0.1-SNAPSHOT.jar --server.port="$PORT" &
cd ../bin || exit