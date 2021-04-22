# BUILD OF RNG

## CONTAINERIZED WITH JVM

1. Build Jar
```
mvn clean package
```
2. Build Docker image
```
docker build -f ./Dockerfile.hotspot -t rng-hotspot:0.0.2-SNAPSHOT .
```