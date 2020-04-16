#!/bin/bash

printf "Starting Java program with pid="
cd ../log_parser
nohup java Driver $1 >/dev/null 2>&1 2>&1 &
printf "%s\n" $!
