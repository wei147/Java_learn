## Zookeeper+Dubbo应用与面试

#### 35周介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219134939609.png" alt="image-20230219134939609" style="zoom:40%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219135301214.png" alt="image-20230219135301214" style="zoom:33%;" />

```
Dubbo和Spring Cloud的通信方式有了很大不同。原来是通过http请求是通过接口的形式,现在就不再需要接口了
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219135412721.png" alt="image-20230219135412721" style="zoom:40%;" />



#### Zookeeper章节介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219140304348.png" alt="image-20230219140304348" style="zoom:40%;" />

```
Zookeeper 动物园管理员
```

#### 为什么需要Zookeeper

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219140941237.png" alt="image-20230219140941237" style="zoom:40%;" />

```
Zookeeper和CAP的关系: CAP定理,之前在分布式的时候了解过,我们知道网络分区是不可避免的,可用性和一致性只能选择一个
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219142645704.png" alt="image-20230219142645704" style="zoom:30%;" />

```
Zookeeper主要解决的是两个问题:
	1.避免了单机的不可靠
	2.在多节点的情况下,尽可能的压缩同步的时间
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219141948189.png" alt="image-20230219141948189" style="zoom:40%;" />

```
最早诞生于雅虎研究院
```

##### Zookeeper是什么

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219181641420.png" alt="image-20230219181641420" style="zoom:33%;" />

```
它是一个分布式协调服务给分布式应用去使用的
```

#### Zookeeper的特点和作用

##### 5大特点

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219183407451.png" alt="image-20230219183407451" style="zoom:40%;" />

```java
1.顺序一致性:Zookeeper是分为客户端和服务端的,作为客户端而言会有一些消息发给服务端,那么在发送消息的时候肯定是一条一条的发,这些消息我们发出去的顺序是怎么样的,对于服务端Zookeeper而言,它就会以怎么样的顺序执行

2.原子性:本意指的是一系列的操作要么全部成功,要么全部不成功。把这个含义运用到Zookeeper中是 比如有一个请求过来,要更新一个内容,这个内容会被同步到所有节点上面去,这个同步的过程式一致的,要么全部成功

3.单一视图:Zookeeper是集群的,比如有三个服务器三个节点,无论连哪个看到的内容应该是一样的

4.可靠性:节点上的数据会一直保留,直到有新的请求把该数据写掉了

5.及时性:Zookeeper能保证在一定的时间段内,可接受的时间段内,一定会保证客户端能从服务器上读到最新的数据状态,
```

##### 集群情况下的Zookeeper架构图

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219183950740.png" alt="image-20230219183950740" style="zoom:33%;" />

##### Zookeeper和CAP的关系

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219184731060.png" alt="image-20230219184731060" style="zoom:33%;" />

```
Zookeeper为什么选择CP?  Zookeeper本身最大的作用就是分布式协调服务,它的职责就是保证数据在所有服务之间保持同步和一致。所以这个C出于它的目的而言就是成为了必选项,,,
```

##### Zookeeper作用的阐述

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219185433526.png" alt="image-20230219185433526" style="zoom:33%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219185950902.png" alt="image-20230219185950902" style="zoom:30%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219190058035.png" alt="image-20230219190058035" style="zoom:30%;" />

```java
Zookeeper是个中间件,主要是用于分布式系统的,具体有五大作用:
1.分布式服务注册与订阅
2.统一配置文件
3.生成分布式唯一ID
4.Master节点选举
5.分布式锁
    
    分布式锁:在jvm中为了保证对资源的有序访问,比如往文件中写数据,我们是不能同时写的,如果同时写数据可能会造成数据混乱、错误,所以为了保证线程安全通常会加synchronize,对资源进行互斥同步保证他们的线程安全,可以是分布式系统中这些锁就会失效,因为他们都分布在不同的机器不同的jvm中,如果他们想同时往一个文件中写数据如何保证互斥访问?这个时候就需要用到分布式锁了。主流实现分布式锁有两种方式 一种是利用redis,另一种是利用Zookeeper
