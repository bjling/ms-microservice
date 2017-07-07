## Get Started, Part 1: Orientation and Setup

1. Orientation and Setup

###### image

An image is a lightweight, stand-alone, executable package that includes everything needed to run a piece of software, including the code, a runtime, libraries, environment variables, and config files.

映像是一个轻量级的、独立的、可执行的包，它包含运行一个软件所需的一切，包括代码、运行时库、环境变量和配置文件。

###### container

A container is a runtime instance of an image—what the image becomes in memory when actually executed. It runs completely isolated from the host environment by default, only accessing host files and ports if configured to do so.

容器是映像的运行时实例，当实际执行时映像在内存中变成什么。默认情况下，它与宿主环境完全隔离，仅访问主机文件和端口，如果配置为这样做。

2. Containers vs. virtual machines

Virtual Machine diagram

![](https://www.docker.com/sites/default/files/VM%402x.png)

Virtual machines run guest operating systems—note the OS layer in each box. This is resource intensive, and the resulting disk image and application state is an entanglement of OS settings, system-installed dependencies, OS security patches, and other easy-to-lose, hard-to-replicate ephemera.

虚拟机运行guest操作系统，请注意每个框中的OS层。这是资源密集型，以及由此产生的磁盘映像和应用程序状态与操作系统的配置是纠缠的，系统安装的依赖性，操作系统的安全补丁，和其他容易失去，难以复制的蜉蝣。

Container diagram

![](https://www.docker.com/sites/default/files/Container%402x.png)

Containers can share a single kernel, and the only information that needs to be in a container image is the executable and its package dependencies, which never need to be installed on the host system. These processes run like native processes, and you can manage them individually by running commands like docker ps—just like you would run ps on Linux to see active processes. Finally, because they contain all their dependencies, there is no configuration entanglement; a containerized app “runs anywhere.”

3. Conclusion

The unit of scale being an individual, portable executable has vast implications. It means CI/CD can push updates to any part of a distributed application, system dependencies are not an issue, and resource density is increased. Orchestration of scaling behavior is a matter of spinning up new executables, not new VM hosts.

规模的单位是独立的，便携式可执行文件具有巨大的影响。这意味着CI / CD可以将更新推送到分布式应用程序的任何部分，系统依赖性不是问题，而且资源密度也增加了。规模行为表现的编排是一个新的可执行文件的转动，没有新的VM主机。

## Get Started, Part 2: Containers

~~~
    docker build -t friendlyname .  # Create image using this directory's Dockerfile
    docker run -p 4000:80 friendlyname  # Run "friendlyname" mapping port 4000 to 80
    docker run -d -p 4000:80 friendlyname         # Same thing, but in detached mode
    docker ps                                 # See a list of all running containers
    docker stop <hash>                     # Gracefully stop the specified container
    docker ps -a           # See a list of all containers, even the ones not running
    docker kill <hash>                   # Force shutdown of the specified container
    docker rm <hash>              # Remove the specified container from this machine
    docker rm $(docker ps -a -q)           # Remove all containers from this machine
    docker images -a                               # Show all images on this machine
    docker rmi <imagename>            # Remove the specified image from this machine
    docker rmi $(docker images -q)             # Remove all images from this machine
    docker login             # Log in this CLI session using your Docker credentials
    docker tag <image> username/repository:tag  # Tag <image> for upload to registry
    docker push username/repository:tag            # Upload tagged image to registry
    docker run username/repository:tag                   # Run image from a registry
~~~

## Get Started, Part 3: Services

~~~
    docker stack ls              # List all running applications on this Docker host
    docker stack deploy -c <composefile> <appname>  # Run the specified Compose file
    docker stack services <appname>       # List the services associated with an app
    docker stack ps <appname>   # List the running containers associated with an app
    docker stack rm <appname>                             # Tear down an application
~~~

## Get Started, Part 4: Swarms

A swarm is a group of machines that are running Docker and joined into a cluster. After that has happened, you continue to run the Docker commands you’re used to, but now they are executed on a cluster by a swarm manager. The machines in a swarm can be physical or virtual. After joining a swarm, they are referred to as nodes.

一个swarm是一组正在运行Docker的机器加入到一个集群。之后的事情发生了，你继续你习惯的Docker的命令，但在集群中他们通过swam manager去执行命令。集群中的机器可以是物理的，也可以是虚拟的。加入集群后，它们被称为节点。

~~~
    docker-machine create --driver virtualbox myvm1 # Create a VM (Mac, Win7, Linux)
    docker-machine create -d hyperv --hyperv-virtual-switch "myswitch" myvm1 # Win10
    docker-machine env myvm1                # View basic information about your node
    docker-machine ssh myvm1 "docker node ls"         # List the nodes in your swarm
    docker-machine ssh myvm1 "docker node inspect <node ID>"        # Inspect a node
    docker-machine ssh myvm1 "docker swarm join-token -q worker"   # View join token
    docker-machine ssh myvm1   # Open an SSH session with the VM; type "exit" to end
    docker-machine ssh myvm2 "docker swarm leave"  # Make the worker leave the swarm
    docker-machine ssh myvm1 "docker swarm leave -f" # Make master leave, kill swarm
    docker-machine start myvm1            # Start a VM that is currently not running
    docker-machine stop $(docker-machine ls -q)               # Stop all running VMs
    docker-machine rm $(docker-machine ls -q) # Delete all VMs and their disk images
    docker-machine scp docker-compose.yml myvm1:~     # Copy file to node's home dir
    docker-machine ssh myvm1 "docker stack deploy -c <file> <app>"   # Deploy an app
~~~

## Get Started, Part 5: Stacks

Here in part 5, you’ll reach the top of the hierarchy of distributed applications: the stack. A stack is a group of interrelated services that share dependencies, and can be orchestrated and scaled together. A single stack is capable of defining and coordinating the functionality of an entire application (though very complex applications may want to use multiple stacks).

在第5部分中，您将达到分布式应用程序层次结构的顶层：堆栈。堆栈是一组共享依赖关系的相互关联的服务，可以一起被编排和缩放。单个堆栈能够定义和协调整个应用程序的功能（尽管非常复杂的应用程序可能希望使用多个堆栈）。