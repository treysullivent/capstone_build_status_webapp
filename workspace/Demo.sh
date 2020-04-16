#!/bin/bash

DEMO_DATA_FOLDER="/home/ubuntu/data/logs"
SHELL_PARENT="$(dirname "$(readlink -fm "$0")")"
SHELL_PARENT="/home/ubuntu/workspace"

"$SHELL_PARENT/scripts/ResetDatabase.sh"
"$SHELL_PARENT/scripts/RebuildJava.sh"
"$SHELL_PARENT/scripts/StartNodeFetcher.sh"
"$SHELL_PARENT/scripts/PopulateDBFromFolder.sh" "$DEMO_DATA_FOLDER"
