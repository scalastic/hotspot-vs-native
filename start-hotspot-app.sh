#!/usr/bin/env bash

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

printf "=== ️Starting %s apps on HotSpot JVM 🚀 ===\n\n" "${PWD##*/}"

printf "== Check requirements ==\n"
./extra/script/checkRequirements.sh

printf "== Build Maven apps ==\n"
cd ./hasher
./mvnw clean package
cd ../rng
./mvnw clean package
cd ..

printf "== Build Docker containers & start ==\n"
docker-compose --file ./docker-compose-hotspot.yml up
