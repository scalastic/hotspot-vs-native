#!/usr/bin/env bash

printf "=== ️Starting %s apps on HotSpot JVM 🚀 ===\n\n" "${PWD##*/}"

docker-compose --file ./docker-compose-hotspot.yml up
