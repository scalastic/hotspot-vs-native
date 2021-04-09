#!/usr/bin/env bash

printf "=== ️Push HotSpot images to Docker Hub Registry ===\n\n"

printf "== List images ==\n"
NAMES=($(docker images | grep hotspot | awk '{print $1":"$2}'))
printf "%s\n" "${NAMES[@]}"

printf "\n== Tag images ==\n"
for i in "${NAMES[@]}"
do
	docker tag "${i}" jeanjerome/"${i}"
	docker push jeanjerome/"${i}"
done

printf "\n== Done ✅ ==\n"