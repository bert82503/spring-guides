

`线程`名称描述(实例化方法)
```
main：应用主线程
Dubbo-Holder：Dubbo持有线程(DubboHolderListener.onApplicationEvent)
main-SendThread(127.0.0.1:2181)：ZooKeeper线程，服务传出的请求队列并生成心跳检查(ClientCnxn.ClientCnxn() -> ClientCnxn.SendThread)
main-EventThread：ZooKeeper配置数据事件处理线程(ClientCnxn.ClientCnxn() -> ClientCnxn.EventThread)
DubboServerHandler-192.168.1.2:20880-thread-{num}：Dubbo服务端处理程序(NettyServer.NettyServer)
New I/O worker #1：Netty工作线程
DubboShutdownHook：基于JVM关闭钩子的Dubbo关闭线程(AbstractConfig.java:76)
Thread-{num}：基于JVM关闭钩子的Spring应用上下文关闭线程(AbstractApplicationContext.registerShutdownHook)
```


#### 启动日志
```java
# Spring Boot横幅，包括其版本
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.5.7.RELEASE)

# 在哪台机器(192.168.1.2)上使用哪个进程(PID 8677)启动哪个应用入口类(DubboXmlProviderApplication)，包括应用所在目录
2017-10-06 00:35:25.122 [main] INFO  s.g.d.DubboXmlProviderApplication - Starting DubboXmlProviderApplication on dannongdembp with PID 8677 (/Users/dannong/Documents/workspace/GitHub/spring-guides/spring-boot-rpc-soa-dubbo-provider-xml/target/classes started by dannong in /Users/dannong/Documents/workspace/GitHub/spring-guides)
# 配置文件(profile)设置
2017-10-06 00:35:25.138 [main] INFO  s.g.d.DubboXmlProviderApplication - No active profile set, falling back to default profiles: default
### ======= Dubbo =======
# 'Dubbo持有线程'开始运行(DubboHolderListener)
Dubbo-Holder
# 刷新'基于注解配置的应用上下文(AnnotationConfigApplicationContext)'，上下文层次结构的根
2017-10-06 00:35:25.235 [main] INFO  o.s.c.a.AnnotationConfigApplicationContext - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@45dd4eda: startup date [Fri Oct 06 00:35:25 CST 2017]; root of context hierarchy
# 加载'Dubbo服务提供者(dubbo-service-provider.xml)'的XML组件定义
2017-10-06 00:35:25.682 [main] INFO  o.s.b.f.xml.XmlBeanDefinitionReader - Loading XML bean definitions from file [/Users/dannong/Documents/workspace/GitHub/spring-guides/spring-boot-rpc-soa-dubbo-provider-xml/target/classes/META-INF/spring/dubbo-service-provider.xml]
# 日志记录器工厂(log4j)
2017-10-06 00:35:25.825 [main] INFO  c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.log4j.Log4jLoggerAdapter
# 在启动时为JMX暴露注册组件
2017-10-06 00:35:26.646 [main] INFO  o.s.j.e.a.AnnotationMBeanExporter - Registering beans for JMX exposure on startup
### ======= 注册Dubbo服务提供者 =======
# Dubbo服务提供者已准备好(DemoService)
2017-10-06 00:35:26.659 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] The service ready on spring started. service: spring.guides.dubbo.service.DemoService, dubbo version: 2.5.5, current host: 127.0.0.1
# 导出Dubbo服务提供者到本地注册中心(local registry)
2017-10-06 00:35:26.758 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Export dubbo service spring.guides.dubbo.service.DemoService to local registry, dubbo version: 2.5.5, current host: 127.0.0.1
# 导出Dubbo服务提供者为URL(dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?application=xxx&side=provider)
2017-10-06 00:35:26.759 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Export dubbo service spring.guides.dubbo.service.DemoService to url dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1
# 注册Dubbo服务提供者的URL到远程注册中心(registry://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?client=curator&group=dubbo_develop&registry=zookeeper)
2017-10-06 00:35:26.759 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Register dubbo service spring.guides.dubbo.service.DemoService url dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673 to registry registry://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-provider&check=true&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&pid=8677&registry=zookeeper&timestamp=1507221326667, dubbo version: 2.5.5, current host: 127.0.0.1
# 启动绑定到20880端口(/0.0.0.0:20880)的Netty服务器(NettyServer)，导出服务器通讯地址(/192.168.1.2:20880)
2017-10-06 00:35:26.942 [main] INFO  c.a.d.r.transport.AbstractServer -  [DUBBO] Start NettyServer bind /0.0.0.0:20880, export /192.168.1.2:20880, dubbo version: 2.5.5, current host: 127.0.0.1
# 加载本地注册中心存储文件数据(empty://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?category=configurators&side=provider)
2017-10-06 00:35:26.956 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Load registry store file /Users/dannong/.dubbo/registry.cache, data: {spring.guides.dubbo.service.DemoService=empty://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=7875&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507217014876}, dubbo version: 2.5.5, current host: 127.0.0.1
### ======= Zookeeper客户端 =======
# 启动Zookeeper注册中心的Curator客户端
2017-10-06 00:35:27.074 [main] INFO  o.a.c.f.imps.CuratorFrameworkImpl - Starting
# ZooKeeper客户端环境(ZK版本、主机名称、JDK信息、OS信息、用户信息、OS内存信息)
2017-10-06 00:35:27.101 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:zookeeper.version=3.5.3-beta-8ce24f9e675cbefffb8f21a47e06b42864475a60, built on 04/03/2017 16:19 GMT
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:host.name=dannongdembp
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.version=1.8.0_112
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.vendor=Oracle Corporation
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.class.path=/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/lib/tools.jar:/Users/dannong/Documents/workspace/GitHub/spring-guides/spring-boot-rpc-soa-dubbo-provider-xml/target/classes:/Users/dannong/Documents/workspace/GitHub/spring-guides/spring-boot-rpc-soa-api/target/classes:/Users/dannong/.m2/repository/com/alibaba/dubbo/2.5.5/dubbo-2.5.5.jar:/Users/dannong/.m2/repository/org/springframework/spring-beans/4.3.11.RELEASE/spring-beans-4.3.11.RELEASE.jar:/Users/dannong/.m2/repository/org/springframework/spring-core/4.3.11.RELEASE/spring-core-4.3.11.RELEASE.jar:/Users/dannong/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/Users/dannong/.m2/repository/org/springframework/spring-context/4.3.11.RELEASE/spring-context-4.3.11.RELEASE.jar:/Users/dannong/.m2/repository/org/springframework/spring-aop/4.3.11.RELEASE/spring-aop-4.3.11.RELEASE.jar:/Users/dannong/.m2/repository/org/springframework/spring-expression/4.3.11.RELEASE/spring-expression-4.3.11.RELEASE.jar:/Users/dannong/.m2/repository/org/javassist/javassist/3.21.0-GA/javassist-3.21.0-GA.jar:/Users/dannong/.m2/repository/org/springframework/spring-web/4.3.11.RELEASE/spring-web-4.3.11.RELEASE.jar:/Users/dannong/.m2/repository/io/dubbo/springboot/spring-boot-starter-dubbo/1.0.0/spring-boot-starter-dubbo-1.0.0.jar:/Users/dannong/.m2/repository/org/springframework/boot/spring-boot-starter/1.5.7.RELEASE/spring-boot-starter-1.5.7.RELEASE.jar:/Users/dannong/.m2/repository/org/springframework/boot/spring-boot/1.5.7.RELEASE/spring-boot-1.5.7.RELEASE.jar:/Users/dannong/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/1.5.7.RELEASE/spring-boot-autoconfigure-1.5.7.RELEASE.jar:/Users/dannong/.m2/repository/org/springframework/boot/spring-boot-starter-logging/1.5.7.RELEASE/spring-boot-starter-logging-1.5.7.RELEASE.jar:/Users/dannong/.m2/repository/ch/qos/logback/logback-classic/1.1.11/logback-classic-1.1.11.jar:/Users/dannong/.m2/repository/ch/qos/logback/logback-core/1.1.11/logback-core-1.1.11.jar:/Users/dannong/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/dannong/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.25/jcl-over-slf4j-1.7.25.jar:/Users/dannong/.m2/repository/org/slf4j/jul-to-slf4j/1.7.25/jul-to-slf4j-1.7.25.jar:/Users/dannong/.m2/repository/org/slf4j/log4j-over-slf4j/1.7.25/log4j-over-slf4j-1.7.25.jar:/Users/dannong/.m2/repository/org/yaml/snakeyaml/1.17/snakeyaml-1.17.jar:/Users/dannong/.m2/repository/com/101tec/zkclient/0.10/zkclient-0.10.jar:/Users/dannong/.m2/repository/org/apache/zookeeper/zookeeper/3.5.3-beta/zookeeper-3.5.3-beta.jar:/Users/dannong/.m2/repository/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:/Users/dannong/.m2/repository/io/netty/netty/3.10.5.Final/netty-3.10.5.Final.jar:/Users/dannong/.m2/repository/org/apache/curator/curator-recipes/3.3.0/curator-recipes-3.3.0.jar:/Users/dannong/.m2/repository/org/apache/curator/curator-framework/3.3.0/curator-framework-3.3.0.jar:/Users/dannong/.m2/repository/org/apache/curator/curator-client/3.3.0/curator-client-3.3.0.jar:/Users/dannong/.m2/repository/com/google/guava/guava/16.0.1/guava-16.0.1.jar:/Applications/IntelliJ IDEA 16.app/Contents/lib/idea_rt.jar
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.library.path=/Users/dannong/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.io.tmpdir=/var/folders/0y/kzmh046n3bqb07pwdrq_zy_r0000gn/T/
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.compiler=<NA>
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.name=Mac OS X
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.arch=x86_64
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.version=10.12
2017-10-06 00:35:27.102 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.name=dannong
2017-10-06 00:35:27.103 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.home=/Users/dannong
2017-10-06 00:35:27.103 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.dir=/Users/dannong/Documents/workspace/GitHub/spring-guides
2017-10-06 00:35:27.103 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.free=73MB
2017-10-06 00:35:27.103 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.max=1820MB
2017-10-06 00:35:27.103 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.total=108MB
# 初始化客户端连接，包括连接字符串、会话超时、观察者状态
2017-10-06 00:35:27.106 [main] INFO  org.apache.zookeeper.ZooKeeper - Initiating client connection, connectString=127.0.0.1:2181 sessionTimeout=60000 watcher=org.apache.curator.ConnectionState@21694e53
# 客户端连接套接字
2017-10-06 00:35:27.114 [main] INFO  o.apache.zookeeper.ClientCnxnSocket - jute.maxbuffer value is 4194304 Bytes
# 打开到ZooKeeper服务器(127.0.0.1/127.0.0.1:2181)的套接字连接(ClientCnxn：管理客户端与服务端的连接)
2017-10-06 00:35:27.127 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Opening socket connection to server 127.0.0.1/127.0.0.1:2181. Will not attempt to authenticate using SASL (unknown error)
# 套接字连接已建立，初始化客户端与服务端的会话(/127.0.0.1:51839 -> 127.0.0.1/127.0.0.1:2181)
2017-10-06 00:35:27.141 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Socket connection established, initiating session, client: /127.0.0.1:51839, server: 127.0.0.1/127.0.0.1:2181
# 使用默认的协议模式
2017-10-06 00:35:27.154 [main] INFO  o.a.c.f.imps.CuratorFrameworkImpl - Default schema
# ZooKeeper服务器(127.0.0.1/127.0.0.1:2181)上的会话建立完成
2017-10-06 00:35:27.158 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Session establishment complete on server 127.0.0.1/127.0.0.1:2181, sessionid = 0x15ee6ce79200018, negotiated timeout = 40000
# 注册Dubbo服务提供者的URL
2017-10-06 00:35:27.164 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Register: dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1
# 会话状态变换为已连接(CONNECTED)
2017-10-06 00:35:27.173 [main-EventThread] INFO  o.a.c.f.state.ConnectionStateManager - State change: CONNECTED
# 收到新的配置事件(数据：{})
2017-10-06 00:35:27.199 [main-EventThread] INFO  o.a.c.framework.imps.EnsembleTracker - New config event received: {}
# 订阅Dubbo服务提供者URL(provider://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?application=xxx&category=configurators&side=provider)
2017-10-06 00:35:27.257 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Subscribe: provider://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1
# 通知订阅Dubbo服务提供者URL(provider://xxx)的URL列表(empty://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?category=configurators&side=provider)
2017-10-06 00:35:27.272 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Notify urls for subscribe url provider://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, urls: [empty://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673], dubbo version: 2.5.5, current host: 127.0.0.1
# 应用已启动
2017-10-06 00:35:27.319 [main] INFO  s.g.d.DubboXmlProviderApplication - Started DubboXmlProviderApplication in 3.355 seconds (JVM running for 4.714)
# 收到新的配置事件
2017-10-06 00:35:28.158 [main-EventThread] INFO  o.a.c.framework.imps.EnsembleTracker - New config event received: {}


# Dubbo服务访问日志，包括消费方地址(192.168.1.2:51855)、服务方法及参数值
2017-10-06 00:40:13.072 [DubboServerHandler-192.168.1.2:20880-thread-1] INFO  d.a.s.g.dubbo.service.DemoService -  [DUBBO] [2017-10-06 00:40:13] 192.168.1.2:51855 -> 192.168.1.2:20880 - spring.guides.dubbo.service.DemoService sayHello(java.lang.String) ["dannong"], dubbo version: 2.5.5, current host: 127.0.0.1
2017-10-06 00:40:14.528 [DubboServerHandler-192.168.1.2:20880-thread-1] INFO  d.a.s.g.dubbo.service.DemoService -  [DUBBO] [2017-10-06 00:40:14] 192.168.1.2:51855 -> 192.168.1.2:20880 - spring.guides.dubbo.service.DemoService sayHello(java.lang.String) ["dannong"], dubbo version: 2.5.5, current host: 127.0.0.1
2017-10-06 00:40:15.073 [DubboServerHandler-192.168.1.2:20880-thread-1] INFO  d.a.s.g.dubbo.service.DemoService -  [DUBBO] [2017-10-06 00:40:15] 192.168.1.2:51855 -> 192.168.1.2:20880 - spring.guides.dubbo.service.DemoService sayHello(java.lang.String) ["dannong"], dubbo version: 2.5.5, current host: 127.0.0.1


### ======= Telnet =======
# 关闭Netty通信通道(/0:0:0:0:0:0:0:1:51862 => /0:0:0:0:0:0:0:1:20880)
2017-10-06 00:41:16.848 [DubboServerHandler-192.168.1.2:20880-thread-1] INFO  c.a.d.r.transport.netty.NettyChannel -  [DUBBO] Close netty channel [id: 0x89506a59, /0:0:0:0:0:0:0:1:51862 => /0:0:0:0:0:0:0:1:20880], dubbo version: 2.5.5, current host: 127.0.0.1
# 连接断开来自某客户端(/0:0:0:0:0:0:0:1:51862)的某服务URL(dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?channel.readonly.sent=true&codec=dubbo&side=provider)
2017-10-06 00:41:16.852 [DubboServerHandler-192.168.1.2:20880-thread-1] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] disconected from /0:0:0:0:0:0:0:1:51862,url:dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&channel.readonly.sent=true&codec=dubbo&dubbo=2.5.5&generic=false&heartbeat=60000&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1


### ======= 所有Zookeeper客户端套接字连接都已断开 =======
# 所有客户端套接字连接都已断开，现在可以优雅地关闭(Dubbo服务器)
2017-10-06 00:41:35.010 [New I/O worker #1] WARN  c.a.d.r.transport.AbstractServer -  [DUBBO] All clients has discontected from /192.168.1.2:20880. You can graceful shutdown now., dubbo version: 2.5.5, current host: 127.0.0.1
# 连接断开来自某客户端(/192.168.1.2:51855)的某服务URL(dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?channel.readonly.sent=true&codec=dubbo&side=provider)
2017-10-06 00:41:35.011 [DubboServerHandler-192.168.1.2:20880-thread-1] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] disconected from /192.168.1.2:51855,url:dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&channel.readonly.sent=true&codec=dubbo&dubbo=2.5.5&generic=false&heartbeat=60000&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1


### ======= 关闭Dubbo应用 =======
# 现在运行Dubbo关闭钩子
2017-10-06 00:41:40.973 [DubboShutdownHook] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Run shutdown hook now., dubbo version: 2.5.5, current host: 127.0.0.1
# 关闭'基于注解配置的应用上下文(AnnotationConfigApplicationContext)'，上下文层次结构的根
2017-10-06 00:41:40.973 [Thread-2] INFO  o.s.c.a.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@45dd4eda: startup date [Fri Oct 06 00:35:25 CST 2017]; root of context hierarchy
# 关闭所有注册中心(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop)
2017-10-06 00:41:40.975 [DubboShutdownHook] INFO  c.a.d.r.s.AbstractRegistryFactory -  [DUBBO] Close all registries [zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-provider&check=true&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&interface=com.alibaba.dubbo.registry.RegistryService&pid=8677&timestamp=1507221326667], dubbo version: 2.5.5, current host: 127.0.0.1
# 销毁某个注册中心(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop)
2017-10-06 00:41:40.976 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy registry:zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-provider&check=true&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&interface=com.alibaba.dubbo.registry.RegistryService&pid=8677&timestamp=1507221326667, dubbo version: 2.5.5, current host: 127.0.0.1
# 销毁注销的Dubbo服务提供者URL(dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?application=xxx&side=provider)
2017-10-06 00:41:40.977 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy unregister url dubbo://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1
# 销毁取消订阅的Dubbo服务提供者URL(provider://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?application=xxx&category=configurators&side=provider)
2017-10-06 00:41:40.978 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy unsubscribe url provider://192.168.1.2:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&methods=sayHello&pid=8677&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507221326673, dubbo version: 2.5.5, current host: 127.0.0.1
# 在关闭时注销JMX暴露的组件
2017-10-06 00:41:40.980 [Thread-2] INFO  o.s.j.e.a.AnnotationMBeanExporter - Unregistering JMX-exposed beans on shutdown
# 退出Curator后台操作循环
2017-10-06 00:41:40.980 [Curator-Framework-0] INFO  o.a.c.f.imps.CuratorFrameworkImpl - backgroundOperationsLoop exiting
# ZooKeeper会话已关闭
2017-10-06 00:41:41.003 [DubboShutdownHook] INFO  org.apache.zookeeper.ZooKeeper - Session: 0x15ee6ce79200018 closed
# ZooKeeper事件处理线程关闭会话
2017-10-06 00:41:41.003 [main-EventThread] INFO  org.apache.zookeeper.ClientCnxn - EventThread shut down for session: 0x15ee6ce79200018
# 关闭Dubbo服务器(/192.168.1.2:20880)
2017-10-06 00:41:41.003 [DubboShutdownHook] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] Close dubbo server: /192.168.1.2:20880, dubbo version: 2.5.5, current host: 127.0.0.1
# 关闭绑定到20880端口(/0.0.0.0:20880)的Netty服务器
2017-10-06 00:41:41.004 [DubboShutdownHook] INFO  c.a.d.r.transport.AbstractServer -  [DUBBO] Close NettyServer bind /0.0.0.0:20880, export /192.168.1.2:20880, dubbo version: 2.5.5, current host: 127.0.0.1

# 应用进程完成退出
Process finished with exit code 130 (interrupted by signal 2: SIGINT)
```

