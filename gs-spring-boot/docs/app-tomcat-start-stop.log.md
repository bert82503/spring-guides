

熟悉又陌生的应用日志
==========
> 2019-08-24

本文对**应用日志**进行"解剖"，刨根问底应用启动流程及其背后实现。


## 0.背景
实验环境
* spring-boot-starter-parent:1.5.19.RELEASE

日志信息：什么时间点(what)，哪个线程(who)，哪个类(who)，做了哪些事(how)。
```
<pattern>%date [%thread] %level %logger - %msg%n</pattern>
```

三步走：
1. 应用日志解读
2. 应用日志剖析与框架源码
3. 框架源码剖析与实现原理
<!-- 4. 框架设计思想与理念 -->


## 1.应用日志解读

```
# 1.应用启动阶段

## SpringBootBanner#printBanner
## 打印Spring框架宣传横幅，包含框架名和版本号(可自定义，把框架做成产品思维)

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v1.5.19.RELEASE)

## "main"主线程
## StartupInfoLogger#getStartupMessage
## 开始启动 [应用名/应用源类名] [v应用源类版本] on [主机名] with PID [进程ID] (应用程序上下文[[应用程序主目录] started by [用户名] in [用户目录]])
## Q：为何是由应用源类HelloApplication打印这条日志，而不是StartupInfoLogger？
2019-08-24 15:40:54,679 [main] INFO spring.guides.hello.HelloApplication - Starting HelloApplication on dannongdeMacBook-Pro.local with PID 61268 (/Users/dannong/Documents/workspace/GitHub/spring-guides/gs-spring-boot/target/classes started by dannong in /Users/dannong/Documents/workspace/GitHub/spring-guides)
## SpringApplication#logStartupProfileInfo
## 没有活动的配置文件集，回退到默认的配置文件：[defaultProfiles] (`application-default.properties`)
## Q："default"定义在哪里？
## Q：为何是由应用源类HelloApplication打印这条日志，而不是SpringApplication？
2019-08-24 15:40:54,831 [main] INFO spring.guides.hello.HelloApplication - No active profile set, falling back to default profiles: default
## AbstractApplicationContext#prepareRefresh => AbstractApplicationContext#toString
## 开始刷新 [应用上下文的显示名称/AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d/基于注解配置的内嵌的web应用上下文对象]: startup date [启动时的系统时间]; [root of context hierarchy/应用上下文层次结构的根]
## Q：为何是由AnnotationConfigEmbeddedWebApplicationContext打印这条日志，而不是AbstractApplicationContext？
2019-08-24 15:40:55,095 [main] INFO org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext - Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d: startup date [Sat Aug 24 15:40:55 CST 2019]; root of context hierarchy
## "background-preinit"线程
## BackgroundPreinitializer#performPreinitialization
2019-08-24 15:40:55,932 [background-preinit] INFO org.hibernate.validator.internal.util.Version - HV000001: Hibernate Validator 5.3.6.Final
## TomcatEmbeddedServletContainer#initialize
## [Tomcat initialized with port(s)/开始初始化Tomcat]: [端口 (scheme/协议)]
## Q："8080 (http)"定义在哪里？
2019-08-24 15:40:58,023 [main] INFO org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer - Tomcat initialized with port(s): 8080 (http)
## apache.*.AbstractProtocol#init，"abstractProtocolHandler.init"
## 开始初始化HTTP协议处理器 [http-nio-8080/协议处理器的名称前缀-端点端口]
## Q："http-nio-8080"定义在哪里？
## Q：为何是由Http11NioProtocol打印这条日志，而不是AbstractProtocol？
2019-08-24 15:40:58,052 [main] INFO org.apache.coyote.http11.Http11NioProtocol - Initializing ProtocolHandler ["http-nio-8080"]
## StandardService#startInternal，"standardService.start.name"
## 开始启动Tomcat服务
## Q："Tomcat"服务名称定义在哪里？
2019-08-24 15:40:58,076 [main] INFO org.apache.catalina.core.StandardService - Starting service [Tomcat]
## StandardEngine#startInternal
## 开始启动Servlet引擎容器: [Apache Tomcat/8.5.37:tomcat服务器信息/版本]
2019-08-24 15:40:58,076 [main] INFO org.apache.catalina.core.StandardEngine - Starting Servlet Engine: Apache Tomcat/8.5.37
## "localhost-startStop-1"线程(ContainerBase#initInternal)，"localhost"是Host主机容器的名称(Tomcat#getHost)
## EmbeddedWebApplicationContext#prepareEmbeddedWebApplicationContext
## 开始初始化Spring内嵌的web应用上下文
## Q：为何是由"ContainerBase.[Tomcat].[localhost].[/]"打印这条日志？
## Q："[Tomcat].[localhost].[/]"从何而来？
2019-08-24 15:40:58,270 [localhost-startStop-1] INFO org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/] - Initializing Spring embedded WebApplicationContext
## EmbeddedWebApplicationContext#prepareEmbeddedWebApplicationContext
## 层次结构的根web应用上下文: 初始化完成时间
2019-08-24 15:40:58,271 [localhost-startStop-1] INFO org.springframework.web.context.ContextLoader - Root WebApplicationContext: initialization completed in 3176 ms
## ServletRegistrationBean#onStartup
## 映射servlet/服务器端程序：'[name/服务器端程序的名称]' to [/] (urlMappings)
## Q："dispatcherServlet"请求分发器定义在哪里？
2019-08-24 15:40:59,259 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.ServletRegistrationBean - Mapping servlet: 'dispatcherServlet' to [/]
## AbstractFilterRegistrationBean#configure
## 映射filter/过滤器: '[filterName/过滤器的名称]' to: [/*] (DEFAULT_URL_MAPPINGS)
## Q："metricsFilter"定义在哪里？
2019-08-24 15:40:59,271 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'metricsFilter' to: [/*]
## Q："characterEncodingFilter"定义在哪里？
2019-08-24 15:40:59,281 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'characterEncodingFilter' to: [/*]
## Q："hiddenHttpMethodFilter"定义在哪里？
2019-08-24 15:40:59,281 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
## Q："httpPutFormContentFilter"定义在哪里？
2019-08-24 15:40:59,281 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'httpPutFormContentFilter' to: [/*]
## Q："requestContextFilter"定义在哪里？
2019-08-24 15:40:59,282 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'requestContextFilter' to: [/*]
## Q："webRequestLoggingFilter"定义在哪里？
2019-08-24 15:40:59,282 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'webRequestLoggingFilter' to: [/*]
## Q："applicationContextIdFilter"定义在哪里？
2019-08-24 15:40:59,282 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'applicationContextIdFilter' to: [/*]
## HelloApplication.HelloApplication
## "HelloApplication$$EnhancerBySpringCGLIB$$2b2fce09@33d05366"，被CGLIB增强而非同一个类型
2019-08-24 15:40:59,397 [main] INFO spring.guides.hello.HelloApplication - create spring.guides.hello.HelloApplication$$EnhancerBySpringCGLIB$$2b2fce09@33d05366
## HomeController.HomeController
2019-08-24 15:40:59,402 [main] INFO spring.guides.hello.web.HomeController - create spring.guides.hello.web.HomeController@32c0915e
## RequestMappingHandlerAdapter#initControllerAdviceCache
## 寻找@ControllerAdvice/控制器通知注解: [应用上下文/ApplicationObjectSupport#getApplicationContext]
2019-08-24 15:41:00,143 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d: startup date [Sat Aug 24 15:40:55 CST 2019]; root of context hierarchy
## AbstractHandlerMethodMapping.MappingRegistry#register
## Mapped "[mapping/RequestMappingInfo/请求映射条件的契约]" onto [handlerMethod/由方法和bean组成的处理方法]
## RequestMappingHandlerMapping/请求方法映射处理器映射表
## 控制器：HomeController.index()，BasicErrorController.error()，BasicErrorController.errorHtml()
## Q：为何是由RequestMappingHandlerMapping打印这条日志，而不是AbstractHandlerMethodMapping？
2019-08-24 15:41:00,341 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/],methods=[GET],produces=[application/json;charset=UTF-8]}" onto public java.lang.String spring.guides.hello.web.HomeController.index()
2019-08-24 15:41:00,365 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2019-08-24 15:41:00,366 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
## AbstractUrlHandlerMapping#registerHandler(String, Object)
## Mapped URL path [urlPath] onto [getHandlerDescription(handler))/](handler of type [handler.getClass()])/映射URL路径到bean方法处理器
## ResourceHttpRequestHandler/静态资源请求处理器
2019-08-24 15:41:00,516 [main] INFO org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-24 15:41:00,516 [main] INFO org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-24 15:41:00,641 [main] INFO org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
## AbstractHandlerMethodMapping.MappingRegistry#register
## Mapped "[mapping/RequestMappingInfo/请求映射条件的契约]" onto [handlerMethod/由方法和bean组成的处理方法]
## EndpointHandlerMapping/端点处理器映射表
## 端点：/env，/configprops，/info，/dump，/trace，/auditevents，/heapdump，/loggers，/health，/mappings，/beans，/autoconfig，/metrics
## Q：为何是由EndpointHandlerMapping打印这条日志，而不是AbstractHandlerMethodMapping？
2019-08-24 15:41:01,034 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/env/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EnvironmentMvcEndpoint.value(java.lang.String)
2019-08-24 15:41:01,034 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/env || /env.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,035 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/configprops || /configprops.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,036 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/info || /info.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,036 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/dump || /dump.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,037 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/trace || /trace.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,039 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/auditevents || /auditevents.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public org.springframework.http.ResponseEntity<?> org.springframework.boot.actuate.endpoint.mvc.AuditEventsMvcEndpoint.findByPrincipalAndAfterAndType(java.lang.String,java.util.Date,java.lang.String)
2019-08-24 15:41:01,040 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/heapdump || /heapdump.json],methods=[GET],produces=[application/octet-stream]}" onto public void org.springframework.boot.actuate.endpoint.mvc.HeapdumpMvcEndpoint.invoke(boolean,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse) throws java.io.IOException,javax.servlet.ServletException
2019-08-24 15:41:01,044 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/loggers/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.LoggersMvcEndpoint.get(java.lang.String)
2019-08-24 15:41:01,045 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/loggers/{name:.*}],methods=[POST],consumes=[application/vnd.spring-boot.actuator.v1+json || application/json],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.LoggersMvcEndpoint.set(java.lang.String,java.util.Map<java.lang.String, java.lang.String>)
2019-08-24 15:41:01,045 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/loggers || /loggers.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,045 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/health || /health.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.HealthMvcEndpoint.invoke(javax.servlet.http.HttpServletRequest,java.security.Principal)
2019-08-24 15:41:01,046 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/mappings || /mappings.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,046 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/beans || /beans.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,047 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/autoconfig || /autoconfig.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,047 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/metrics/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.MetricsMvcEndpoint.value(java.lang.String)
2019-08-24 15:41:01,047 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/metrics || /metrics.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
## MBeanExporter#afterSingletonsInstantiated
## 在启动时为JMX暴露注册的beans
## Q：为何是由AnnotationMBeanExporter打印这条日志，而不是MBeanExporter？
2019-08-24 15:41:01,202 [main] INFO org.springframework.jmx.export.annotation.AnnotationMBeanExporter - Registering beans for JMX exposure on startup
## MBeanExporter#afterSingletonsInstantiated
## EndpointMBeanExporter/端点JMX暴露者
2019-08-24 15:41:01,208 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Registering beans for JMX exposure on startup
## DefaultLifecycleProcessor.LifecycleGroup#start
## Starting beans in phase [0/phase]/在阶段0中开始启动beans
## DefaultLifecycleProcessor/组件生命周期处理器策略接口的默认实现
2019-08-24 15:41:01,213 [main] INFO org.springframework.context.support.DefaultLifecycleProcessor - Starting beans in phase 0
## MBeanExporter#registerBeanInstance
## Located managed bean 'beanKey': registering with JMX server as MBean [objectName]
## JMX暴露的端点：auditEventsEndpoint，requestMappingEndpoint，environmentEndpoint，healthEndpoint，beansEndpoint，infoEndpoint，loggersEndpoint，metricsEndpoint，traceEndpoint，dumpEndpoint，autoConfigurationReportEndpoint，configurationPropertiesReportEndpoint
2019-08-24 15:41:01,215 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'auditEventsEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=auditEventsEndpoint]
2019-08-24 15:41:01,242 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'requestMappingEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=requestMappingEndpoint]
2019-08-24 15:41:01,266 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'environmentEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=environmentEndpoint]
2019-08-24 15:41:01,274 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'healthEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=healthEndpoint]
2019-08-24 15:41:01,289 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'beansEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=beansEndpoint]
2019-08-24 15:41:01,293 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'infoEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=infoEndpoint]
2019-08-24 15:41:01,297 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'loggersEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=loggersEndpoint]
2019-08-24 15:41:01,310 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'metricsEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=metricsEndpoint]
2019-08-24 15:41:01,315 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'traceEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=traceEndpoint]
2019-08-24 15:41:01,324 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'dumpEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=dumpEndpoint]
2019-08-24 15:41:01,328 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'autoConfigurationReportEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=autoConfigurationReportEndpoint]
2019-08-24 15:41:01,332 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'configurationPropertiesReportEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=configurationPropertiesReportEndpoint]
## apache.*.AbstractProtocol#start，"abstractProtocolHandler.start"
## 开始启动HTTP协议处理器 [http-nio-8080/协议处理器的名称前缀-端点端口]
2019-08-24 15:41:01,368 [main] INFO org.apache.coyote.http11.Http11NioProtocol - Starting ProtocolHandler ["http-nio-8080"]
## NioSelectorPool#getSharedSelector
## 使用共享的选择器进行servlet写/读
2019-08-24 15:41:01,391 [main] INFO org.apache.tomcat.util.net.NioSelectorPool - Using a shared selector for servlet write/read
## TomcatEmbeddedServletContainer#start
## [Tomcat started on port(s)/Tomcat启动完成]: [端口 (scheme/协议)]
2019-08-24 15:41:01,418 [main] INFO org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer - Tomcat started on port(s): 8080 (http)
## StartupInfoLogger#getStartedMessage
## 应用启动完成 [应用名/应用源类名] in [应用启动总时间] seconds (JVM running for [JVM的运行时间])
## Q：为何是由应用源类HelloApplication打印这条日志，而不是StartupInfoLogger？
2019-08-24 15:41:01,426 [main] INFO spring.guides.hello.HelloApplication - Started HelloApplication in 7.79 seconds (JVM running for 9.384)


# 2.第一个HTTP请求(FrameworkServlet延迟初始化)

## "http-nio-8080-exec-{threadNumber}"线程(AbstractEndpoint#createExecutor)
## Q："http-nio-8080-exec-{threadNumber}"定义在哪里？
## FrameworkServlet#initServletBean
## 开始初始化Spring FrameworkServlet 'dispatcherServlet/servletName'
2019-08-24 15:41:12,870 [http-nio-8080-exec-1] INFO org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/] - Initializing Spring FrameworkServlet 'dispatcherServlet'
## FrameworkServlet#initServletBean
## FrameworkServlet 'dispatcherServlet/servletName': 初始化开始
## Q：为何是由应用源类DispatcherServlet打印这条日志，而不是FrameworkServlet？
2019-08-24 15:41:12,870 [http-nio-8080-exec-1] INFO org.springframework.web.servlet.DispatcherServlet - FrameworkServlet 'dispatcherServlet': initialization started
## FrameworkServlet#initServletBean
## FrameworkServlet 'dispatcherServlet/servletName': 初始化完成
2019-08-24 15:41:12,896 [http-nio-8080-exec-1] INFO org.springframework.web.servlet.DispatcherServlet - FrameworkServlet 'dispatcherServlet': initialization completed in 25 ms


# 3.应用关闭阶段

## "Thread-{threadNumber}"线程/JVM关闭钩子(SpringApplication#refreshContext，Thread#Thread())
## Q："Thread-{threadNumber}"定义在哪里？
## AbstractApplicationContext#doClose
## 开始关闭 [应用上下文的显示名称/AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d/基于注解配置的内嵌的web应用上下文对象]: startup date [启动时的系统时间]; [root of context hierarchy/应用上下文层次结构的根]
2019-08-24 15:41:24,842 [Thread-5] INFO org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext - Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d: startup date [Sat Aug 24 15:40:55 CST 2019]; root of context hierarchy
## DefaultLifecycleProcessor.LifecycleGroup#stop
## Stopping beans in phase [0/phase]/在阶段0中开始停止beans
## DefaultLifecycleProcessor/组件生命周期处理器策略接口的默认实现
2019-08-24 15:41:24,844 [Thread-5] INFO org.springframework.context.support.DefaultLifecycleProcessor - Stopping beans in phase 0
## MBeanExporter#destroy
## 在关闭时注销JMX暴露的beans
2019-08-24 15:41:24,846 [Thread-5] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Unregistering JMX-exposed beans on shutdown
## MBeanRegistrationSupport#unregisterBeans
## 开始注销JMX暴露的beans
2019-08-24 15:41:24,847 [Thread-5] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Unregistering JMX-exposed beans
## MBeanExporter#destroy
## 在关闭时注销JMX暴露的beans
2019-08-24 15:41:24,847 [Thread-5] INFO org.springframework.jmx.export.annotation.AnnotationMBeanExporter - Unregistering JMX-exposed beans on shutdown

## 进程以退出码130结束 (由信号2中断: SIGINT)
Process finished with exit code 130 (interrupted by signal 2: SIGINT)

```


