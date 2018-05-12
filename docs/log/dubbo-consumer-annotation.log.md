

`线程`名称描述(实例化方法)
```
main：应用主线程
background-preinit：以后台线程的方式触发Spring Boot应用的早期初始化过程(BackgroundPreinitializer.onApplicationEvent)
Dubbo-Holder：Dubbo持有线程(DubboHolderListener.onApplicationEvent)
localhost-startStop-1：处理Tomcat容器的启动和停止事件的线程(ContainerBase.initInternal)
main-SendThread(127.0.0.1:2181)：ZooKeeper线程，服务传出的请求队列并生成心跳检查(ClientCnxn.ClientCnxn() -> ClientCnxn.SendThread)
main-EventThread：ZooKeeper配置数据事件处理线程(ClientCnxn.ClientCnxn() -> ClientCnxn.EventThread)
http-nio-8080-exec-{num}：Tomcat执行请求的任务线程(AbstractProtocol.getName -> Http11NioProtocol.getNamePrefix)
DubboSharedHandler-thread-1：共享的执行器服务(WrappedChannelHandler.SHARED_EXECUTOR)
DubboShutdownHook：基于JVM关闭钩子的Dubbo关闭线程(AbstractConfig.java:76)
Thread-{num}：基于JVM关闭钩子的Spring应用上下文关闭线程(AbstractApplicationContext.registerShutdownHook)
```

### 启动步骤
```
0. 'Dubbo持有线程'开始运行
1. Web应用上下文扫描Dubbo服务消费者的@Reference注解组件定义
2. 加载本地注册中心存储文件数据
3. 建立与注册中心的套接字连接会话
  1. 注册Dubbo服务消费者URL
  2. 订阅Dubbo服务消费者URL
  3. 通知订阅Dubbo服务消费者URL的URL列表
4. 启动Netty客户端连接到Dubbo服务器
5. 从注册中心URL引用Dubbo服务
```

### 关闭步骤
```
0. 现在运行Dubbo关闭钩子(DubboShutdownHook)
1. 关闭所有注册中心
2. 销毁某个注册中心
  1. 销毁取消的Dubbo服务消费者URL
  2. 销毁取消订阅的Dubbo服务消费者URL
  3. ZooKeeper会话已关闭
  4. ZooKeeper事件处理线程关闭会话
3. 关闭Web应用上下文
4. 在阶段0中停止组件(默认的组件生命周期处理程序)
5. 关闭Netty通信通道
6. 关闭Dubbo连接
```

