#!/usr/bin/env bash

printf "=== ️Starting %s apps on HotSpot JVM 🚀 ===\n\n" "${PWD##*/}"

printf "== Check requirements ==\n"
./extra/script/checkRequirements.sh

printf "== Build Maven apps ==\n"
cd ./hasher
./mvnw package -Dmaven.test.skip=true
cd ../rng
./mvnw package -Dmaven.test.skip=true
cd ..

printf "== Build Docker containers & start ==\n"
docker-compose --file ./docker-compose-hotspot.yml up
