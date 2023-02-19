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
```

