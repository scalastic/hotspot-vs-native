#!/usr/bin/env bash

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'


printf "=== ️Building Docker Images of %s Apps 🚀 ===\n\n" "${PWD##*/}"

./_tools/_check_requirements.sh

## WebUI app
cd webui
printf "** Start building %s app 🔨 ...\n\n" "${PWD##*/}"

docker build -t webui-js:1.0.0 -q .

echo "Done ✅"
cd ..

## Worker app
cd worker
printf "** Start building %s app 🔨 ...\n\n" "${PWD##*/}"

docker build -t worker-python:1.0.0 -q .

echo "Done ✅"
cd ..

## Hasher app
cd hasher-java
printf "** Start building %s app 🔨 ...\n\n" "${PWD##*/}"

printf "${RED}Note this is a long process and could take 10-20 min!!${NC}"
# Build JVM-based image
../mvnw -B -Dmaven.test.skip=true clean package
docker build -t hasher-jvm:1.0.0 -q .
# Build Native-based
../mvnw -B -Dmaven.test.skip=true spring-boot:build-image

echo "Done ✅"
cd ..

## RNG app
cd rng-java
printf "** Start building %s app 🔨 ...\n\n" "${PWD##*/}"

printf "${RED}Note this is a long process and could take 10-20 min!!${NC}"
# Build JVM-based image
../mvnw -B -Dmaven.test.skip=true clean package
docker build -t rng-jvm:1.0.0 -q .
# Build Native-based
../mvnw -B -Dmaven.test.skip=true spring-boot:build-image

echo "Done ✅"
cd ..
