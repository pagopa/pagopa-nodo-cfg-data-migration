#!/bin/bash

set -e

db='cfg'

echo "Running liquibase in [$2/$1]"
liquibase --defaultsFile=properties/${db}.properties validate
liquibase --defaultsFile=properties/${db}.properties update

echo "End!!!!!!"
