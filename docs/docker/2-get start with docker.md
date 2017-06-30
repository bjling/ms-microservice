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