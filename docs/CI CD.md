## Context

[What's the CI/CD](http://www.ruanyifeng.com/blog/2015/09/continuous-integration.html?utm_source=tuicool)
1. 持续集成 (Continuous Integration)指的是，频繁地（一天多次）将代码集成到主干。
2. 持续交付（Continuous delivery）指的是，频繁地将软件的新版本，交付给质量团队或者用户，以供评审。
3. 持续部署（continuous deployment）是持续交付的下一步，指的是代码通过评审以后，自动部署到生产环境。

![Difference between ci and cd ](http://www.ruanyifeng.com/blogimg/asset/2015/bg2015092302.jpg)

[What's the DevOps](https://en.wikipedia.org/wiki/DevOps)

DevOps（“development”与“operations”缩写）是一个软件开发和交付的过程，强调在产品管理软件开发和运营的专业人士之间的沟通和协作。它试图通过建立一种文化、环境来自动化软件集成、测试、部署和基础设施的变化，在这种环境中，构建、测试和发布软件可以快速、频繁、更可靠地发生。

[Docker in CI/CD](https://www.docker.com/use-cases/cicd#/resources)

![](https://www.docker.com/sites/default/files/2016-01-26_0907_2.jpg)

## Jenkins

#### JDK

[Ubuntu 安装 JDK 7 / JDK8 的两种方式](http://www.cnblogs.com/a2211009/p/4265225.html)


#### Git
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

#### Maven

[Ubuntu中安装Maven](http://www.linuxidc.com/Linux/2015-03/114619.htm)

#### Docker
~~~
    docker run -p 8080:8080 -p 50000:50000 --name jenkins  
    -v /your/home:/var/jenkins_home
    -v /usr/bin/docker:/usr/bin/docker                                         
    -v /var/run/docker.sock:/var/run/docker.sock   //将宿主机中docker挂载到jenkins容器中，以便在jenkins容器中执行docker命令
    -v /usr/jdk/jdk1.7.0_25:/usr/java/jdk1.7.0_25  //将宿主机中jdk挂载到jenkins容器中
    -v /usr/tomcat/apache-tomcat-7.0.29:/usr/tomcat/apache-tomcat-7.0.29     //将宿主机中tomcat挂载到jenkins容器中
    -v /usr/maven/apache-maven-3.3.9:/usr/maven/apache-maven-3.3.9           //将宿主机中maven挂载到jenkins容器中
    jenkins
~~~