```

#### Zookeeper的安装、配置

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230219191131295.png" alt="image-20230219191131295" style="zoom:33%;" />

```java
1 Linux 下安装
1 安装
wget https://downloads.apache.org/zookeeper/zookeeper-3.6.4/apache-zookeeper-3.6.4-bin.tar.gz 如果下载速度慢，就用教辅提供的安装包
tar zxvf apache-zookeeper-3.6.0-bin.tar.gz
cd apache-zookeeper-3.6.0-bin
cp conf/zoo_sample.cfg conf/zoo.cfg
2 配置 zoo.cfg
vi conf/zoo.cfg
把内容修改为：
tickTime=2000
dataDir=/var/lib/zookeeper
clientPort=2181
3 启动
./bin/zkServer.sh start
4 停止
./bin/zkServer.sh stop
    
    (注 需要安装jdk?)
```



#### znode节点

```
Zookeeper关于它的节点、它的结构是最必备的技能
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220220416748.png" alt="image-20230220220416748" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220220531173.png" alt="image-20230220220531173" style="zoom:36%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220221221158.png" alt="image-20230220221221158" style="zoom:33%;" />

##### 节点类型

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220221322711.png" alt="image-20230220221322711" style="zoom:40%;" />

##### 节点属性

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220221618809.png" alt="image-20230220221618809" style="zoom:33%;" />

```
版本相关的属性,对数据的操作就会引起对这些版本的更新,,,
dataVersion: 数据的版本号。每次对节点进行set操作的时候,对里面的内容进行修改,dataVersion+1
cversion: 子节点的意思,子节点发生变化则子节点加1
aclVersion:权限控制列表的意思,权限有变化的时候则节点加1
```

#### 常用命令

```
作为客户端连接服务器:
./bin/zkCli.sh -server 127.0.0.1:2181
```

```java
[zk: localhost:2181(CONNECTED) 5] stat /      //查看某些节点的状态
cZxid = 0x0
ctime = Thu Jan 01 08:00:00 CST 1970
mZxid = 0x0
mtime = Thu Jan 01 08:00:00 CST 1970
pZxid = 0x0
cversion = -1
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0     //可以判断是临时节点还是永久节点 这里是永久
dataLength = 0
numChildren = 1         //有多少个子节点
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220232857340.png" alt="image-20230220232857340" style="zoom:33%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230220232710115.png" alt="image-20230220232710115" style="zoom:43%;" />

```java
[zk: localhost:2181(CONNECTED) 9] create /wei
Created /wei
[zk: localhost:2181(CONNECTED) 10] ls
ls [-s] [-w] [-R] path
[zk: localhost:2181(CONNECTED) 11] ls /
[wei, zookeeper]
[zk: localhost:2181(CONNECTED) 12] create /yidou 10086
Created /yidou
[zk: localhost:2181(CONNECTED) 13] get /yidou
10086
[zk: localhost:2181(CONNECTED) 14] get /wei
null
[zk: localhost:2181(CONNECTED) 15] stat /wei
    ...
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 0
numChildren = 0
    
    ls / 查看当前所有节点
    用create 创建。
    用get /wei查看
    用set /wei 109 修改
    用stat /wei 查看状态
```

#### 高级命令

```java
[zk: 127.0.0.1:2181(CONNECTED) 3] create
create [-s] [-e] [-c] [-t ttl] path [data] [acl]
    
    不支持一次性创造多级目录
//以父节点为基准进行递增的  (顺序节点的能力)
[zk: 127.0.0.1:2181(CONNECTED) 10] create -s  /chen/s
Created /chen/s0000000002
[zk: 127.0.0.1:2181(CONNECTED) 11] create -s  /chen/p
Created /chen/p0000000003
    
