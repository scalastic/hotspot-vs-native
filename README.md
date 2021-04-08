# HotSpot vs Native : an effective comparison of performances

## Introduction
When it comes to comparing JVM-HotSpot and GraalVM-native executions, 
it is often hard to decide on application's architecture and technology to test and even what to mesure.

Recently I came across an interesting training course about [containers and orchestration](https://github.com/jpetazzo/container.training) 
written by Jérôme Petazzoni. He uses a bunch of interacting Python apps encapsulated in Docker containers. They act as 
a microservices mesh and measuring the number of completed cycles per elapsed time provides a good estimation of the 
system effectiveness. Being able to play with the number of running containers is also a good illustration of what 
actually happens.

I therefore decided to port the microservices `Python` code into a more JVM-friendly one and more realistic in
business community: `Spring Boot` with its `GraalVM` native compilation and its reactive programming `WebFlux` modules.

## General architecture

![schéma d'architecture](docs/architecture.jpg)

The microservices system is composed of 5 containers :
- `worker`: the algorithm orchestrator [`Python`]
- `rng`: the random number generator [`Spring Boot`]
- `hasher`: the hasher processor [`Spring Boot`]
- `redis`: the database recording each complete execution cycle
- `webui`: the web interface where system metrics are displayed [`Python`]

