#!/usr/bin/env bash

if ! ps -ef | grep upload-module-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9 1>/dev/null 2>&1; then
  echo "Failed to stop the upload module server (server not running)"
fi