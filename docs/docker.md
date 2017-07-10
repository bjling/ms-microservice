#### command line

[command line](https://docs.docker.com/edge/engine/reference/commandline/docker/#description)

#### glossary

[docker glossary](https://docs.docker.com/glossary/)

#### 加速器

1. 阿里云

[accelerator](https://cr.console.aliyun.com/?spm=5176.1971733.0.2.394b9fbdbpnsBY#/accelerator)

~~~
    sudo mkdir -p /etc/docker
    sudo tee /etc/docker/daemon.json <<-'EOF'
    {
      "registry-mirrors": ["https://a4tyth8r.mirror.aliyuncs.com"]
    }
    EOF
    sudo systemctl daemon-reload
    sudo systemctl restart docker
~~~

2. daocloud

[accelerator](https://www.daocloud.io/mirror#accelerator-doc)

~~~
    curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://a8c1ed21.m.daocloud.io
~~~


3. official

[official](https://docs.docker.com/registry/recipes/mirror/#use-case-the-china-registry-mirror)


#### QUESTION

1. application 一般是通过挂卷的方式运行 or 通过images方式运行

2. registry 应用的数量，以及storage driver使用问题

3. Harbor 是单机还是集群

#### HARBOR

1. Docker-compose [Install](https://github.com/docker/compose/releases)
~~~
curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
~~~

2. Harbor [Install](https://github.com/vmware/harbor/blob/master/docs/installation_guide.md)