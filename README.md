# HotSpot vs Native : an effective comparison of performances


> WORK IN PROGRESS
> 
> @TODO
> - Docker compose is not necessary
> - Script Native build
> - Deploy on K8S
> - Implement and test monitoring tool on native apps : target micrometer/prometheus

> Performances Comparison Scenario
> 0. Build jvm-based and Native-based images
> 1. Start apps with JVM-based images (for hasher and rng defined with Deployments k8s objects)
> 2. Scale up number of replicas for each deployment above
> 3. Note the time to stabilize the system, and the effectiveness of the process (cycles/sec)
> 4. Update the image of deployments to Native-based image (for hasher and rng)
> 5. Note the time to stabilize the system, and the effectiveness of the process (cycles/sec)
> 
> See https://kubernetes.io/docs/concepts/workloads/controllers/deployment/#updating-a-deployment
## Introduction
When it comes to comparing JVM-HotSpot and GraalVM-native executions, 
it is often hard to decide on application's architecture and technology to test and even what to mesure.

Recently I came across an interesting training course about [containers and orchestration](https://github.com/jpetazzo/container.training) 
written by Jérôme Petazzoni. He uses a bunch of interacting Python and Ruby apps encapsulated in Docker containers. They act as 
a microservices mesh and measuring the number of completed cycles per elapsed time provides a good estimation of the 
system effectiveness. Being able to play with the number of running containers is also a good illustration of what 
actually happens.

I therefore decided to port 2 of its microservices `Python` and `Ruby` coded
into more realistic ones in enterprise ecosystem (and also more JVM-friendly): `Spring Boot` 
and reactive `WebFlux`.

Compiling these apps into JVM-based or Native images would make a good case study for this purpose.
## Microservices Architecture

![Microservices Architecture](_img/architecture.jpg)

The resulting microservices system is composed of 5 containers :
- `worker`: the algorithm orchestrator [`Python`]
- `rng`: the random number generator [`Spring Boot`]
- `hasher`: the hasher processor [`Spring Boot`]
- `redis`: the database recording each complete execution cycle
- `webui`: the web interface where number of complete cycles is rendered [`JavaScript`]

## Requirements

- In order to ***build*** the app, you'll need to install :
    - [GraalVM Java 8 based](https://www.graalvm.org/docs/getting-started/#install-graalvm)
    - [GraalVM Native Images](https://www.graalvm.org/docs/getting-started/#native-images)
    - [Docker](https://www.docker.com/products/docker-desktop)
    - [Maven](https://maven.apache.org) (but this is optional as you may use the Maven Wrapper provided with the project)

- @TODO:
  - A kubernetes environment
  - If you only want to ***run*** the app, you'd just need [Docker](https://www.docker.com/products/docker-desktop) as 
    images were pulled on Docker Hub and are publicly accessible.

## How-to build

The goal of these builds is to produce Docker images, one per microservice. However, the Java-based ones will be build 
twice, the first, as a ***JVM-based*** image and the second, as a ***native*** one.

### The easy way

It should work on linux and macOS based systems - *and on Windows with some small modifications*

> info "Note"
> 
> It will take time....... 15-20 min depending on your internet connection and processor! That's the price to compile 
> to native code.

To do so, execute the script at the project root:
```bash
./build_docker_images.sh
```

### The other way

- For a non-java app, just enter:
``` bash
docker build -t <app_docker_tag> ./<app_dir>
```

- For a Java app and JVM-based image:
``` bash
cd <app_dir>
mvn clean package
docker build -t <app_docker_tag> .
```

- For a Java app and native image:
``` bash
cd <app_dir>
mvn spring-boot:build-image
```

### Expected result

At least, you should now see your images in your local registry:
```
> docker images
REPOSITORY                TAG        IMAGE ID       CREATED             SIZE
rng-jvm                   1.0.0      93de422df5d5   58 minutes ago      310MB
hasher-jvm                1.0.0      d83f93c156de   About an hour ago   310MB
worker-python             1.0.0      eecd70ae0cf4   5 hours ago         54.7MB
webui-js                  1.0.0      0216a3b68548   2 days ago          219MB
paketobuildpacks/builder  tiny       3d35e291e768   41 years ago        409MB
rng-native                1.0.0      1afb354ae0cb   41 years ago        80.6MB
hasher-native             1.0.0      c89096e7fb46   41 years ago        80.6MB
```

> info "Note #1"
> 
> Images whose name starts with `paketobuildpacks` comes from [Buildpacks](https://buildpacks.io) 
> and are used for building native parts

> info "Note #2"
> 
> Native images created time seems inaccurate. It's not, the explanation is here: 
> [Time Travel with Pack](https://medium.com/buildpacks/time-travel-with-pack-e0efd8bf05db)

### The other other-way

I've pulled this stuf into a public registry on Docker Hub so you don't even need to worry about these builds.

## Let's play now

This is the demo part. Because this application is microservices-based, we are going to play with the number of containers for each microservice.

For that, we need a ***Kubernetes cluster***... and ***Prometheus***, ***Grafana***... and **metrics** coming from our microservices to monitor the app!!

Well, no problem:
- Thanks to ***Spring Boot*** and ***Micrometer***, the JVM and native versions of the app already expose metrics to Prometheus.
- For the complete Kubernetes stack, there's also this previous article with all explained and scripted: [Locally install Kubernetes, Prometheus, and Grafana](https://scalastic.io/install-kubernetes/) 

### First start

#### Kubernetes configuration

Let's have a look at how to set up these microservices into our kubernetes cluster.

Remember the microservices architecture :
![Microservices Architecture](_img/architecture.jpg)

1. First, we want to manage the number of ~~containers~~ - pods in this case -  per microservice . We could want to 
   scale up automatically this number depending on metrics. We also would like to change the image of the pod, passing 
   from a JVM image to a native image without the need to restart from scratch... Such Kubernetes resource already 
   exists: [Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)

2. Second, we want our microservices to communicate each others in the Kubernetes cluster. That's the job of 
   [Service](https://kubernetes.io/docs/concepts/services-networking/).

3. We want to access the web UI from outside the cluster: 
   [NodePort](https://kubernetes.io/docs/concepts/services-networking/service/#nodeport) would be sufficient.

4. The Redis database does not need to be reached from the outside but only from the inside: that's already done by 
   [ClusterIP](https://kubernetes.io/docs/concepts/services-networking/service/) which is the default Service type in Kubernetes.


And I forgot the specific monitoring definition for the two Spring Boot microsercices... Well, it should be done 
in Service/metadata/annotations.


Have a look at the `_kube/k8s-app-jvm.yml`.

#### Start the app

In the first place, we start the app with one pod per microservice and the JVM-based for those based on Java langage.

Execute the command:
```
kubectl apply -f _kube/k8s-app-jvm.yml
```

Connect to the Web UI interface [http://localhost/](http://localhost/)

You should see the worker resulting process:

![The Web UI interface at startup](_img/webui-at-startup.png)

## Based on

- Jérôme Patazzoni's `container-training` : [https://github.com/jpetazzo/container.training](https://github.com/jpetazzo/container.training)