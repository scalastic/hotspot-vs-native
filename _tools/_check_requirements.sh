#!/usr/bin/env bash

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

### Check Java version ###

if [[ -x $(type -p java) ]]; then
    # found java executable in PATH
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    # found java executable in JAVA_HOME
    _java="$JAVA_HOME/bin/java"
else
    printf "${RED}FAILURE${NC}: Can't find java command! 🔥\n"
    exit 1;
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)
    if [[ "$version" == "11" ]]; then
        printf "Found Java version ${version} ✅\n"
    else
        printf "${RED}FAILURE${NC}: Found Java version ${version} but should be 11! 🔥\n"
        exit 1;
    fi
fi

### Check Docker ###

if [[ -x $(type -p docker) ]]; then
  printf "Found Docker command ✅\n"
else
  printf "${RED}FAILURE${NC}: Can't find docker command! 🔥\n"
  exit 1;
fi

### Check Maven ###

if [[ -x $(type -p mvn) ]]; then
  printf "Found Maven command ✅\n"
else
  printf "No Maven command found : we'll use Maven Wrapper instead ✅\n"
fi

### Final
printf "Check complete ✅\n\n"