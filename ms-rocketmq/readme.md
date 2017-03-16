https://rocketmq.incubator.apache.org/docs/quick-start/

## Prerequisite
* 64bit OS, Linux/Unix/Mac is recommended;
* 64bit JDK 1.7+;
* Maven 3.2.x
* Git

Install maven
```bash
wget http://mirrors.hust.edu.cn/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
```

修改环境变量，在/etc/profile中添加以下几行
```bash
MAVEN_HOME=/home/pdt_pa_zhangjun/apache-maven-3.3.9
export MAVEN_HOME
export PATH=${PATH}:${MAVEN_HOME}/bin
```
记得执行source /etc/profile使环境变量生效。

## Clone & Build
```bash
> git clone https://github.com/apache/incubator-rocketmq.git
> cd incubator-rocketmq
> mvn clean package install -Prelease-all assembly:assembly -U
> cd target/apache-rocketmq-all/
```

## Start Name Server
```bash
> nohup sh bin/mqnamesrv &
> tail -f ~/logs/rocketmqlogs/namesrv.log
  The Name Server boot success...
```

## Start Broker
```bash
> nohup sh bin/mqbroker -n localhost:9876 &
> tail -f ~/logs/rocketmqlogs/broker.log 
  The broker[%s, 172.30.30.233:10911] boot success...
```

## Send & Receive Messages
```bash
> export NAMESRV_ADDR=localhost:9876
> sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
 SendResult [sendStatus=SEND_OK, msgId= ...

> sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
 ConsumeMessageThread_%d Receive New Messages: [MessageExt...
```

## Shutdown Servers
```bash
> sh bin/mqshutdown broker
The mqbroker(36695) is running...
Send shutdown request to mqbroker(36695) OK

> sh bin/mqshutdown namesrv
The mqnamesrv(36664) is running...
Send shutdown request to mqnamesrv(36664) OK
```

## Configuration
There are three pre-built configurations shipped with the distribution of RocketMQ under conf folder for your reference:

* 2m-2s-sync
* 2m-2s-async
* 2m-noslave

Note: all configurations uses ASYNC_FLUSH.

## Deployment Example
For example, We want to have a cluster with 2 name servers, 2 broker sets with general purpose: for this example, we choose 2m-2s-sync Assuming binary RocketMQ is at /home/rocketmq/dist

Start up two name servers as is shown in Quick Start guide. Assume their IPs are 192.168.0.2 and 192.168.0.3.

Start brokers
```bash
cd /home/rocketmq/dist/bin

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-a.properties -n 192.168.0.2:9876;192.168.0.3:9876 &

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-a-s.properties -n 192.168.0.2:9876;192.168.0.3:9876 &

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-b.properties -n 192.168.0.2:9876;192.168.0.3:9876 &

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-b-s.properties -n 192.168.0.2:9876;192.168.0.3:9876 &
```
Verify

Execute the following command to verify
```bash
bash mqadmin clusterList
```

## Install zip && lrzsz && telnet && iptables
```bash
yum install -y unzip zip

yum install lrzsz

iptables -F
```


问题：
1. Caused by: java.net.UnknownHostException: soaconfig018096: Temporary failure in name resolution
```bash
/etc/hosts
/etc/sysconfig/network
```

2. JAVA_HOME

/etc/profile
```bash
JAVA_HOME=/usr/java/jdk1.8.0_111
CLASSPATH=$JAVA_HOME/lib/
PATH=/bin:/usr/bin:/sbin
PATH=${PATH}:${JAVA_HOME}/bin
export PATH JAVA_HOME CLASSPATH
```

3. cannot allocate Memory
查看磁盘
查看内存
```bash
df -hl
free -hl
top
```


nohup bash mqbroker -c ../conf/2m-2s-sync/broker-a.properties -n 172.18.18.96:9876;172.18.18.97:9876 &

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-a-s.properties -n 172.18.18.96:9876;172.18.18.97:9876 &

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-b.properties -n 172.18.18.96:9876;172.18.18.97:9876 &

nohup bash mqbroker -c ../conf/2m-2s-sync/broker-b-s.properties -n 172.18.18.96:9876;172.18.18.97:9876 &



1、使用sync命令将所有未写的系统缓存区write to disk，包含已修改的i node、已延迟的块I\O和读写映射
[root@ipython ~]# sync
 
2、粗暴清理
[root@ipython ~]# cat /proc/sys/vm/drop_caches
0
##可以看到默认是0，drop_caches提供了三个选项操作##
1 清空页缓存   2 清空inode和目录树缓存  3 清空所有的缓存
 
##清空缓存页##
[root@ipython ~]# echo "1">/proc/sys/vm/drop_caches
 
##再来看看##
[root@ipython ~]# free -m
[root@ipython ~]# free -hl


Linux防火墙(iptables)的开启与关闭

Linux中的防火墙主要是对iptables的设置和管理.

1. Linux防火墙(Iptables)重启系统生效

开启： chkconfig iptables on  
 
关闭： chkconfig iptables off  
 
2.Linux防火墙(Iptables) 即时生效，重启后失效

开启： service iptables start  
 
关闭： service iptables stop  
 
需要说明的是对于Linux下的其它服务都可以用以上命令执行开启和关闭操作。

在开启了Linux防火墙(Iptables)时，做如下设置，开启25和110端口，

修改/etc/sysconfig/iptables 文件，添加以下内容：

-A RH-Firewall-1-INPUT -m state --state NEW -p tcp -m tcp --dport 25 --syn -j ACCEPT  
 
-A RH-Firewall-1-INPUT -m state --state NEW -p tcp -m tcp --dport 110 --syn -j 