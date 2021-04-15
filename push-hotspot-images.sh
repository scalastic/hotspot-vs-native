#!/usr/bin/env bash

printf "=== ️Push HotSpot images to Docker Hub Registry ===\n\n"

printf "== List images ==\n"
HOTSPOT_NAMES=($(docker images | grep hotspot | awk '{print $1":"$2}'))
REGULAR_NAMES=($(docker images | grep regular | awk '{print $1":"$2}'))
printf "%s\n" "${HOTSPOT_NAMES[@]}"
printf "%s\n" "${REGULAR_NAMES[@]}"

printf "\n== Tag images ==\n"
for i in "${HOTSPOT_NAMES[@]}"
do
	docker tag "${i}" jeanjerome/"${i}"
	docker push jeanjerome/"${i}"
done
for i in "${REGULAR_NAMES[@]}"
do
	docker tag "${i}" jeanjerome/"${i}"
	docker push jeanjerome/"${i}"
done

printf "\n== Done ✅ ==\n"