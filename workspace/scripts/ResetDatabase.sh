#!/bin/bash

printf "Clearing and resetting database...\n"
mysql --defaults-extra-file=~/database/credentials/root.cnf -s < ~/database/sql/delete_db.sql
mysql --defaults-extra-file=~/database/credentials/root.cnf -s < ~/database/sql/create_db.sql

