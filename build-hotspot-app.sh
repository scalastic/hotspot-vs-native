#!/usr/bin/env bash

printf "=== ️Starting %s apps on HotSpot JVM 🚀 ===\n\n" "${PWD##*/}"

printf "== Check requirements ==\n"
./extra/script/checkRequirements.sh

printf "== Build Maven apps ==\n"
cd ./hasher-java
./mvnw clean package -Dmaven.test.skip=true
cd ../rng-java
./mvnw clean package -Dmaven.test.skip=true
cd ..
