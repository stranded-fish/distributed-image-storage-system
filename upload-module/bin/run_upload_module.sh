#!/usr/bin/env bash

if [ $# -ne 2 ]; then
    echo "Usage:"
    echo "  ./run_upload_module.sh PORT CLUSTER"
    echo
    echo "Examples:"
    echo "  ./run_upload_module.sh 8080 list://127.0.0.1:8051,127.0.0.1:8052,127.0.0.1:8053  # Listening on port 8080 with cluster IP and port list"
    exit
fi

PORT=$1
CLUSTER=$2

cd ..
mvn clean package

cd ./target || exit
RUN_JAVA="$JAVA_HOME/bin/java"
nohup "$RUN_JAVA" -jar upload-module-0.0.1-SNAPSHOT.jar --server.port="$PORT" --raft.ipPorts="$CLUSTER" &
cd ../bin || exit