Notice : All based on version of 1.5.4.RELEASE

#### SpringApplication类的初始化

~~~
    @SpringBootApplication
    public class YppContentApiApplication {
        public static void main(String[] args) {
            SpringApplication.run(YppContentApiApplication.class, args);
        }
    }
~~~

构建ConfigurableApplicationContext上下文

~~~
    public static ConfigurableApplicationContext run(Object source, String... args) {
        return run(new Object[]{source}, args);
    }

    public static ConfigurableApplicationContext run(Object[] sources, String[] args) {
        return (new SpringApplication(sources)).run(args);
    }
~~~

SpringApplication构造函数，可以自定义资源加载器，此处使用的是第一个
~~~
    public SpringApplication(Object... sources) {
        initialize(sources);
    }
    
	public SpringApplication(ResourceLoader resourceLoader, Object... sources) {
		this.resourceLoader = resourceLoader;
		initialize(sources);
	}
~~~

初始化YppContentApiApplication.class
~~~
    private void initialize(Object[] sources) {
        if(sources != null && sources.length > 0) {
            this.sources.addAll(Arrays.asList(sources));
        }

        this.webEnvironment = this.deduceWebEnvironment();
        this.setInitializers(this.getSpringFactoriesInstances(ApplicationContextInitializer.class));
        this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
        this.mainApplicationClass = this.deduceMainApplicationClass();
    }
~~~
推导是否是web项目，true
~~~
	private boolean deduceWebEnvironment() {
		for (String className : WEB_ENVIRONMENT_CLASSES) {
			if (!ClassUtils.isPresent(className, null)) {
				return false;
			}
		}
		return true;
	}
	
    private static final String[] WEB_ENVIRONMENT_CLASSES = { "javax.servlet.Servlet",
            "org.springframework.web.context.ConfigurableWebApplicationContext" };
	
~~~
ApplicationContextInitializer.class，获取当前线程的类加载器
~~~
	private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type) {
		return getSpringFactoriesInstances(type, new Class<?>[] {});
	}

	private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, Object... args) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// Use names and ensure unique to protect against duplicates
		Set<String> names = new LinkedHashSet<String>(
				SpringFactoriesLoader.loadFactoryNames(type, classLoader));
		List<T> instances = createSpringFactoriesInstances(type, parameterTypes,
				classLoader, args, names);
		AnnotationAwareOrderComparator.sort(instances);
		return instances;
	}
~~~
获取META-INF/spring.factories资源路径，返回文件中标明的所有制定类及其子类
~~~
	public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader) {
		String factoryClassName = factoryClass.getName();
		try {
			Enumeration<URL> urls = (classLoader != null ? classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
			List<String> result = new ArrayList<String>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
				String factoryClassNames = properties.getProperty(factoryClassName);
				result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
			}
			return result;
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load [" + factoryClass.getName() +
					"] factories from location [" + FACTORIES_RESOURCE_LOCATION + "]", ex);
		}
	}
	
	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
~~~
例如spring-cloud-netflix-core:
~~~
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.cloud.netflix.archaius.ArchaiusAutoConfiguration,\
org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration,\
org.springframework.cloud.netflix.feign.FeignAutoConfiguration,\
org.springframework.cloud.netflix.feign.encoding.FeignAcceptGzipEncodingAutoConfiguration,\
org.springframework.cloud.netflix.feign.encoding.FeignContentGzipEncodingAutoConfiguration,\
org.springframework.cloud.netflix.hystrix.HystrixAutoConfiguration,\
org.springframework.cloud.netflix.hystrix.security.HystrixSecurityAutoConfiguration,\
org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration,\
org.springframework.cloud.netflix.rx.RxJavaAutoConfiguration,\
org.springframework.cloud.netflix.metrics.servo.ServoMetricsAutoConfiguration

org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker=\
org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerConfiguration

org.springframework.boot.env.EnvironmentPostProcessor=\
org.springframework.cloud.netflix.metrics.ServoEnvironmentPostProcessor
~~~
类加载器与类实例化（Class.forName(xxx.xx.xx)的作用是要求JVM查找并加载指定的类），并按照Order注解排序
isAssignableFrom 是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口。
~~~
    public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError
~~~
~~~
	@SuppressWarnings("unchecked")
	private <T> List<T> createSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, ClassLoader classLoader, Object[] args,
			Set<String> names) {
		List<T> instances = new ArrayList<T>(names.size());
		for (String name : names) {
			try {
				Class<?> instanceClass = ClassUtils.forName(name, classLoader);
				Assert.isAssignable(type, instanceClass);
				Constructor<?> constructor = instanceClass
						.getDeclaredConstructor(parameterTypes);
				T instance = (T) BeanUtils.instantiateClass(constructor, args);
				instances.add(instance);
			}
			catch (Throwable ex) {
				throw new IllegalArgumentException(
						"Cannot instantiate " + type + " : " + name, ex);
			}
		}
		return instances;
	}
~~~

根据运行时异常的栈追踪获取主类
~~~
	private Class<?> deduceMainApplicationClass() {
		try {
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
			for (StackTraceElement stackTraceElement : stackTrace) {
				if ("main".equals(stackTraceElement.getMethodName())) {
					return Class.forName(stackTraceElement.getClassName());
				}
			}
		}
		catch (ClassNotFoundException ex) {
			// Swallow and continue
		}
		return null;
	}
~~~

![stacktrace](images/stacktrace.png "Optional title")


至此，加载ApplicationContextInitializer.class ， ApplicationListener.class所有子类实例

![init](images/init.png "Optional title")


#### SpringApplication类的静态run方法
new SpringApplication(sources).run(args)

~~~
	public ConfigurableApplicationContext run(String... args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		FailureAnalyzers analyzers = null;
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
					args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners,
					applicationArguments);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			analyzers = new FailureAnalyzers(context);
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			listeners.finished(context, null);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass)
						.logStarted(getApplicationLog(), stopWatch);
			}
			return context;
		}
		catch (Throwable ex) {
			handleRunFailure(context, listeners, analyzers, ex);
			throw new IllegalStateException(ex);
		}
	}
~~~