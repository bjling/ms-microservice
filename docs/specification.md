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
    
参考Demo：[微服务实践](https://github.com/wangfeishsh/ms-microservice.git)


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
