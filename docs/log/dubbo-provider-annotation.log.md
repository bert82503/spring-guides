

`线程`名称描述(实例化方法)
```
main：应用主线程
Dubbo-Holder：Dubbo持有线程(DubboHolderListener.onApplicationEvent)
main-SendThread(127.0.0.1:2181)：ZooKeeper线程，服务传出的请求队列并生成心跳检查(ClientCnxn.ClientCnxn() -> ClientCnxn.SendThread)
main-EventThread：ZooKeeper配置数据事件处理线程(ClientCnxn.ClientCnxn() -> ClientCnxn.EventThread)
DubboServerHandler-{ip:port}-thread-{num}：Dubbo服务端处理程序(NettyServer.NettyServer)
DubboShutdownHook：基于JVM关闭钩子的Dubbo关闭线程(AbstractConfig.java:76)
Thread-{num}：基于JVM关闭钩子的Spring应用上下文关闭线程(AbstractApplicationContext.registerShutdownHook)
New I/O worker #1：Netty工作线程
```

### 启动步骤
```
0. 'Dubbo持有线程'开始运行
1. 应用上下文扫描Dubbo服务提供者的@Service注解组件定义
2. 导出Dubbo服务提供者为URL
3. 开始注册Dubbo服务提供者URL到注册中心(registry)
4. 启动绑定到20880端口的Netty服务器
5. 加载本地注册中心存储文件数据
6. 建立与注册中心的套接字连接会话
  1. 注册Dubbo服务提供者URL
  2. 订阅Dubbo服务提供者URL
  3. 通知订阅Dubbo服务提供者URL的URL列表
8. Zookeeper客户端收到新的配置事件
```

