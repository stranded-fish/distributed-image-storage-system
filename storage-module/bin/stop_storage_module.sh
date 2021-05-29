#!/usr/bin/env bash

if ! ps -ef | grep cn.yulan.storage.module.server.ServerMain | grep -v grep | awk '{print $2}' | xargs kill -9 1>/dev/null 2>&1; then
  echo "Failed to stop the storage module server (server not running)"
fi