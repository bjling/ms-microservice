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

* Problem 1 

[--insecure-registry](http://www.cnblogs.com/jackluo/p/5582329.html)
~~~
root@iZbp139ic9ytcyoavmelx2Z:/data/docker/registry/harbor# cat /lib/systemd/system/docker.service 
[Unit]
Description=Docker Application Container Engine
Documentation=https://docs.docker.com
After=network-online.target docker.socket firewalld.service
Wants=network-online.target
Requires=docker.socket

[Service]
Type=notify
# the default is not to use systemd for cgroups because the delegate issues still
# exists and systemd currently does not support the cgroup feature set required
# for containers run by docker
EnvironmentFile=/etc/default/docker
ExecStart=/usr/bin/dockerd -H fd:// --insecure-registry=10.168.9.114
ExecReload=/bin/kill -s HUP $MAINPID
LimitNOFILE=1048576
# Having non-zero Limit*s causes performance problems due to accounting overhead
# in the kernel. We recommend using cgroups to do container-local accounting.
LimitNPROC=infinity
LimitCORE=infinity
# Uncomment TasksMax if your systemd version supports it.
# Only systemd 226 and above support this version.
TasksMax=infinity
TimeoutStartSec=0
# set delegate yes so that systemd does not reset the cgroups of docker containers
Delegate=yes
# kill only the docker process, not all processes in the cgroup
KillMode=process
# restart the docker process if it exits prematurely
Restart=on-failure
StartLimitBurst=3
StartLimitInterval=60s

[Install]
WantedBy=multi-user.target

~~~
[systemd](https://docs.docker.com/engine/admin/systemd/#httphttps-proxy)

3. [Ubuntu iptables](http://www.cnblogs.com/general0878/p/5757377.html)
