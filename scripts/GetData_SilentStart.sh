#!/bin/bash


SHELL_PARENT="$(dirname "$(readlink -fm "$0")")"

./KillNodeFetcher.sh > /dev/null 2>&1

cd ~/node_getdata
printf "Starting Node program with pid="
nohup node GetData.js >/dev/null 2>&1 &

cd "$SHELL_PARENT"
echo $! > ".node_proc.pid"
printf "%s\n" $!
