[Best practices for writing Dockerfiles](https://docs.docker.com/engine/userguide/eng-image/dockerfile_best-practices/#build-cache)

[Docker run reference](https://docs.docker.com/edge/engine/reference/run/)

[Dockerfile reference](https://docs.docker.com/engine/reference/builder/)

## Usage

The docker build command builds an image from a Dockerfile and a context. The build’s context is the set of files at a specified location PATH or URL. The PATH is a directory on your local filesystem. The URL is a Git repository location.

A context is processed recursively (递归的). So, a PATH includes any subdirectories and the URL includes the repository and its submodules. 

~~~
    $ docker build .
    Sending build context to Docker daemon  6.51 MB
    ...
~~~

The build is run by the Docker daemon, not by the CLI. The first thing a build process does is send the entire context (recursively) to the daemon. In most cases, it’s best to start with an empty directory as context and keep your Dockerfile in that directory. Add only the files needed for building the Dockerfile.

> Warning: Do not use your root directory, /, as the PATH as it causes the build to transfer the entire contents of your hard drive to the Docker daemon.

Traditionally, the Dockerfile is called Dockerfile and located in the root of the context. You use the -f flag with docker build to point to a Dockerfile anywhere in your file system.
~~~
$ docker build -f /path/to/a/Dockerfile .
~~~
You can specify a repository and tag at which to save the new image if the build succeeds:
~~~
$ docker build -t shykes/myapp .
~~~
To tag the image into multiple repositories after the build, add multiple -t parameters when you run the build command:
~~~
$ docker build -t shykes/myapp:1.0.2 -t shykes/myapp:latest .
~~~
Before the Docker daemon runs the instructions in the Dockerfile, it performs a preliminary validation of the Dockerfile and returns an error if the syntax is incorrect:
~~~
$ docker build -t test/myapp .
Sending build context to Docker daemon 2.048 kB
Error response from daemon: Unknown instruction: RUNCMD
~~~

## Format

The instruction is not case-sensitive.

Docker runs instructions in a Dockerfile in order. A Dockerfile must start with a `FROM` instruction. The FROM instruction specifies the Base Image from which you are building. FROM may only be proceeded by one or more ARG instructions, which declare arguments that are used in FROM lines in the Dockerfile.

## Parser directives

## escape
~~~
# escape=\ (backslash)
Or
# escape=` (backtick)
~~~

Setting the escape character to ` is especially useful on Windows, where \ is the directory path separator. ` is consistent with Windows PowerShell.

The result of this dockerfile is that second and third lines are considered a single instruction:

~~~
FROM microsoft/nanoserver
COPY testfile.txt c:\\
RUN dir c:\
~~~
Results in:

~~~
PS C:\John> docker build -t cmd .
Sending build context to Docker daemon 3.072 kB
Step 1/2 : FROM microsoft/nanoserver
 ---> 22738ff49c6d
Step 2/2 : COPY testfile.txt c:\RUN dir c:
GetFileAttributesEx c:RUN: The system cannot find the file specified.
PS C:\John>
~~~

By adding the escape parser directive, the following Dockerfile succeeds as expected with the use of natural platform semantics for file paths on Windows:

~~~
# escape=`

FROM microsoft/nanoserver
COPY testfile.txt c:\
RUN dir c:\
~~~

Results in:
~~~
PS C:\John> docker build -t succeeds --no-cache=true .
Sending build context to Docker daemon 3.072 kB
Step 1/3 : FROM microsoft/nanoserver
 ---> 22738ff49c6d
Step 2/3 : COPY testfile.txt c:\
 ---> 96655de338de
Removing intermediate container 4db9acbb1682
Step 3/3 : RUN dir c:\
 ---> Running in a2c157f842f5
 Volume in drive C has no label.
 Volume Serial Number is 7E6D-E0F7

 Directory of c:\

10/05/2016  05:04 PM             1,894 License.txt
10/05/2016  02:22 PM    <DIR>          Program Files
10/05/2016  02:14 PM    <DIR>          Program Files (x86)
10/28/2016  11:18 AM                62 testfile.txt
10/28/2016  11:20 AM    <DIR>          Users
10/28/2016  11:20 AM    <DIR>          Windows
           2 File(s)          1,956 bytes
           4 Dir(s)  21,259,096,064 bytes free
 ---> 01c7f3bef04f
Removing intermediate container a2c157f842f5
Successfully built 01c7f3bef04f
PS C:\John>
~~~

## Environment replacement

Environment variables are notated in the Dockerfile either with $variable_name or ${variable_name}. They are treated equivalently and the brace syntax is typically used to address issues with variable names with no whitespace, like ${foo}_bar.

The ${variable_name} syntax also supports a few of the standard bash modifiers as specified below:

* ${variable:-word} indicates that if variable is set then the result will be that value. If variable is not set then word will be the result.
* ${variable:+word} indicates that if variable is set then word will be the result, otherwise the result is the empty string.

Escaping is possible by adding a \ before the variable: \$foo or \${foo}, for example, will translate to $foo and ${foo} literals respectively.

Example (parsed representation is displayed after the #):

~~~
FROM busybox
ENV foo /bar
WORKDIR ${foo}   # WORKDIR /bar
ADD . $foo       # ADD . /bar
COPY \$foo /quux # COPY $foo /quux
~~~

Environment variables are supported by the following list of instructions in the Dockerfile:

* ADD
* COPY
* ENV
* EXPOSE
* FROM
* LABEL
* STOPSIGNAL
* USER
* VOLUME
* WORKDIR

as well as:

* ONBUILD (when combined with one of the supported instructions above)

> Note: prior to 1.4, ONBUILD instructions did NOT support environment variable, even when combined with any of the instructions listed above.

## .dockerignore file

~~~
    *.md
    !README*.md
    README-secret.md
~~~

You can even use the .dockerignore file to exclude the Dockerfile and .dockerignore files. These files are still sent to the daemon because it needs them to do its job. But the ADD and COPY instructions do not copy them to the image.

Finally, you may want to specify which files to include in the context, rather than which to exclude. To achieve this, specify * as the first pattern, followed by one or more ! exception patterns.

## FROM

~~~
FROM <image> [AS <name>]

FROM <image>[:<tag>] [AS <name>]

FROM <image>[@<digest>] [AS <name>]
~~~

* ARG is the only instruction that may proceed FROM in the Dockerfile. See Understand how ARG and FROM interact.

* FROM can appear multiple times within a single Dockerfile to create multiple images or use one build stage as a dependency for another. Simply make a note of the last image ID output by the commit before each new FROM instruction. Each FROM instruction clears any state created by previous instructions.

* Optionally a name can be given to a new build stage by adding AS name to the FROM instruction. The name can be used in subsequent FROM and COPY --from=<name|index> instructions to refer to the image built in this stage.

* The tag or digest values are optional. If you omit either of them, the builder assumes a latest tag by default. The builder returns an error if it cannot find the tag value.

~~~
ARG  CODE_VERSION=latest
FROM base:${CODE_VERSION}
CMD  /code/run-app

FROM extras:${CODE_VERSION}
CMD  /code/run-extras
~~~

## RUN

RUN has 2 forms:

* `RUN <command>` (shell form, the command is run in a shell, which by default is `/bin/sh -c` on Linux or cmd /S /C on Windows)
* `RUN ["executable", "param1", "param2"]` (exec form)


## CMD
The CMD instruction has three forms:

* `CMD ["executable","param1","param2"]` (exec form, this is the preferred form)
* `CMD ["param1","param2"]` (as default parameters to ENTRYPOINT)
* `CMD command param1 param2` (shell form)

There can only be one CMD instruction in a Dockerfile. If you list more than one CMD then only the last CMD will take effect.

###### The main purpose of a CMD is to provide defaults for an executing container.

> Note: Don’t confuse RUN with CMD. RUN actually runs a command and commits the result; CMD does not execute anything at build time, but specifies the intended command for the image. 

## LABEL

~~~
LABEL <key>=<value> <key>=<value> <key>=<value> ...
~~~

Each LABEL instruction produces a new layer which can result in an inefficient image if you use many labels.

To view an image’s labels, use the docker inspect command.

~~~
"Labels": {
    "com.example.vendor": "ACME Incorporated"
    "com.example.label-with-value": "foo",
    "version": "1.0",
    "description": "This text illustrates that label-values can span multiple lines.",
    "multi.label1": "value1",
    "multi.label2": "value2",
    "other": "value3"
},
~~~

## MAINTAINER (deprecated)

To set a label corresponding to the MAINTAINER field you could use:
~~~
LABEL maintainer="SvenDowideit@home.org.au"
~~~
This will then be visible from docker inspect with the other labels.

## EXPOSE

~~~
EXPOSE <port> [<port>...]
~~~

设置指令，该指令会将容器中的端口映射成宿主机器中的某个端口。当你需要访问容器的时候，可以不是用容器的IP地址而是使用宿主机器的IP地址和映射后的端口。要完成整个操作需要两个步骤，首先在Dockerfile使用EXPOSE设置需要映射的容器端口，然后在运行容器的时候指定-p选项加上EXPOSE设置的端口，这样EXPOSE设置的端口号会被随机映射成宿主机器中的一个端口号。也可以指定需要映射到宿主机器的那个端口，这时要确保宿主机器上的端口号没有被使用。EXPOSE指令可以一次设置多个端口号，相应的运行容器的时候，可以配套的多次使用-p选项。

~~~
# 映射一个端口  
EXPOSE port1  
# 相应的运行容器使用的命令  
docker run -p port1 image  
  
# 映射多个端口  
EXPOSE port1 port2 port3  
# 相应的运行容器使用的命令  
docker run -p port1 -p port2 -p port3 image  
# 还可以指定需要映射到宿主机器上的某个端口号  
docker run -p host_port1:port1 -p host_port2:port2 -p host_port3:port3 image  
~~~

端口映射是docker比较重要的一个功能，原因在于我们每次运行容器的时候容器的IP地址不能指定而是在桥接网卡的地址范围内随机生成的。宿主机器的IP地址是固定的，我们可以将容器的端口的映射到宿主机器上的一个端口，免去每次访问容器中的某个服务时都要查看容器的IP的地址。对于一个运行的容器，可以使用docker port加上容器中需要映射的端口和容器的ID来查看该端口号在宿主机器上的映射端口。


## ENV

~~~
ENV <key> <value>
ENV <key>=<value> ...
~~~

## ADD

ADD has two forms:

* `ADD <src>... <dest>`
* `ADD ["<src>",... "<dest>"]` (this form is required for paths containing whitespace)

## COPY
COPY has two forms:

* `COPY <src>... <dest>`
* `COPY ["<src>",... "<dest>"]` (this form is required for paths containing whitespace)

## ENTRYPOINT
ENTRYPOINT has two forms:

* `ENTRYPOINT ["executable", "param1", "param2"]` (exec form, preferred)
* `ENTRYPOINT command param1 param2` (shell form)

Command line arguments to docker run <image> will be appended after all elements in an exec form ENTRYPOINT, and will override all elements specified using CMD. This allows arguments to be passed to the entry point, i.e., docker run <image> -d will pass the -d argument to the entry point. You can override the ENTRYPOINT instruction using the docker run --entrypoint flag.

The shell form prevents any CMD or run command line arguments from being used, but has the disadvantage that your ENTRYPOINT will be started as a subcommand of /bin/sh -c, which does not pass signals. This means that the executable will not be the container’s PID 1 - and will not receive Unix signals - so your executable will not receive a SIGTERM from docker stop <container>.

Only the last ENTRYPOINT instruction in the Dockerfile will have an effect.

#### Understand how CMD and ENTRYPOINT interact

## VOLUME

~~~
VOLUME ["/data"]
~~~

The VOLUME instruction creates a mount point with the specified name and marks it as holding externally mounted volumes from native host or other containers. The value can be a JSON array, VOLUME ["/var/log/"], or a plain string with multiple arguments, such as VOLUME /var/log or VOLUME /var/log /var/db

可以将本地文件夹或者其他container的文件夹挂载到container中。

## USER

~~~
USER <user>[:<group>] or
USER <UID>[:<GID>]
~~~

The USER instruction sets the user name (or UID) and optionally the user group (or GID) to use when running the image and for any RUN, CMD and ENTRYPOINT instructions that follow it in the Dockerfile.

> Warning: When the user does doesn’t have a primary group then the image (or the next instructions) will be run with the root group.

## WORKDIR

~~~
WORKDIR /path/to/workdir
~~~
The WORKDIR instruction sets the working directory for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile. If the WORKDIR doesn’t exist, it will be created even if it’s not used in any subsequent Dockerfile instruction.

## ARG

~~~
ARG <name>[=<default value>]
~~~

The ARG instruction defines a variable that users can pass at build-time to the builder with the docker build command using the --build-arg <varname>=<value> flag.

consider this Dockerfile:
~~~
1 FROM busybox
2 USER ${user:-some_user}
3 ARG user
4 USER $user
...
~~~
A user builds this file by calling:

~~~
$ docker build --build-arg user=what_user .
~~~
Docker has a set of predefined ARG variables that you can use without a corresponding ARG instruction in the Dockerfile.

* HTTP_PROXY
* http_proxy
* HTTPS_PROXY
* https_proxy
* FTP_PROXY
* ftp_proxy
* NO_PROXY
* no_proxy

To use these, simply pass them on the command line using the flag:

    --build-arg <varname>=<value>
    
## ONBUILD

This is useful if you are building an image which will be used as a base to build other images

## STOPSIGNAL

~~~
 STOPSIGNAL signal
~~~

## HEALTHCHECK

The HEALTHCHECK instruction has two forms:

* `HEALTHCHECK [OPTIONS] CMD command` (check container health by running a command inside the container)
* `HEALTHCHECK NONE` (disable any healthcheck inherited from the base image)

The options that can appear before CMD are:

* `--interval=DURATION` (default: 30s)
* `--timeout=DURATION` (default: 30s)
* `--start-period=DURATION` (default: 0s)
* `--retries=N` (default: 3)

The command after the CMD keyword can be either a shell command (e.g. HEALTHCHECK CMD /bin/check-running) or an exec array (as with other Dockerfile commands; see e.g. ENTRYPOINT for details).

he command’s exit status indicates the health status of the container. The possible values are:

* 0: success - the container is healthy and ready for use
* 1: unhealthy - the container is not working correctly
* 2: reserved - do not use this exit code

For example, to check every five minutes or so that a web-server is able to serve the site’s main page within three seconds:

~~~
HEALTHCHECK --interval=5m --timeout=3s \
  CMD curl -f http://localhost/ || exit 1
~~~

## SHELL

    SHELL ["executable", "parameters"]