//创建临时节点
create -e /yang/tmp 123
//临时节点的标识
ephemeralOwner = 0x1001020d9a10003
//临时节点和永久节点最大的区别就是 临时节点一旦这个会话断开,这个节点就会被自动的删除
    
[zk: 127.0.0.1:2181(CONNECTED) 1] set
set [-s] [-v version] path data  //这里的version是条件更新。版本号一致才能更新成功。有条件的更新
 
//好处: 这样可以保证在多线程的情况下,我们这个数据不会被频繁的操作,就算是set相同的值dataVersion也会发生变化
[zk: 127.0.0.1:2181(CONNECTED) 10] set -v 1 /yidou 9
version No is not valid : /yidou
    
 //删除节点  delete 
 [zk: 127.0.0.1:2181(CONNECTED) 11] delete
delete [-v version] path
    
```



#### Watch机制

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222224029299.png" alt="image-20230222224029299" style="zoom:33%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222224151527.png" alt="image-20230222224151527" style="zoom:33%;" />

##### ACL



#### Java原生客户端连接到ZK

##### 代码实操

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222225515894.png" alt="image-20230222225515894" style="zoom:50%;" />













### 第2节 Dubbo

#### Dubbo章节介绍

```
一个非常知名的框架

主要用途是用于rpc领域的  RPC(Remote Procedure Call) 远程调用

SpringCloud http通行
Dubbo rpc
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230221230420393.png" alt="image-20230221230420393" style="zoom:40%;" />

#### Dubbo是什么

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222164944937.png" alt="image-20230222164944937" style="zoom:33%;" />

```
Dubbo已经是RPC框架了,就不会和SpringCloud一样,并不会是一个微服务的全面解决方案,而是专注于rpc领域成为我们微服务生态的一个重要组件,,,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222164616230.png" alt="image-20230222164616230" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222170419394.png" alt="image-20230222170419394" style="zoom:33%;" />

##### 对开源的理解

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222170210894.png" alt="image-20230222170210894" style="zoom:40%;" />

#### RPC和HTTP

##### RPC介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222171302084.png" alt="image-20230222171302084" style="zoom:33%;" />

```
早期单机时代: IPC (Inter-Process Communication) 单个电脑里面个个进程之间的相互通信

网络时代:把IPC拓展到网络上,这就是RPC,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222171850310.png" alt="image-20230222171850310" style="zoom:33%;" />

```
Dubbo就是性能特别好
Montan是轻量级、便于理解
facebook家的就是支持语言的多
```

##### Http和RPC对比

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230222180459950.png" alt="image-20230222180459950" style="zoom:33%;" />

```java
普通话和方言

普通话就是HTTP,普通话的特点就是通用,没有很大的沟通和学习成本。普通话这个特点很可能会做很多的规定比如说你的请求头怎么写、传输有哪些要求,这样一来效率就降低了。

假设是在企业内部的话,rpc会更加的高效,它的传输效率和运行速度会更高,因为rpc是一种方言,方言所表达的内容和普通话所能表达的内容并没有什么差别,只不过是限定了地域而已,假设是一个团队内部,都能用这套方言来交流的话其实是更高效的,比http更加节省资源。这也是现在企业中既能存在http和rpc的原因


    RPC比HTTP 传输效率较高、性能好、有负载均衡能力
```



#### 第3节 分布式与微服务面试

#### 面试课介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230223193434693.png" alt="image-20230223193434693" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230223193344226.png" alt="image-20230223193344226" style="zoom:40%;" />

```
单例模式是所有设计模式中最适合被用来面试的设计模式
```



#### Spring、Spring Boot和Spring Cloud的关系

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230223194810174.png" alt="image-20230223194810174" style="zoom:33%;" />

```java
1.SpringBoot是在强大的Spring帝国上面发展而来的,而且发明SpringBoot是为了让人们更容易的使用Spring
2.约定大于配置
3.SpringCloud是基于SpringBoot的微服务框架
```



#### SpringBoot如何配置多环境

