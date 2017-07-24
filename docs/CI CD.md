## Context

[What's the CI/CD](http://www.ruanyifeng.com/blog/2015/09/continuous-integration.html?utm_source=tuicool)
1. 持续集成 (Continuous Integration)指的是，频繁地（一天多次）将代码集成到主干。
2. 持续交付（Continuous delivery）指的是，频繁地将软件的新版本，交付给质量团队或者用户，以供评审。
3. 持续部署（continuous deployment）是持续交付的下一步，指的是代码通过评审以后，自动部署到生产环境。

![Difference between ci and cd ](http://www.ruanyifeng.com/blogimg/asset/2015/bg2015092302.jpg)

[What's the DevOps](https://en.wikipedia.org/wiki/DevOps)

DevOps（“development”与“operations”缩写）是一个软件开发和交付的过程，强调在产品管理软件开发和运营的专业人士之间的沟通和协作。它试图通过建立一种文化、环境来自动化软件集成、测试、部署和基础设施的变化，在这种环境中，构建、测试和发布软件可以快速、频繁、更可靠地发生。


## Jenkins && Dokcer && Maven && Git

[Spring Boot && Docker Demo](https://github.com/wangfeishsh/ms-docker)

[Harbor registry](http://10.168.9.114/) user: admin password: Harbor12345

[Docker in CI/CD](https://www.docker.com/use-cases/cicd#/resources)

![](https://www.docker.com/sites/default/files/2016-01-26_0907_2.jpg)


#### JDK

[Ubuntu 安装 JDK 7 / JDK8 的两种方式](http://www.cnblogs.com/a2211009/p/4265225.html)


#### Git
[mac jenkins环境安装及jenkins使用（未完待续）](sudo launchctl unload -w /Library/LaunchDaemons/org.jenkins-ci.plist )
~~~
在ubuntu14.04上默认安装了Git：
# git --version
git version 1.9.1

可以使用下面命令升级git（如果不是root用户，需在命令前加sudo）：
# add-apt-repository ppa:git-core/ppa
# apt-get update
# apt-get install git

安装完成后，再查看git版本：
# git --version
git version 2.10.1
~~~

[Gitlab webhook](http://www.jianshu.com/p/ad018160aff9)

[Github webhook](http://www.jianshu.com/p/b2ed4d23a3a9)

Mac环境中Jenkins的停止和启动命令

~~~
sudo launchctl load /Library/LaunchDaemons/org.jenkins-ci.plist
sudo launchctl unload /Library/LaunchDaemons/org.jenkins-ci.plist
~~~

URL Options
http://[jenkins-server]/[command]
where [command] can be
~~~
exit shutdown jenkins
restart restart jenkins
reload to reload the configuration
~~~

#### Maven

[Ubuntu中安装Maven](http://www.linuxidc.com/Linux/2015-03/114619.htm)

Mac下需要安装在/Users/Shared/apache-maven-3.5.0目录下

#### Docker
~~~
docker run -p 8080:8080 -p 50000:50000 \
    -v /root/data/jenkins:/var/jenkins_home \
    -v /usr/bin/docker:/usr/bin/docker  \                                       
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v /usr/lib/jvm/jdk1.8.0_131:/usr/java/jdk1.8.0_131 \
    -v $(which git):/usr/bin/git \
    -v /root/apache-maven-3.5.0:/usr/maven/apache-maven-3.5.0 \
    --name jenkins  jenkins

docker run -p8080:8080 -p50000:50000 -v /root/data/jenkins:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v /usr/lib/jvm/jdk1.8.0_131:/usr/java/jdk1.8.0_131 -v $(which git):/usr/bin/git -v /root/apache-maven-3.5.0:/usr/maven/apache-maven-3.5.0  --name jenkins  jenkins
~~~

mac
~~~
docker run -p8080:8080 -p50000:50000 -v $PWD/jenkins:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v /Library/java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home:/usr/java/jdk -v $(which git):/usr/bin/git -v /Users/Shared/apache-maven-3.5.0:/usr/maven/apache-maven-3.5.0  -t jenkins
~~~

~~~
$ docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t springio/gs-spring-boot-docker
~~~

#### Shell

~~~
#more /opt/scripts/devops.sh 
#!/bin/sh
IP=$1
PROJECT=$2
VERSION=$3
 
echo "############git new code....#############"
cd /opt/scripts/Dockerfile/resin/webapps
git pull git@code.gzlife.cn:chenminghui/cmhtest.git
 
echo "#################build new images and push to private registry###########"
docker build -t registry.cmh.cn/test:${VERSION} /opt/scripts/Dockerfile/resin
docker push registry.cmh.cn/test:${VERSION}
 
echo "###########download new images and start new version of project##########"
ssh $IP "docker pull registry.cmh.cn/test:${VERSION}"
CONTAINER=`ssh $IP "docker ps | grep ${PROJECT}" | awk '{print $1}'`
ssh $IP "docker stop ${CONTAINER}"
ssh $IP "docker run -idt --name=${PROJECT}_${VERSION} -p 8080:8080 registry.cmh.cn/test:${VERSION}"

~~~