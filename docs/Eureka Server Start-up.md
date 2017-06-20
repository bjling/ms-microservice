Notice : All based on version of 1.5.4.RELEASE

#### Eureka server start-up

SpringApplication类的静态run方法

~~~
	public ConfigurableApplicationContext run(String... args) {
		...
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
		...
	}
~~~

新建对象FailureAnalyzers并且准备上下文信息

...
8. 获取资源配置类，并且进行加载

![source](images/source.png "Optional title")

...

分析DiscoveryClientConfigServiceBootstrapConfiguration


