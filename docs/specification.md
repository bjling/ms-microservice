## JAVA编码规范

[阿里巴巴JAVA编码规范](https://yq.aliyun.com/attachment/download/?id=1170)

[Google JAVA编码规范](http://google-styleguide.googlecode.com/svn/trunk/javaguide.html#s1.1-terminology)

## YPP 框架规范

项目结构采用SpringBoot + Spring Cloud

Maven项目结构生成网站[start.spring](http://start.spring.io/)

项目结构采用package方式（非module），例如用户系统

    com.ypp.user
    -----------.controller 对外的rest controller
    -----------.service    服务
    -----------.domain     领域模型（下面包含dto,db包，分别对应数据传输对象与数据库对象）
    -----------.mapper     mybatis的mapper文件
    -----------.common     常量，枚举，异常code   
    -----------.external   依赖的外部接口
    -----------.util       工具类（公司可提供公用的工具包）
    -----------.scheduler  定时任务
    -----------.config     自定义配置（例如数据库，缓存等） 
    -----------.exception  业务异常与全局处理类
    ---------------------.BusinessException
    ---------------------.GlobalExceptionHandler
    
参考Demo：[微服务实践](https://github.com/wangfeishsh/ms-ypp.git)
1. ms-eureka 服务注册中心
2. ms-config 配置服务中心
3. ms-trade  交易服务demo
4. ms-user   用户服务demo
5. [配置文件仓库](https://github.com/wangfeishsh/ypp-config.git)


## Spring Cloud 使用规范

强烈推荐[官方文档](http://projects.spring.io/spring-cloud/)，pom文件采用spring boot 为parent

~~~
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
~~~
#### config

鉴于微服务开发过程中各个环境的不同，使用git作为配置文件中心化管理工具

1. 服务端

Annotation：
~~~
    @EnableConfigServer
    @SpringBootApplication
    public class MsConfigApplication
~~~

application.properties配置：
~~~
    spring.cloud.config.server.git.force-pull=true
    spring.cloud.config.server.git.uri=https://github.com/wangfeishsh/ypp-config.git
    spring.cloud.config.server.git.name=**
    spring.cloud.config.server.git.password=**
~~~

2. 客户端

~~~
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
~~~

在项目中bootstrap.properties文件配置如下：
~~~
    spring.application.name=ms-user
    spring.cloud.config.uri: http://myconfigserver.com
~~~
#### zipkin

主要为日志链路追踪，结合ELK可方便容易查询问题

~~~
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
~~~

#### sleuth

已集成到zipkin

#### hystrix

~~~
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
    </dependency>
~~~

#### security

主要使用oauth2方式

#### feign

~~~
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>
~~~

需要加入注解
~~~
    @EnableDiscoveryClient
    @EnableFeignClients
    public class MsHelloApplication 
~~~

在使用过程中，既可以使用服务名，亦可使用url地址，需要注意可以在注册中心发现的服务一定使用服务名（测试过程可使用url）
~~~
@FeignClient(name = "ms-netty",url = "http://baidu.com")
public interface NettyClient {

    @RequestMapping(value = "/netty", method = RequestMethod.GET)
    String netty();

}
~~~


## MAVEN 使用规范

## GIT 使用规范

[GIT FLOW](http://www.cnblogs.com/cnblogsfans/p/5075073.html)

## Redis使用规范

1. key不要太长，太长导致消耗内存还会降低查询效率，也不要太短，太短会降低key的可读性

2. 使用时在项目readme中需要标志数据类型以及作用

 * strings
 * lists
 * sets
 * sorted sets
 * hashes

3. 命名规范，名字具有一定的描述性，例如用户缓存YPP:USER:{TOKEN},使用冒号做分割

4. 现阶段可评估是否与php的redis集群分开，单独建立自己项目的缓存

5. 缓存中基础数据最好从基础系统中获取，否则会导致数据不一致，另外一些可变的配置无需写入缓存，例如图片的域名地址
 

## 日志规范

[Spring Cloud Sleuth](http://cloud.spring.io/spring-cloud-sleuth/spring-cloud-sleuth.html)

#### 日志格式

1. 文件与控制台格式：
~~~
<property name="CONSOLE_LOG_PATTERN"
              value="%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"/>
~~~

2. Json格式：
~~~
    <pattern>
        <pattern>
            {
            "severity": "%level",
            "service": "${springAppName:-}",
            "trace": "%X{X-B3-TraceId:-}",
            "span": "%X{X-B3-SpanId:-}",
            "parent": "%X{X-B3-ParentSpanId:-}",
            "exportable": "%X{X-Span-Export:-}",
            "pid": "${PID:-}",
            "thread": "%thread",
            "class": "%logger{40}",
            "rest": "%message",
            "exception": "%exception-conversion-word"
            }
        </pattern>
    </pattern>
~~~

#### 日志级别

#### 日志信息不足
1. 系统级别的请求返回值必须记录
2. 关键链路标记好步骤


## MQ使用规范

## Mybatis使用规范

1. 读写分离，mybatis方法名称的规范，增删改查使用的关键字如下，其中select从从库中读取，find从主库读取

* select
* update
* insert
* delete
* find