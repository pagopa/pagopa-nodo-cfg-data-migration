#!/bin/bash

set -e

db='cfg'

echo "Running liquibase in [$2/$1]"
liquibase --defaultsFile=properties/${db}.properties validate --log-level debug
liquibase --defaultsFile=properties/${db}.properties update --log-level info

echo "End!!!!!!"