## 2.应用日志剖析与框架源码

```
# 1.应用启动阶段
## SpringBootBanner#printBanner
## 打印Spring框架宣传横幅，包含框架名和版本号(可自定义，把框架做成产品思维)

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v1.5.19.RELEASE)

## "main"主线程
## StartupInfoLogger#getStartupMessage
## 开始启动 [应用名/应用源类名] [v应用源类版本] on [主机名] with PID [进程ID] (应用程序上下文[[应用程序主目录] started by [用户名] in [用户目录]])
## Q：为何是由应用源类HelloApplication打印这条日志，而不是StartupInfoLogger？答案见：SpringApplication#logStartupInfo => SpringApplication#getApplicationLog
2019-08-24 15:40:54,679 [main] INFO spring.guides.hello.HelloApplication - Starting HelloApplication on dannongdeMacBook-Pro.local with PID 61268 (/Users/dannong/Documents/workspace/GitHub/spring-guides/gs-spring-boot/target/classes started by dannong in /Users/dannong/Documents/workspace/GitHub/spring-guides)

## SpringApplication#logStartupProfileInfo
## 没有活动的配置文件集，回退到默认的配置文件：[defaultProfiles] (`application-default.properties`)
## Q："default"定义在哪里？答案见：AbstractEnvironment#getDefaultProfiles => AbstractEnvironment#getReservedDefaultProfiles
## Q：为何是由应用源类HelloApplication打印这条日志，而不是SpringApplication？答案见：SpringApplication#getApplicationLog
2019-08-24 15:40:54,831 [main] INFO spring.guides.hello.HelloApplication - No active profile set, falling back to default profiles: default

## AbstractApplicationContext#prepareRefresh => AbstractApplicationContext#toString
## 开始刷新 [应用上下文的显示名称/AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d/基于注解配置的内嵌的web应用上下文对象]: startup date [启动时的系统时间]; [root of context hierarchy/应用上下文层次结构的根]
## Q：为何是由AnnotationConfigEmbeddedWebApplicationContext打印这条日志，而不是AbstractApplicationContext？答案见：`Log logger = LogFactory.getLog(getClass())`
2019-08-24 15:40:55,095 [main] INFO org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext - Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d: startup date [Sat Aug 24 15:40:55 CST 2019]; root of context hierarchy

## "background-preinit"线程
## BackgroundPreinitializer#performPreinitialization
2019-08-24 15:40:55,932 [background-preinit] INFO org.hibernate.validator.internal.util.Version - HV000001: Hibernate Validator 5.3.6.Final

## TomcatEmbeddedServletContainer#initialize
## [Tomcat initialized with port(s)/开始初始化Tomcat]: [端口 (scheme/协议)]
## Q："8080 (http)"定义在哪里？答案见：TomcatEmbeddedServletContainer#getPortsDescription，"8080"在Tomcat#getConnector，"http"在Connector#scheme
2019-08-24 15:40:58,023 [main] INFO org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer - Tomcat initialized with port(s): 8080 (http)

## apache.*.AbstractProtocol#init，"abstractProtocolHandler.init"
## 开始初始化HTTP协议处理器 [http-nio-8080/协议处理器的名称前缀-端点端口]
## Q："http-nio-8080"定义在哪里？答案见：AbstractProtocol#getNameInternal，"http-nio"在Http11NioProtocol#getNamePrefix，"8080"在ServerProperties#port
## Q：为何是由Http11NioProtocol打印这条日志，而不是AbstractProtocol？答案见：Http11NioProtocol#getLog
2019-08-24 15:40:58,052 [main] INFO org.apache.coyote.http11.Http11NioProtocol - Initializing ProtocolHandler ["http-nio-8080"]

## StandardService#startInternal，"standardService.start.name"
## 开始启动Tomcat服务 [Tomcat/Service的名称]
## Q：Tomcat服务名称定义在哪里？答案见：Tomcat#getServer
2019-08-24 15:40:58,076 [main] INFO org.apache.catalina.core.StandardService - Starting service [Tomcat]

## StandardEngine#startInternal
## 开始启动Servlet引擎容器: [Apache Tomcat/8.5.37:tomcat服务器信息/版本]
2019-08-24 15:40:58,076 [main] INFO org.apache.catalina.core.StandardEngine - Starting Servlet Engine: Apache Tomcat/8.5.37

## "localhost-startStop-1"线程(ContainerBase#initInternal)，"localhost"是Host主机容器的名称(Tomcat#getHost)
## EmbeddedWebApplicationContext#prepareEmbeddedWebApplicationContext
## 开始初始化Spring内嵌的web应用上下文
## Q：为何是由"ContainerBase.[Tomcat].[localhost].[/]"打印这条日志？答案见：ApplicationContext#log(String) => ContainerBase#getLogger => ContainerBase#getLogName
## Q："[Tomcat].[localhost].[/]"从何而来？"Tomcat"是Engine引擎容器的名称(Tomcat#getEngine)，"localhost"是Host主机容器的名称(Tomcat#getHost)
2019-08-24 15:40:58,270 [localhost-startStop-1] INFO org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/] - Initializing Spring embedded WebApplicationContext

## EmbeddedWebApplicationContext#prepareEmbeddedWebApplicationContext
## 层次结构的根web应用上下文: 初始化完成时间
2019-08-24 15:40:58,271 [localhost-startStop-1] INFO org.springframework.web.context.ContextLoader - Root WebApplicationContext: initialization completed in 3176 ms

## ServletRegistrationBean#onStartup
## 映射servlet/服务器端程序：'[name/服务器端程序的名称]' to [/] (urlMappings)
## Q："dispatcherServlet"请求分发器定义在哪里？答案见：DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration => DispatcherServletAutoConfiguration.DispatcherServletConfiguration#dispatcherServlet
2019-08-24 15:40:59,259 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.ServletRegistrationBean - Mapping servlet: 'dispatcherServlet' to [/]

## AbstractFilterRegistrationBean#configure
## 映射filter/过滤器: '[filterName/过滤器的名称]' to: [/*] (DEFAULT_URL_MAPPINGS)
## Q："metricsFilter"定义在哪里？答案见：MetricFilterAutoConfiguration#metricsFilter
2019-08-24 15:40:59,271 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'metricsFilter' to: [/*]
## Q："characterEncodingFilter"定义在哪里？答案见：HttpEncodingAutoConfiguration#characterEncodingFilter
2019-08-24 15:40:59,281 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'characterEncodingFilter' to: [/*]
## Q："hiddenHttpMethodFilter"定义在哪里？答案见：WebMvcAutoConfiguration#hiddenHttpMethodFilter
2019-08-24 15:40:59,281 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
## Q："httpPutFormContentFilter"定义在哪里？答案见：WebMvcAutoConfiguration#httpPutFormContentFilter
2019-08-24 15:40:59,281 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'httpPutFormContentFilter' to: [/*]
## Q："requestContextFilter"定义在哪里？答案见：WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#requestContextFilter
2019-08-24 15:40:59,282 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'requestContextFilter' to: [/*]
## Q："webRequestLoggingFilter"定义在哪里？答案见：TraceWebFilterAutoConfiguration#webRequestLoggingFilter
2019-08-24 15:40:59,282 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'webRequestLoggingFilter' to: [/*]
## Q："applicationContextIdFilter"定义在哪里？答案见：EndpointWebMvcAutoConfiguration.ApplicationContextFilterConfiguration#applicationContextIdFilter
2019-08-24 15:40:59,282 [localhost-startStop-1] INFO org.springframework.boot.web.servlet.FilterRegistrationBean - Mapping filter: 'applicationContextIdFilter' to: [/*]

## HelloApplication.HelloApplication
## "HelloApplication$$EnhancerBySpringCGLIB$$2b2fce09@33d05366"，被CGLIB增强而非同一个类型
2019-08-24 15:40:59,397 [main] INFO spring.guides.hello.HelloApplication - create spring.guides.hello.HelloApplication$$EnhancerBySpringCGLIB$$2b2fce09@33d05366
## HomeController.HomeController
2019-08-24 15:40:59,402 [main] INFO spring.guides.hello.web.HomeController - create spring.guides.hello.web.HomeController@32c0915e

## RequestMappingHandlerAdapter#initControllerAdviceCache
## 寻找@ControllerAdvice/控制器通知注解: [应用上下文/ApplicationObjectSupport#getApplicationContext]
2019-08-24 15:41:00,143 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d: startup date [Sat Aug 24 15:40:55 CST 2019]; root of context hierarchy

## AbstractHandlerMethodMapping.MappingRegistry#register
## Mapped "[mapping/RequestMappingInfo/请求映射条件的契约]" onto [handlerMethod/由方法和bean组成的处理方法]
## RequestMappingHandlerMapping/请求方法映射处理器映射表
## 控制器：HomeController.index()，BasicErrorController.error()，BasicErrorController.errorHtml()
## Q：为何是由RequestMappingHandlerMapping打印这条日志，而不是AbstractHandlerMethodMapping？答案见：`Log logger = LogFactory.getLog(getClass())`
2019-08-24 15:41:00,341 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/],methods=[GET],produces=[application/json;charset=UTF-8]}" onto public java.lang.String spring.guides.hello.web.HomeController.index()
2019-08-24 15:41:00,365 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2019-08-24 15:41:00,366 [main] INFO org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)

## AbstractUrlHandlerMapping#registerHandler(String, Object)
## Mapped URL path [urlPath] onto [getHandlerDescription(handler))/](handler of type [handler.getClass()])/映射URL路径到bean方法处理器
## ResourceHttpRequestHandler/静态资源请求处理器
2019-08-24 15:41:00,516 [main] INFO org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-24 15:41:00,516 [main] INFO org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2019-08-24 15:41:00,641 [main] INFO org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]

## AbstractHandlerMethodMapping.MappingRegistry#register
## Mapped "[mapping/RequestMappingInfo/请求映射条件的契约]" onto [handlerMethod/由方法和bean组成的处理方法]
## EndpointHandlerMapping/端点处理器映射表
## 端点：/env，/configprops，/info，/dump，/trace，/auditevents，/heapdump，/loggers，/health，/mappings，/beans，/autoconfig，/metrics
## Q：为何是由EndpointHandlerMapping打印这条日志，而不是AbstractHandlerMethodMapping？答案见：`Log logger = LogFactory.getLog(getClass());`
2019-08-24 15:41:01,034 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/env/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EnvironmentMvcEndpoint.value(java.lang.String)
2019-08-24 15:41:01,034 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/env || /env.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,035 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/configprops || /configprops.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,036 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/info || /info.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,036 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/dump || /dump.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,037 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/trace || /trace.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,039 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/auditevents || /auditevents.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public org.springframework.http.ResponseEntity<?> org.springframework.boot.actuate.endpoint.mvc.AuditEventsMvcEndpoint.findByPrincipalAndAfterAndType(java.lang.String,java.util.Date,java.lang.String)
2019-08-24 15:41:01,040 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/heapdump || /heapdump.json],methods=[GET],produces=[application/octet-stream]}" onto public void org.springframework.boot.actuate.endpoint.mvc.HeapdumpMvcEndpoint.invoke(boolean,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse) throws java.io.IOException,javax.servlet.ServletException
2019-08-24 15:41:01,044 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/loggers/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.LoggersMvcEndpoint.get(java.lang.String)
2019-08-24 15:41:01,045 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/loggers/{name:.*}],methods=[POST],consumes=[application/vnd.spring-boot.actuator.v1+json || application/json],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.LoggersMvcEndpoint.set(java.lang.String,java.util.Map<java.lang.String, java.lang.String>)
2019-08-24 15:41:01,045 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/loggers || /loggers.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,045 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/health || /health.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.HealthMvcEndpoint.invoke(javax.servlet.http.HttpServletRequest,java.security.Principal)
2019-08-24 15:41:01,046 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/mappings || /mappings.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,046 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/beans || /beans.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,047 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/autoconfig || /autoconfig.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2019-08-24 15:41:01,047 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/metrics/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.MetricsMvcEndpoint.value(java.lang.String)
2019-08-24 15:41:01,047 [main] INFO org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping - Mapped "{[/metrics || /metrics.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()

## MBeanExporter#afterSingletonsInstantiated
## 在启动时为JMX暴露注册的beans
## Q：为何是由AnnotationMBeanExporter打印这条日志，而不是MBeanExporter？答案见：`Log logger = LogFactory.getLog(getClass())`
2019-08-24 15:41:01,202 [main] INFO org.springframework.jmx.export.annotation.AnnotationMBeanExporter - Registering beans for JMX exposure on startup

## MBeanExporter#afterSingletonsInstantiated
## EndpointMBeanExporter/端点JMX暴露者
2019-08-24 15:41:01,208 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Registering beans for JMX exposure on startup

## DefaultLifecycleProcessor.LifecycleGroup#start
## Starting beans in phase [0/phase]/在阶段0中开始启动beans
## DefaultLifecycleProcessor/组件生命周期处理器策略接口的默认实现
2019-08-24 15:41:01,213 [main] INFO org.springframework.context.support.DefaultLifecycleProcessor - Starting beans in phase 0

## MBeanExporter#registerBeanInstance
## Located managed bean 'beanKey': registering with JMX server as MBean [objectName]
## JMX暴露的端点：auditEventsEndpoint，requestMappingEndpoint，environmentEndpoint，healthEndpoint，beansEndpoint，infoEndpoint，loggersEndpoint，metricsEndpoint，traceEndpoint，dumpEndpoint，autoConfigurationReportEndpoint，configurationPropertiesReportEndpoint
2019-08-24 15:41:01,215 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'auditEventsEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=auditEventsEndpoint]
2019-08-24 15:41:01,242 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'requestMappingEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=requestMappingEndpoint]
2019-08-24 15:41:01,266 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'environmentEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=environmentEndpoint]
2019-08-24 15:41:01,274 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'healthEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=healthEndpoint]
2019-08-24 15:41:01,289 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'beansEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=beansEndpoint]
2019-08-24 15:41:01,293 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'infoEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=infoEndpoint]
2019-08-24 15:41:01,297 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'loggersEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=loggersEndpoint]
2019-08-24 15:41:01,310 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'metricsEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=metricsEndpoint]
2019-08-24 15:41:01,315 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'traceEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=traceEndpoint]
2019-08-24 15:41:01,324 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'dumpEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=dumpEndpoint]
2019-08-24 15:41:01,328 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'autoConfigurationReportEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=autoConfigurationReportEndpoint]
2019-08-24 15:41:01,332 [main] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Located managed bean 'configurationPropertiesReportEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=configurationPropertiesReportEndpoint]

## apache.*.AbstractProtocol#start，"abstractProtocolHandler.start"
## 开始启动HTTP协议处理器 [http-nio-8080/协议处理器的名称前缀-端点端口]
2019-08-24 15:41:01,368 [main] INFO org.apache.coyote.http11.Http11NioProtocol - Starting ProtocolHandler ["http-nio-8080"]

## NioSelectorPool#getSharedSelector
## 使用共享的选择器进行servlet写/读
2019-08-24 15:41:01,391 [main] INFO org.apache.tomcat.util.net.NioSelectorPool - Using a shared selector for servlet write/read

## TomcatEmbeddedServletContainer#start
## [Tomcat started on port(s)/Tomcat启动完成]: [端口 (scheme/协议)]
2019-08-24 15:41:01,418 [main] INFO org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer - Tomcat started on port(s): 8080 (http)

## StartupInfoLogger#getStartedMessage
## 应用启动完成 [应用名/应用源类名] in [应用启动总时间] seconds (JVM running for [JVM的运行时间])
## Q：为何是由应用源类HelloApplication打印这条日志，而不是StartupInfoLogger？
2019-08-24 15:41:01,426 [main] INFO spring.guides.hello.HelloApplication - Started HelloApplication in 7.79 seconds (JVM running for 9.384)


# 2.第一个HTTP请求(FrameworkServlet延迟初始化)

## "http-nio-8080-exec-{threadNumber}"线程
## Q："http-nio-8080-exec-{threadNumber}"定义在哪里？答案见：AbstractEndpoint#createExecutor，AbstractProtocol#init => AbstractProtocol#getNameInternal => AbstractEndpoint#setName
## FrameworkServlet#initServletBean
## 开始初始化Spring FrameworkServlet 'dispatcherServlet/servletName'
2019-08-24 15:41:12,870 [http-nio-8080-exec-1] INFO org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/] - Initializing Spring FrameworkServlet 'dispatcherServlet'

## FrameworkServlet#initServletBean
## FrameworkServlet 'dispatcherServlet/servletName': 初始化开始
## Q：为何是由应用源类DispatcherServlet打印这条日志，而不是FrameworkServlet？答案见：`Log logger = LogFactory.getLog(getClass())`
2019-08-24 15:41:12,870 [http-nio-8080-exec-1] INFO org.springframework.web.servlet.DispatcherServlet - FrameworkServlet 'dispatcherServlet': initialization started

## FrameworkServlet#initServletBean
## FrameworkServlet 'dispatcherServlet/servletName': 初始化完成
2019-08-24 15:41:12,896 [http-nio-8080-exec-1] INFO org.springframework.web.servlet.DispatcherServlet - FrameworkServlet 'dispatcherServlet': initialization completed in 25 ms


# 3.应用关闭阶段

## "Thread-{threadNumber}"线程/JVM关闭钩子
## Q："Thread-{threadNumber}"定义在哪里？答案见：SpringApplication#refreshContext，Thread#Thread()
## AbstractApplicationContext#doClose
## 开始关闭 [应用上下文的显示名称/AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d/基于注解配置的内嵌的web应用上下文对象]: startup date [启动时的系统时间]; [root of context hierarchy/应用上下文层次结构的根]
2019-08-24 15:41:24,842 [Thread-5] INFO org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext - Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e75aa0d: startup date [Sat Aug 24 15:40:55 CST 2019]; root of context hierarchy

## DefaultLifecycleProcessor.LifecycleGroup#stop
## Stopping beans in phase [0/phase]/在阶段0中开始停止beans
## DefaultLifecycleProcessor/组件生命周期处理器策略接口的默认实现
2019-08-24 15:41:24,844 [Thread-5] INFO org.springframework.context.support.DefaultLifecycleProcessor - Stopping beans in phase 0

## MBeanExporter#destroy
## 在关闭时注销JMX暴露的beans
2019-08-24 15:41:24,846 [Thread-5] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Unregistering JMX-exposed beans on shutdown

## MBeanRegistrationSupport#unregisterBeans
## 开始注销JMX暴露的beans
2019-08-24 15:41:24,847 [Thread-5] INFO org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter - Unregistering JMX-exposed beans

## MBeanExporter#destroy
## 在关闭时注销JMX暴露的beans
2019-08-24 15:41:24,847 [Thread-5] INFO org.springframework.jmx.export.annotation.AnnotationMBeanExporter - Unregistering JMX-exposed beans on shutdown

```


## 3.框架源码剖析与实现原理

