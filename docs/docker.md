#### command line

[command line](https://docs.docker.com/edge/engine/reference/commandline/docker/#description)

#### vocabulary

###### image

An image is a lightweight, stand-alone, executable package that includes everything needed to run a piece of software, including the code, a runtime, libraries, environment variables, and config files.

映像是一个轻量级的、独立的、可执行的包，它包含运行一个软件所需的一切，包括代码、运行时库、环境变量和配置文件。

###### container

A container is a runtime instance of an image—what the image becomes in memory when actually executed. It runs completely isolated from the host environment by default, only accessing host files and ports if configured to do so.

容器是映像的运行时实例，当实际执行时映像在内存中变成什么。默认情况下，它与宿主环境完全隔离，仅访问主机文件和端口，如果配置为这样做。

###### services

In a distributed application, different pieces of the app are called “services.” For example, if you imagine a video sharing site, it probably includes a service for storing application data in a database, a service for video transcoding in the background after a user uploads something, a service for the front-end, and so on.

Services are really just “containers in production.” A service only runs one image, but it codifies the way that image runs—what ports it should use, how many replicas of the container should run so the service has the capacity it needs, and so on. Scaling a service changes the number of container instances running that piece of software, assigning more computing resources to the service in the process.

Luckily it’s very easy to define, run, and scale services with the Docker platform – just write a docker-compose.yml file.

在一个分布式的应用，不同的应用程序被称为“服务”。例如，如果你想象一个视频分享网站，它可能包括一个用于在数据库中存储应用程序数据的service，用户上传的一些对视频后，在后台进行转码的service，提供前端服务的service，等等。

Services就是是“生产环境的容器。”服务只运行一个图像，但它所编码的图像运行它应该使用什么端口的，有多少复制品的容器应运行服务所需要的能力，等等。拓展服务会改变运行该软件的容器实例的数量，将更多的计算资源分配给运行中的服务。

幸运的是，它很容易通过Docker平台去定义、运行，拓展 services -- 只需写一个docker-compose.yml文件。