#!/bin/bash

DEMO_DATA_FOLDER="../data/logs"
SHELL_PARENT="$(dirname "$(readlink -fm "$0")")"
SHELL_PARENT="../workspace"

./ResetDatabase.sh
./RebuildJava.sh
./GetData_SilentStart.sh
./RunLogParser.sh "$DEMO_DATA_FOLDER"
