#!/bin/bash

DEMO_DATA_FOLDER="../data/logs"
SHELL_PARENT="$(dirname "$(readlink -fm "$0")")"
SHELL_PARENT="../workspace"

"$SHELL_PARENT/scripts/ResetDatabase.sh"
"$SHELL_PARENT/scripts/RebuildJava.sh"
"$SHELL_PARENT/scripts/GetData_SilentStart.sh"
"$SHELL_PARENT/scripts/RunLogParser.sh" "$DEMO_DATA_FOLDER"