### 关闭步骤
```
0. 现在运行Dubbo关闭钩子(DubboShutdownHook)
1. 关闭应用上下文
2. 关闭所有注册中心
3. 销毁某个注册中心
  1. 销毁注销的Dubbo服务提供者URL
  2. 销毁取消订阅的Dubbo服务提供者URL
  3. ZooKeeper会话已关闭
  4. ZooKeeper事件处理线程关闭会话
4. 关闭Dubbo服务器
  1. 关闭绑定到20880端口的Netty服务器
  2. 所有客户端套接字连接都已断开，现在可以优雅地关闭(Dubbo服务器)
  3. 连接断开来自某客户端的某服务URL
  4. 未导出的Dubbo服务
5. 应用上下文停止组件
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
2017-10-06 17:18:47.196 [main] INFO  c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.log4j.Log4jLoggerAdapter
# 在哪台机器(dannongdeMacBook-Pro.local)上使用哪个进程(PID 11654)启动哪个应用入口类(DubboAnnotationProviderApplication)，包括应用所在目录
2017-10-06 17:18:52.280 [main] INFO  s.g.d.DubboAnnotationProviderApplication - Starting DubboAnnotationProviderApplication on dannongdeMacBook-Pro.local with PID 11654 (/Users/dannong/Documents/workspace/GitHub/spring-guides/spring-boot-rpc-soa-dubbo-provider-annotation/target/classes started by dannong in /Users/dannong/Documents/workspace/GitHub/spring-guides)
# 配置文件(profile)设置
2017-10-06 17:18:52.281 [main] INFO  s.g.d.DubboAnnotationProviderApplication - No active profile set, falling back to default profiles: default
### ======= Dubbo =======
# 'Dubbo持有线程'开始运行(DubboHolderListener)
Dubbo-Holder
# 刷新'基于注解配置的应用上下文(AnnotationConfigApplicationContext)'，上下文层次结构的根
2017-10-06 17:18:52.349 [main] INFO  o.s.c.a.AnnotationConfigApplicationContext - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@19d37183: startup date [Fri Oct 06 17:18:52 CST 2017]; root of context hierarchy
# 日志记录器工厂(slf4j)
2017-10-06 17:18:52.963 [main] INFO  c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.slf4j.Slf4jLoggerAdapter
# 导出Dubbo服务提供者到本地注册中心(local registry)
2017-10-06 17:19:03.677 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Export dubbo service spring.guides.dubbo.service.DemoService to local registry, dubbo version: 2.5.5, current host: 127.0.0.1
# 导出Dubbo服务提供者为URL(dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?anyhost=true&application=xxx&side=provider)
2017-10-06 17:19:03.677 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Export dubbo service spring.guides.dubbo.service.DemoService to url dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 开始注册Dubbo服务提供者URL到注册中心(registry://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop&registry=zookeeper)
2017-10-06 17:19:03.678 [main] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Register dubbo service spring.guides.dubbo.service.DemoService url dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597 to registry registry://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-annotation-provider&check=true&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&logger=slf4j&organization=middle-ware&owner=dannong&pid=11654&registry=zookeeper&timestamp=1507281533442, dubbo version: 2.5.5, current host: 127.0.0.1
# 启动绑定到20880端口(/0.0.0.0:20880)的Netty服务器(NettyServer)，导出服务器通讯地址(/10.1.112.138:20880)
2017-10-06 17:19:03.886 [main] INFO  c.a.d.r.transport.AbstractServer -  [DUBBO] Start NettyServer bind /0.0.0.0:20880, export /10.1.112.138:20880, dubbo version: 2.5.5, current host: 127.0.0.1
# 加载本地注册中心存储文件数据(xxx.DemoService=empty://10.1.112.138/xxx.DemoService?category=configurators&side=consumer empty://xxx/xxx.DemoService?category=routers&side=consumer dubbo://10.1.112.138:20880/xxx.DemoService?side=provider)
2017-10-06 17:19:03.904 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Load registry store file /Users/dannong/.dubbo/registry.cache, data: {spring.guides.dubbo.service.DemoService=empty://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-consumer&category=configurators&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&pid=11606&side=consumer&timestamp=1507281010835 empty://10.1.112.138/spring.guides.dubbo.service.DemoService?application=spring-boot-rpc-soa-dubbo-consumer&category=routers&dubbo=2.5.5&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&pid=11606&side=consumer&timestamp=1507281010835 dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accepts=0&accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&pid=11546&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507280828592}, dubbo version: 2.5.5, current host: 127.0.0.1
### ======= Zookeeper客户端 =======
# 启动Zookeeper注册中心的Curator客户端
2017-10-06 17:19:09.054 [main] INFO  o.a.c.f.imps.CuratorFrameworkImpl - Starting
# ZooKeeper客户端环境(ZK版本、主机名称、JDK信息、OS信息、用户信息、OS内存信息)
2017-10-06 17:19:14.138 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:zookeeper.version=3.5.3-beta-8ce24f9e675cbefffb8f21a47e06b42864475a60, built on 04/03/2017 16:19 GMT
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:host.name=10.1.112.138
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.version=1.8.0_112
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.vendor=Oracle Corporation
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/jre
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.class.path=.../lib/rt.jar:.../lib/tools.jar:.../spring-guides/spring-boot-rpc-soa-dubbo-provider-xml/target/classes:.../spring-guides/spring-boot-rpc-soa-api/target/classes:.../.m2/repository/com/alibaba/dubbo/2.5.5/dubbo-2.5.5.jar:/Users/dannong/.m2/repository/org/springframework/boot/spring-boot-starter/1.5.7.RELEASE/spring-boot-starter-1.5.7.RELEASE.jar
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.library.path=/Users/dannong/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.io.tmpdir=/var/folders/0y/kzmh046n3bqb07pwdrq_zy_r0000gn/T/
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:java.compiler=<NA>
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.name=Mac OS X
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.arch=x86_64
2017-10-06 17:19:14.139 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.version=10.12
2017-10-06 17:19:14.140 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.name=dannong
2017-10-06 17:19:14.140 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.home=/Users/dannong
2017-10-06 17:19:14.140 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:user.dir=/Users/dannong/Documents/workspace/GitHub/spring-guides
2017-10-06 17:19:14.140 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.free=61MB
2017-10-06 17:19:14.140 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.max=1820MB
2017-10-06 17:19:14.140 [main] INFO  org.apache.zookeeper.ZooKeeper - Client environment:os.memory.total=86MB
# 初始化客户端连接，包括连接字符串、会话超时、观察者状态
2017-10-06 17:19:14.142 [main] INFO  org.apache.zookeeper.ZooKeeper - Initiating client connection, connectString=127.0.0.1:2181 sessionTimeout=60000 watcher=org.apache.curator.ConnectionState@18e7143f
# 客户端连接套接字
2017-10-06 17:19:14.148 [main] INFO  o.apache.zookeeper.ClientCnxnSocket - jute.maxbuffer value is 4194304 Bytes
# 打开到ZooKeeper服务器(127.0.0.1/127.0.0.1:2181)的套接字连接(ClientCnxn：管理客户端与服务端的连接)
2017-10-06 17:19:14.157 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Opening socket connection to server 127.0.0.1/127.0.0.1:2181. Will not attempt to authenticate using SASL (unknown error)
# 使用默认的协议模式
2017-10-06 17:19:14.164 [main] INFO  o.a.c.f.imps.CuratorFrameworkImpl - Default schema
# 注册Dubbo服务提供者URL
2017-10-06 17:19:14.165 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Register: dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 套接字连接已建立，初始化客户端与服务端的会话(/127.0.0.1:55194 -> 127.0.0.1/127.0.0.1:2181)
2017-10-06 17:19:14.166 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Socket connection established, initiating session, client: /127.0.0.1:55194, server: 127.0.0.1/127.0.0.1:2181
# ZooKeeper服务器(127.0.0.1/127.0.0.1:2181)上的会话建立完成
2017-10-06 17:19:14.176 [main-SendThread(127.0.0.1:2181)] INFO  org.apache.zookeeper.ClientCnxn - Session establishment complete on server 127.0.0.1/127.0.0.1:2181, sessionid = 0x15ee6ce79200024, negotiated timeout = 40000
# 会话状态变换为已连接(CONNECTED)
2017-10-06 17:19:14.182 [main-EventThread] INFO  o.a.c.f.state.ConnectionStateManager - State change: CONNECTED
# 收到新的配置事件(数据：{})
2017-10-06 17:19:14.192 [main-EventThread] INFO  o.a.c.framework.imps.EnsembleTracker - New config event received: {}
# 订阅Dubbo服务提供者URL(provider://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?application=xxx&category=configurators&side=provider)
2017-10-06 17:19:14.197 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Subscribe: provider://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 通知订阅Dubbo服务提供者URL(provider://xxx)的URL列表(empty://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?category=configurators&side=provider)
2017-10-06 17:19:14.205 [main] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Notify urls for subscribe url provider://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, urls: [empty://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597], dubbo version: 2.5.5, current host: 127.0.0.1
# 在启动时为JMX暴露注册组件
2017-10-06 17:19:14.252 [main] INFO  o.s.j.e.a.AnnotationMBeanExporter - Registering beans for JMX exposure on startup
# 应用已启动
2017-10-06 17:19:14.269 [main] INFO  s.g.d.DubboAnnotationProviderApplication - Started DubboAnnotationProviderApplication in 37.655 seconds (JVM running for 38.91)
# 收到新的配置事件
2017-10-06 17:19:15.168 [main-EventThread] INFO  o.a.c.framework.imps.EnsembleTracker - New config event received: {}


# Dubbo服务访问日志，包括消费方地址(192.168.1.2:51855)、服务方法及参数值
2017-10-06 17:23:41.810 [DubboServerHandler-10.1.112.138:20880-thread-1] INFO  d.a.s.g.dubbo.service.DemoService -  [DUBBO] [2017-10-06 17:23:41] 10.1.112.138:55206 -> 10.1.112.138:20880 - spring.guides.dubbo.service.DemoService sayHello(java.lang.String) ["dannong"], dubbo version: 2.5.5, current host: 127.0.0.1
2017-10-06 17:23:55.238 [DubboServerHandler-10.1.112.138:20880-thread-1] INFO  d.a.s.g.dubbo.service.DemoService -  [DUBBO] [2017-10-06 17:23:55] 10.1.112.138:55206 -> 10.1.112.138:20880 - spring.guides.dubbo.service.DemoService sayHello(java.lang.String) ["dannong"], dubbo version: 2.5.5, current host: 127.0.0.1
2017-10-06 17:23:55.794 [DubboServerHandler-10.1.112.138:20880-thread-1] INFO  d.a.s.g.dubbo.service.DemoService -  [DUBBO] [2017-10-06 17:23:55] 10.1.112.138:55206 -> 10.1.112.138:20880 - spring.guides.dubbo.service.DemoService sayHello(java.lang.String) ["dannong"], dubbo version: 2.5.5, current host: 127.0.0.1


### ======= 关闭Dubbo应用 =======
# 现在运行Dubbo关闭钩子
2017-10-06 17:24:27.004 [DubboShutdownHook] INFO  c.a.dubbo.config.AbstractConfig -  [DUBBO] Run shutdown hook now., dubbo version: 2.5.5, current host: 127.0.0.1
# 关闭'基于注解配置的应用上下文(AnnotationConfigApplicationContext)'，上下文层次结构的根
2017-10-06 17:24:27.006 [Thread-2] INFO  o.s.c.a.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@19d37183: startup date [Fri Oct 06 17:18:52 CST 2017]; root of context hierarchy
# 关闭所有注册中心(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop)
2017-10-06 17:24:27.007 [DubboShutdownHook] INFO  c.a.d.r.s.AbstractRegistryFactory -  [DUBBO] Close all registries [zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-annotation-provider&check=true&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&interface=com.alibaba.dubbo.registry.RegistryService&logger=slf4j&organization=middle-ware&owner=dannong&pid=11654&timestamp=1507281533442], dubbo version: 2.5.5, current host: 127.0.0.1
# 销毁某个注册中心(zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?group=dubbo_develop)
2017-10-06 17:24:27.007 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy registry:zookeeper://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=spring-boot-rpc-soa-dubbo-annotation-provider&check=true&client=curator&dubbo=2.5.5&file=/Users/dannong/.dubbo/registry.cache&group=dubbo_develop&interface=com.alibaba.dubbo.registry.RegistryService&logger=slf4j&organization=middle-ware&owner=dannong&pid=11654&timestamp=1507281533442, dubbo version: 2.5.5, current host: 127.0.0.1
# 销毁注销的Dubbo服务提供者URL(dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?application=xxx&side=provider)
2017-10-06 17:24:27.008 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy unregister url dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 销毁取消订阅的Dubbo服务提供者URL(provider://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?application=xxx&category=configurators&side=provider)
2017-10-06 17:24:27.008 [DubboShutdownHook] INFO  c.a.d.r.zookeeper.ZookeeperRegistry -  [DUBBO] Destroy unsubscribe url provider://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&category=configurators&check=false&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 在关闭时注销JMX暴露的组件
2017-10-06 17:24:27.009 [Thread-2] INFO  o.s.j.e.a.AnnotationMBeanExporter - Unregistering JMX-exposed beans on shutdown
# 退出Curator后台操作循环
2017-10-06 17:24:27.009 [Curator-Framework-0] INFO  o.a.c.f.imps.CuratorFrameworkImpl - backgroundOperationsLoop exiting
# ZooKeeper会话已关闭
2017-10-06 17:24:27.020 [DubboShutdownHook] INFO  org.apache.zookeeper.ZooKeeper - Session: 0x15ee6ce79200024 closed
# 关闭Dubbo服务器(/10.1.112.138:20880)
2017-10-06 17:24:27.020 [DubboShutdownHook] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] Close dubbo server: /10.1.112.138:20880, dubbo version: 2.5.5, current host: 127.0.0.1
# ZooKeeper事件处理线程关闭会话
2017-10-06 17:24:27.021 [main-EventThread] INFO  org.apache.zookeeper.ClientCnxn - EventThread shut down for session: 0x15ee6ce79200024
# 关闭绑定到20880端口(/0.0.0.0:20880)的Netty服务器
2017-10-06 17:24:27.025 [DubboShutdownHook] INFO  c.a.d.r.transport.AbstractServer -  [DUBBO] Close NettyServer bind /0.0.0.0:20880, export /10.1.112.138:20880, dubbo version: 2.5.5, current host: 127.0.0.1
# 所有客户端套接字连接都已断开，现在可以优雅地关闭(Dubbo服务器)
2017-10-06 17:24:27.025 [New I/O worker #1] WARN  c.a.d.r.transport.AbstractServer -  [DUBBO] All clients has discontected from /10.1.112.138:20880. You can graceful shutdown now., dubbo version: 2.5.5, current host: 127.0.0.1
# 连接断开来自某客户端(/10.1.112.138:55206)的某服务URL(dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?channel.readonly.sent=true&codec=dubbo&side=provider)
2017-10-06 17:24:27.026 [DubboSharedHandler-thread-1] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] disconected from /10.1.112.138:55206,url:dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&channel.readonly.sent=true&codec=dubbo&dubbo=2.5.5&generic=false&heartbeat=60000&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 未导出的Dubbo服务(dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?side=provider)
2017-10-06 17:24:27.031 [DubboShutdownHook] INFO  c.a.d.r.protocol.dubbo.DubboProtocol -  [DUBBO] Unexport service: dubbo://10.1.112.138:20880/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1
# 未导出的Dubbo服务(injvm://127.0.0.1/spring.guides.dubbo.service.DemoService?side=provider)
2017-10-06 17:24:27.031 [DubboShutdownHook] INFO  c.a.d.r.protocol.injvm.InjvmProtocol -  [DUBBO] Unexport service: injvm://127.0.0.1/spring.guides.dubbo.service.DemoService?accesslog=true&anyhost=true&application=spring-boot-rpc-soa-dubbo-annotation-provider&dubbo=2.5.5&generic=false&interface=spring.guides.dubbo.service.DemoService&logger=slf4j&methods=sayHello&organization=middle-ware&owner=dannong&pid=11654&retries=1&side=provider&threadpool=cached&threads=100&timeout=1000&timestamp=1507281543597, dubbo version: 2.5.5, current host: 127.0.0.1

# 应用进程完成退出
Process finished with exit code 130 (interrupted by signal 2: SIGINT)
```

