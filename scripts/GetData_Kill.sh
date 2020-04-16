#!/bin/bash

SHELL_PARENT="$(dirname "$(readlink -fm "$0")")"

printf "Killing Node process with pid=$(cat "$SHELL_PARENT/.node_proc.pid")\n"
kill -9 `cat "$SHELL_PARENT/.node_proc.pid"`
rm "$SHELL_PARENT/.node_proc.pid"