### 日志
```java
# Spring Boot横幅，包括其版本
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.5.7.RELEASE)

# 日志记录器工厂(log4j)
2017-10-06 17:22:17.539 [main] INFO  c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.log4j.Log4jLoggerAdapter
# 校验器版本(Hibernate Validator 5.3.5.Final)
2017-10-06 17:22:18.044 [background-preinit] INFO  o.h.validator.internal.util.Version - HV000001: Hibernate Validator 5.3.5.Final
# 在哪台机器(dannongdeMacBook-Pro.local)上使用哪个进程(PID 11673)启动哪个应用入口类(DubboAnnotationConsumerApplication)，包括应用所在目录
2017-10-06 17:22:22.633 [main] INFO  s.g.d.DubboAnnotationConsumerApplication - Starting DubboAnnotationConsumerApplication on dannongdeMacBook-Pro.local with PID 11673 (/Users/dannong/Documents/workspace/GitHub/spring-guides/spring-boot-rpc-soa-dubbo-consumer-annotation/target/classes started by dannong in /Users/dannong/Documents/workspace/GitHub/spring-guides)
# 配置文件(profile)设置
2017-10-06 17:22:22.633 [main] INFO  s.g.d.DubboAnnotationConsumerApplication - No active profile set, falling back to default profiles: default
### ======= Dubbo =======
# 'Dubbo持有线程'开始运行(DubboHolderListener)
Dubbo-Holder
# 刷新'基于注解配置嵌入的Web应用上下文(AnnotationConfigEmbeddedWebApplicationContext)'，上下文层次结构的根
2017-10-06 17:22:22.698 [main] INFO  o.s.b.c.e.AnnotationConfigEmbeddedWebApplicationContext - Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@184cf7cf: startup date [Fri Oct 06 17:22:22 CST 2017]; root of context hierarchy
### ======= Tomcat =======
2017-10-06 17:22:24.763 [main] INFO  o.s.b.c.e.t.TomcatEmbeddedServletContainer - Tomcat initialized with port(s): 8080 (http)
2017-10-06 17:22:24.784 [main] INFO  o.a.catalina.core.StandardService - Starting service [Tomcat]
2017-10-06 17:22:24.786 [main] INFO  o.a.catalina.core.StandardEngine - Starting Servlet Engine: Apache Tomcat/8.5.20
2017-10-06 17:22:24.951 [localhost-startStop-1] INFO  o.a.c.c.C.[Tomcat].[localhost].[/] - Initializing Spring embedded WebApplicationContext
2017-10-06 17:22:24.952 [localhost-startStop-1] INFO  o.s.web.context.ContextLoader - Root WebApplicationContext: initialization completed in 2254 ms
2017-10-06 17:22:25.358 [localhost-startStop-1] INFO  o.s.b.w.s.ServletRegistrationBean - Mapping servlet: 'dispatcherServlet' to [/]
2017-10-06 17:22:25.364 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'metricsFilter' to: [/*]
2017-10-06 17:22:25.365 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'characterEncodingFilter' to: [/*]
2017-10-06 17:22:25.366 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2017-10-06 17:22:25.366 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'httpPutFormContentFilter' to: [/*]
2017-10-06 17:22:25.367 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'requestContextFilter' to: [/*]
2017-10-06 17:22:25.367 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'webRequestLoggingFilter' to: [/*]
2017-10-06 17:22:25.367 [localhost-startStop-1] INFO  o.s.b.w.s.FilterRegistrationBean - Mapping filter: 'applicationContextIdFilter' to: [/*]
# 日志记录器工厂(slf4j)
2017-10-06 17:22:25.865 [main] INFO  c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.slf4j.Slf4jLoggerAdapter
# 加载本地注册中心存储文件数据(xxx.DemoService=empty://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?category=configurators&side=provider)
2017-10-06 17:22:31.024 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Load registry store file /Users/dannong/.dubbo/registry.cache, data: {spring.guides.dubbo.service.DemoService=empty://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597}, dubbo version: 2.5.5, current host: 127.0.0.1
### ======= Zookeeper客户端 =======
# 启动Zookeeper注册中心的Curator客户端
2017-10-06 17:22:36.276 [main] INFO  o.a.c.f.imps.CuratorFrameworkImpl - Starting
# ZooKeeper客户端环境(ZK版本、主机名称、JDK信息、OS信息、用户信息、OS内存信息)
2017-10-06 17:22:41.357 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:zookeeper.version=3.5.3-beta-8ce24f9e675cbefffb8f21a47e06b42864475a60, built on 04/03/2017 16:19 GMT
2017-10-06 17:22:41.358 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:host.name=10.1.112.138
2017-10-06 17:22:41.358 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.version=1.8.0_112
2017-10-06 17:22:41.358 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.vendor=Oracle Corporation
2017-10-06 17:22:41.358 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre
2017-10-06 17:22:41.358 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.class.path=.../lib/rt.jar:.../lib/tools.jar:.../spring-guides/spring-boot-rpc-soa-dubbo-provider-xml/target/classes:.../spring-guides/spring-boot-rpc-soa-api/target/classes:.../.m2/repository/com/alibaba/dubbo/2.5.5/dubbo-2.5.5.jar:/Users/dannong/.m2/repository/org/springframework/boot/spring-boot-starter/1.5.7.RELEASE/spring-boot-starter-1.5.7.RELEASE.jar
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.library.path=/Users/dannong/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.io.tmpdir=/var/folders/0y/kzmh046n3bqb07pwdrq_zy_r0000gn/T/
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.compiler=<NA>
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.name=Mac OS X
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.arch=x86_64
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.version=10.12
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.name=dannong
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.home=/Users/dannong
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.dir=/Users/dannong/Documents/workspace/GitHub/spring-guides
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.free=85MB
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.max=1820MB
2017-10-06 17:22:41.359 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.total=173MB
# 初始化客户端连接，包括连接字符串、会话超时、观察者状态
2017-10-06 17:22:41.362 [main] INFO  org.apache.zookeeper.ZooKeeper - Initiating client connection, connectString=127.0.0.1:2181 sessionTimeout=60000 watcher=org.apache.curator.ConnectionState@3d36dff4
# 客户端连接套接字
2017-10-06 17:22:41.372 [main] INFO  o.apache.zookeeper.ClientCnxnSocket - jute.maxbuffer value is 4194304 Bytes
# 打开到ZooKeeper服务器(127.0.0.1/127.0.0.1:2181)的套接字连接(ClientCnxn：管理客户端与服务端的连接)
2017-10-06 17:22:41.384 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Opening socket connection to server 127.0.0.1/127.0.0.1:2181. Will not attempt to authenticate using SASL (unknown error)
# 使用默认的协议模式
2017-10-06 17:22:41.391 [main] INFO  o.a.c.f.imps.CuratorFrameworkImpl - Default schema
# 套接字连接已建立，初始化客户端与服务端的会话(/127.0.0.1:55205 -> 127.0.0.1/127.0.0.1:2181)
2017-10-06 17:22:41.407 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Socket connection established, initiating session, client: /127.0.0.1:55205, server: 127.0.0.1/127.0.0.1:2181
# ZooKeeper服务器(127.0.0.1/127.0.0.1:2181)上的会话建立完成
2017-10-06 17:22:41.420 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Session establishment complete on server 127.0.0.1/127.0.0.1:2181, sessionid = 0x15ee6ce79200025, negotiated timeout = 40000
# 会话状态变换为已连接(CONNECTED)
2017-10-06 17:22:41.427 [main-EventThread] INFO  o.a.c.f.state.ConnectionStateManager - State change: CONNECTED
# 收到新的配置事件(数据：{})
2017-10-06 17:22:41.438 [main-EventThread] INFO  o.a.c.framework.imps.EnsembleTracker - New config event received: {}
# 收到新的配置事件(数据：{})
2017-10-06 17:22:42.393 [main-EventThread] INFO  o.a.c.framework.imps.EnsembleTracker - New config event received: {}
# 注册Dubbo服务消费者URL(
consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=xxx&category=consumers&side=consumer, 当前主机: 10.1.112.138)
2017-10-06 17:22:46.489 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Register: consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=consumers&check=false&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, dubbo version: 2.5.5, current host: 10.1.112.138
# 订阅Dubbo服务消费者URL(consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=xxx&category=providers,configurators,routers&side=consumer)
2017-10-06 17:22:46.519 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Subscribe: consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=providers,configurators,routers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, dubbo version: 2.5.5, current host: 10.1.112.138
# 通知订阅Dubbo服务消费者URL(consumer://xxx)的URL列表(dubbo://xxx/xxx.DemoService?side=provider、empty://10.1.112.138/xxx.DemoService?category=configurators&side=consumer、empty://10.1.112.138/xxx.DemoService?category=routers&side=consumer)
2017-10-06 17:22:46.538 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Notify urls for subscribe url consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=providers,configurators,routers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, urls: [dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, empty://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=configurators&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, empty://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=routers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885], dubbo version: 2.5.5, current host: 10.1.112.138
# 从Netty客户端(10.1.112.1382)成功地连接到Dubbo服务器(/10.1.112.138:20880)，通信通道使用Netty通道(/10.1.112.138:55206 => /10.1.112.138:20880)
2017-10-06 17:22:46.726 [main] INFO  c.a.d.r.transport.AbstractClient -  [DUBBO] Successed connect to server /10.1.112.138:20880 from NettyClient 10.1.112.138 using dubbo version 2.5.5, channel is NettyChannel [channel=[id: 0x40c10b04, /10.1.112.138:55206 => /10.1.112.138:20880]], dubbo version: 2.5.5, current host: 10.1.112.138
# 启动Netty客户端(dannongdeMacBook-Pro.local/10.1.112.138)连接到Dubbo服务器(/10.1.112.138:20880)
2017-10-06 17:22:46.729 [main] INFO  c.a.d.r.transport.AbstractClient -  [DUBBO] Start NettyClient dannongdeMacBook-Pro.local/10.1.112.138 connect to the server /10.1.112.138:20880, dubbo version: 2.5.5, current host: 10.1.112.138
# 从注册中心URL引用Dubbo服务(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?anyhost=true&application=xxx&interface=spring.guides.dubbo.service.DemoService&side=consumer)
2017-10-06 17:22:46.836 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Refer dubbo service spring.guides.dubbo.service.DemoService from url zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-consumer&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11673&retries=1&side=consumer&timeout=1000&timestamp=1507281745885, dubbo version: 2.5.5, current host: 10.1.112.138

2017-10-06 17:22:57.420 [main] INFO  o.a.c.c.C.[Tomcat].[localhost].[/] - jolokia: No access restrictor found, access to any MBean is allowed
### ======= Spring MVC =======
# 寻找@ControllerAdvice
2017-10-06 17:22:57.757 [main] INFO  o.s.w.s.m.m.a.RequestMappingHandlerAdapter - Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@184cf7cf: startup date [Fri Oct 06 17:22:22 CST 2017]; root of context hierarchy
2017-10-06 17:22:57.940 [main] INFO  o.s.w.s.m.m.a.RequestMappingHandlerMapping - Mapped "{[/demo/say],methods=[GET],produces=[application/json;charset=UTF-8]}" onto public java.lang.String spring.guides.dubbo.controller.AnnotationDemoController.say(java.lang.String)
2017-10-06 17:22:57.946 [main] INFO  o.s.w.s.m.m.a.RequestMappingHandlerMapping - Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2017-10-06 17:22:57.947 [main] INFO  o.s.w.s.m.m.a.RequestMappingHandlerMapping - Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2017-10-06 17:22:58.000 [main] INFO  o.s.w.s.h.SimpleUrlHandlerMapping - Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-10-06 17:22:58.001 [main] INFO  o.s.w.s.h.SimpleUrlHandlerMapping - Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-10-06 17:22:58.059 [main] INFO  o.s.w.s.h.SimpleUrlHandlerMapping - Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-10-06 17:22:58.618 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/dump || /dump.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.625 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/heapdump || /heapdump.json],methods=[GET],produces=[application/octet-stream]}" onto public void org.springframework.boot.actuate.endpoint.mvc.HeapdumpMvcEndpoint.invoke(boolean,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse) throws java.io.IOException,javax.servlet.ServletException
2017-10-06 17:22:58.626 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/beans || /beans.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.628 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/mappings || /mappings.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.630 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/loggers/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.LoggersMvcEndpoint.get(java.lang.String)
2017-10-06 17:22:58.631 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/loggers/{name:.*}],methods=[POST],consumes=[application/vnd.spring-boot.actuator.v1+json || application/json],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.LoggersMvcEndpoint.set(java.lang.String,java.util.Map<java.lang.String, java.lang.String>)
2017-10-06 17:22:58.631 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/loggers || /loggers.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.635 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/metrics/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.MetricsMvcEndpoint.value(java.lang.String)
2017-10-06 17:22:58.635 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/metrics || /metrics.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.637 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/jolokia/**]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.actuate.endpoint.mvc.JolokiaMvcEndpoint.handle(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse) throws java.lang.Exception
2017-10-06 17:22:58.638 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/info || /info.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.640 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/autoconfig || /autoconfig.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.641 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/configprops || /configprops.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.642 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/trace || /trace.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
2017-10-06 17:22:58.643 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/health || /health.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.HealthMvcEndpoint.invoke(javax.servlet.http.HttpServletRequest,java.security.Principal)
2017-10-06 17:22:58.644 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/auditevents || /auditevents.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public org.springframework.http.ResponseEntity<?> org.springframework.boot.actuate.endpoint.mvc.AuditEventsMvcEndpoint.findByPrincipalAndAfterAndType(java.lang.String,java.util.Date,java.lang.String)
2017-10-06 17:22:58.653 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/env/{name:.*}],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EnvironmentMvcEndpoint.value(java.lang.String)
2017-10-06 17:22:58.654 [main] INFO  o.s.b.a.e.mvc.EndpointHandlerMapping - Mapped "{[/env || /env.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public java.lang.Object org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter.invoke()
# 在启动时为JMX暴露注册组件
2017-10-06 17:22:58.882 [main] INFO  o.s.j.e.a.AnnotationMBeanExporter - Registering beans for JMX exposure on startup
2017-10-06 17:22:58.896 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Registering beans for JMX exposure on startup
2017-10-06 17:22:58.903 [main] INFO  o.s.c.s.DefaultLifecycleProcessor - Starting beans in phase 0
2017-10-06 17:22:58.908 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'auditEventsEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=auditEventsEndpoint]
2017-10-06 17:22:58.948 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'requestMappingEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=requestMappingEndpoint]
2017-10-06 17:22:58.977 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'environmentEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=environmentEndpoint]
2017-10-06 17:22:59.001 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'healthEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=healthEndpoint]
2017-10-06 17:22:59.012 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'beansEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=beansEndpoint]
2017-10-06 17:22:59.019 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'infoEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=infoEndpoint]
2017-10-06 17:22:59.027 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'loggersEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=loggersEndpoint]
2017-10-06 17:22:59.039 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'metricsEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=metricsEndpoint]
2017-10-06 17:22:59.043 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'traceEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=traceEndpoint]
2017-10-06 17:22:59.047 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'dumpEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=dumpEndpoint]
2017-10-06 17:22:59.053 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'autoConfigurationReportEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=autoConfigurationReportEndpoint]
2017-10-06 17:22:59.055 [main] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Located managed bean 'configurationPropertiesReportEndpoint': registering with JMX server as MBean [org.springframework.boot:type=Endpoint,name=configurationPropertiesReportEndpoint]
2017-10-06 17:22:59.077 [main] INFO  o.a.coyote.http11.Http11NioProtocol - Initializing ProtocolHandler ["http-nio-8080"]
2017-10-06 17:22:59.093 [main] INFO  o.a.coyote.http11.Http11NioProtocol - Starting ProtocolHandler ["http-nio-8080"]
2017-10-06 17:22:59.097 [main] INFO  o.a.tomcat.util.net.NioSelectorPool - Using a shared selector for servlet write/read
2017-10-06 17:22:59.135 [main] INFO  o.s.b.c.e.t.TomcatEmbeddedServletContainer - Tomcat started on port(s): 8080 (http)
# 应用已启动
2017-10-06 17:22:59.142 [main] INFO  s.g.d.DubboAnnotationConsumerApplication - Started DubboAnnotationConsumerApplication in 52.431 seconds (JVM running for 53.59)


### ======= 请求分发程序 =======
# 初始化Spring框架的请求分发程序
2017-10-06 17:23:41.555 [http-nio-8080-exec-1] INFO  o.a.c.c.C.[Tomcat].[localhost].[/] - Initializing Spring FrameworkServlet 'dispatcherServlet'
2017-10-06 17:23:41.555 [http-nio-8080-exec-1] INFO  o.s.web.servlet.DispatcherServlet - FrameworkServlet 'dispatcherServlet': initialization started
2017-10-06 17:23:41.578 [http-nio-8080-exec-1] INFO  o.s.web.servlet.DispatcherServlet - FrameworkServlet 'dispatcherServlet': initialization completed in 23 ms


### ======= Dubbo服务提供方某JVM实例关闭 =======
# 通知订阅Dubbo服务消费者URL(consumer://xxx)的URL列表(empty://10.1.112.138/spring.guides.dubbo.service.DemoService?category=providers&side=consumer)
2017-10-06 17:24:27.022 [main-EventThread] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Notify urls for subscribe url consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=providers,configurators,routers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, urls: [empty://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=providers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885], dubbo version: 2.5.5, current host: 10.1.112.138
# 关闭Netty通信通道(/10.1.112.138:55206 => /10.1.112.138:20880)
2017-10-06 17:24:27.024 [main-EventThread] INFO  c.a.d.r.transport.netty.NettyChannel -  [DUBBO] Close netty channel [id: 0x40c10b04, /10.1.112.138:55206 => /10.1.112.138:20880], dubbo version: 2.5.5, current host: 10.1.112.138
# 连接断开来自某客户端(/10.1.112.138:20880)的某服务URL(dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?codec=dubbo&side=consumer)
2017-10-06 17:24:27.026 [DubboSharedHandler-thread-1] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] disconected from /10.1.112.138:20880,url:dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-consumer&check=false&codec=dubbo&dubbo=2.5.5&generic=false&heartbeat=60000&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11673&retries=1&side=consumer&timeout=1000&timestamp=1507281745885, dubbo version: 2.5.5, current host: 10.1.112.138


### ======= 关闭Dubbo应用 =======
# 现在运行Dubbo关闭钩子
2017-10-06 17:24:35.426 [DubboShutdownHook] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Run shutdown hook now., dubbo version: 2.5.5, current host: 10.1.112.138
# 关闭所有注册中心(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop)
2017-10-06 17:24:35.426 [DubboShutdownHook] INFO  c.a.d.r.s.AbstractRegistryFactory -  [DUBBO] Close all registries [zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&check=false&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&interface=com.alibaba.dubbo.registry.RegistryService&logger=slf4j&owner=dannong&pid=11673&timestamp=1507281750985], dubbo version: 2.5.5, current host: 10.1.112.138
# 销毁某个注册中心(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop)
2017-10-06 17:24:35.427 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy registry:zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&check=false&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&interface=com.alibaba.dubbo.registry.RegistryService&logger=slf4j&owner=dannong&pid=11673&timestamp=1507281750985, dubbo version: 2.5.5, current host: 10.1.112.138
# 销毁取消的Dubbo服务消费者URL(consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=xxx&category=consumers&side=consumer)
2017-10-06 17:24:35.427 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy unregister url consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=consumers&check=false&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, dubbo version: 2.5.5, current host: 10.1.112.138
# 销毁取消订阅的Dubbo服务消费者URL(consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=xxx&category=providers,configurators,routers&side=consumer)
2017-10-06 17:24:35.427 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy unsubscribe url consumer://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-annotation-consumer&category=providers,configurators,routers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&owner=dannong&pid=11673&side=consumer&timestamp=1507281745885, dubbo version: 2.5.5, current host: 10.1.112.138
# 退出Curator后台操作循环
2017-10-06 17:24:35.428 [Curator-Framework-0] INFO  o.a.c.f.imps.CuratorFrameworkImpl - backgroundOperationsLoop exiting
# 关闭'基于注解配置嵌入的Web应用上下文(AnnotationConfigEmbeddedWebApplicationContext)'，上下文层次结构的根
2017-10-06 17:24:35.428 [Thread-3] INFO  o.s.b.c.e.AnnotationConfigEmbeddedWebApplicationContext - Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@184cf7cf: startup date [Fri Oct 06 17:22:22 CST 2017]; root of context hierarchy
# 在阶段0中停止组件(默认的组件生命周期处理程序)
2017-10-06 17:24:35.430 [Thread-3] INFO  o.s.c.s.DefaultLifecycleProcessor - Stopping beans in phase 0
# 在关闭时取消JMX暴露的组件
2017-10-06 17:24:35.433 [Thread-3] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Unregistering JMX-exposed beans on shutdown
2017-10-06 17:24:35.433 [Thread-3] INFO  o.s.b.a.e.jmx.EndpointMBeanExporter - Unregistering JMX-exposed beans
2017-10-06 17:24:35.434 [Thread-3] INFO  o.s.j.e.a.AnnotationMBeanExporter - Unregistering JMX-exposed beans on shutdown
# ZooKeeper会话已关闭
2017-10-06 17:24:35.453 [DubboShutdownHook] INFO  org.apache.zookeeper.ZooKeeper - Session: 0x15ee6ce79200025 closed
# 关闭Dubbo连接(10.1.112.138:0-->10.1.112.138:20880)
2017-10-06 17:24:35.453 [DubboShutdownHook] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] Close dubbo connect: 10.1.112.138:0-->10.1.112.138:20880, dubbo version: 2.5.5, current host: 10.1.112.138
# 关闭Dubbo连接(10.1.112.138:0-->10.1.112.138:20880)
2017-10-06 17:24:35.453 [DubboShutdownHook] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] Close dubbo connect: 10.1.112.138:0-->10.1.112.138:20880, dubbo version: 2.5.5, current host: 10.1.112.138
# ZooKeeper事件处理线程关闭会话
2017-10-06 17:24:35.454 [main-EventThread] INFO  org.apache.zookeeper.ClientCnxn - EventThread shut down for session: 0x15ee6ce79200025

# 应用进程完成退出
Process finished with exit code 130 (interrupted by signal 2: SIGINT)
```

