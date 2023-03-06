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
    
    ./bin/zkServer.sh status
    
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

```
ctrl shift u  全部大写、全部小写
```

```java
package com.wei.zkjavaapi;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.io.IOException;

/**
 * 连接到ZK服务端,打印连接状态
 */
public class ZKConnect implements Watcher {
    //先声明一个常量
    public static final String SERVER_PATH = "127.0.0.1:2181";
    public static final Integer  TIMEOUT = 5000;

    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * 客户端和服务器他们是异步连接,连接成功之后,客户端会收到watcher通知。
         * connectString:服务器的IP+端口号,比如127.0.0.1:2181
         * watcher:通知事件 (其他的监听事件比如创建删除也可以用它接收)
         */
        ZooKeeper zooKeeper = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKConnect());
        System.out.println("客户端开始连接ZK服务器了");
        System.out.println(zooKeeper.getState()); //拿到Zookeeper的连接情况,没那么快连接上
        Thread.sleep(2000);
        System.out.println(zooKeeper.getState()); }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("收到了通知: "+watchedEvent);  //watchedEvent是通知内容
    }}
```

```
2023年2月25日22:25:58 无法连接到阿里云服务器的Zookeeper,改用window了
```

#### 用代码对节点进行操作

```java
package com.wei.zkjavaapi;
import com.wei.zkjavaapi.callback.DeleteCallBack;
import org.apache.zookeeper.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 演示对节点的操作,包含创建、读取、删除等
 */
public class ZKOperator implements Watcher {
    public static final String SERVER_PATH = "127.0.0.1:2181";
    public static final Integer  TIMEOUT = 5000;
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        /**
         * 客户端和服务器他们是异步连接,连接成功之后,客户端会收到watcher通知。
         * connectString:服务器的IP+端口号,比如127.0.0.1:2181
         * watcher:通知事件 (其他的监听事件比如创建删除也可以用它接收)
         */
        ZooKeeper zooKeeper = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKConnect());
        System.out.println("客户端开始连接ZK服务器了");
        System.out.println(zooKeeper.getState()); //拿到Zookeeper的连接情况,没那么快连接上
        Thread.sleep(2000);
        System.out.println(zooKeeper.getState());

        /**
         * path:创建的路径
         * data:存储的数据 （存进去的内容）
         * acl:权限、开放
         * createMode:永久、临时、顺序。
         *
         */
//        创建节点
//        zooKeeper.create("/test","wei".getBytes(),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //写入数据
//        zooKeeper.setData("/wei-create-node","橘子洲头 2".getBytes(),1);

        //读取数据
//        byte[] data = zooKeeper.getData("/wei-create-node", null, null);
//        System.out.println(new String(data));
//
        String ctx = "删除成功";
        //在删除完成之后,会把ctx 传到DeleteCallBack(),
        zooKeeper.delete("/test",0,new DeleteCallBack(),ctx);
        Thread.sleep(2000); //callback是异步函数需要一定的时间
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("收到了通知: "+watchedEvent);  //watchedEvent是通知内容 }}
```

```java
package com.wei.zkjavaapi.callback;
import org.apache.zookeeper.AsyncCallback;
public class DeleteCallBack implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int i, String s, Object o) {
        System.out.println("删除节点 :" +s);
        System.out.println((String)o);}}
```



#### 处理Watcher事件

```java
package com.wei.zkjavaapi;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 和节点相关:是否存在,获取数据,加上watch
 * 1.用exists()判断节点是否存在
 * 2.给某个节点设置上监听器。并且可以根据监听事件的类型做出不同的行为判断
 */
public class ZKGetNode implements Watcher {

    //先声明一个常量
    public static final String SERVER_PATH = "127.0.0.1:2181";
    public static final Integer TIMEOUT = 5000;

    //这个类的作用是倒数。相当于门栓,一定的时候进行等待,适当的时候打开,继续运行
    public static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKGetNode());
        System.out.println("客户端开始连接ZK服务器了");
        System.out.println(zooKeeper.getState()); //拿到Zookeeper的连接情况,没那么快连接上
        Thread.sleep(2000);
        System.out.println(zooKeeper.getState());

//        Stat exists = zooKeeper.exists("/wei", false); //不需要对这个节点进行额外的监听
//        if (exists != null) {
//            //在存在的情况下获取它的版本
//            System.out.println("节点的版本为 :" + exists.getAversion());
//        } else {
//            System.out.println("该节点不存在");}

        zooKeeper.getData("/wei", true, null);
        countDownLatch.await();}

    @Override
    public void process(WatchedEvent watchedEvent) {
        //数据被改变才触发。(根据类型的不同做不同的事情)
        if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            System.out.println("数据被改变");  //watchedEvent是通知内容
        }
        System.out.println("收到了通知: "+watchedEvent); }}
```



#### 用Curator操作ZK

##### 原生的java的API的缺点

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230226005412157.png" alt="image-20230226005412157" style="zoom:40%;" />

##### 利用Apache Curator

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230226005454400.png" alt="image-20230226005454400" style="zoom:40%;" />

```java
package com.wei.zkjavaapi.curator;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 用Curator来操作ZK
 */
public class CuratorTests {
    public static void main(String[] args) throws Exception {
        String connectString = "127.0.0.1:2181";
        String path = "/curator3";
        String data = "come in";
        String data2 = "some text";
        //自动重连的功能
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retry);
        //连接
        client.start();
        //利用curator 实现Watcher操作,实现监听
        client.getCuratorListenable().addListener((CuratorFramework c, CuratorEvent event) -> {
            switch (event.getType()) {
                case WATCHED:
                    WatchedEvent watchedEvent = event.getWatchedEvent();
                    //如果触发了修改数据的监听事件
                    if (watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
                        System.out.println("数据被改变了: "+new String(c.getData().forPath(path)));
                    }}});
   //创建一个永久节点,并加入数据
  client.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());

//        byte[] bytes = client.getData().forPath(path);
        byte[] bytes = client.getData().watched().forPath(path); //这里传入watched() 和原生api传入true开启是一样的
        System.out.println(new String(bytes));

        client.setData().forPath(path, data2.getBytes());  //这里数据更改之后,便会回到WATCHED监听器。
        client.delete().forPath(path);
        Thread.sleep(2000);}}
```

#### Zookeeper重难点总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230226013453077.png" alt="image-20230226013453077" style="zoom:40%;" />



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



#### dubbo工作原理

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230226014124941.png" alt="image-20230226014124941" style="zoom:43%;" />

```
0.先启动生产者
1.生产者把自己注册到注册中心
2.消费者订阅注册中心,后续注册中心有什么变动信息会提醒消费者
4.消费者使用生产者的功能
5.无论是调用者还是被调用者,Monitor监听者会希望监控统计一些调用次数数据和时间

SpringCloud的 Eureka工作流程和这个也非常像。
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230226014826291.png" alt="image-20230226014826291" style="zoom:40%;" />

#### 服务提供者开发

```
之前的service是spring的那一套,而Dubbo有自己的service

给dubbo指定注册中心,最适合作为注册中心的Zookeeper在此时发挥作用,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230227072835150.png" alt="image-20230227072835150" style="zoom:50%;" />



#### 服务提供者的dubbo化配置

```java
demo.service.version=1.0.0

server.port=8081

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/course_practice?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=1234

#Zookeeper需要日志打印的吧,所以这里有一个打印格式的配置
logging.pattern.console=%clr(%d{${LOG_DATEFORMAT_PATTERN:HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}


spring.application.name=course-list

#dubbo协议
dubbo.protocol.name=dubbo
dubbo.protocol.port=-1
#dubbo注册
dubbo.registry.address=zookeeper://127.0.0.1:2181
dubbo.registry.file=${user.home}/dubbo-cache/${spring.application.name}/dubbo.cache

#对于实体类用下划线连接的得转成 驼峰式
mybatis.configuration.map-underscore-to-camel-case=true

dubbo.scan.base-packages=com.wei.producer.service.impl
```

```java
package com.wei.producer.mapper;
import com.wei.producer.entity.Course;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
/**
 * 描述：     Mapper类
 */
@Mapper
@Repository
public interface CourseMapper {
    @Select("SELECT * FROM course WHERE valid = 1")
    List<Course> findValidCourses();}
```

```java
//提供服务的接口
package com.wei.producer.service;
import com.wei.producer.entity.Course;
import java.util.List;
/**
 * 课程列表服务  (从数据库中拿到课程列表并把它展示出来)
 */
public interface CourseListService {
    List<Course> getCourseList();}
```

```java
//课程列表服务的实现类
package com.wei.producer.service.impl;
import com.wei.producer.entity.Course;
import com.wei.producer.mapper.CourseMapper;
import com.wei.producer.service.CourseListService;
import org.apache.dubbo.config.annotation.Service;
import javax.annotation.Resource;
import java.util.List;

@Service(version = "${demo.service.version}")  // dubbo的service
public class CourseListServiceImpl implements CourseListService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList() {
        return courseMapper.findValidCourses();}}
```

```java
/**
 * Spring Boot启动类
 */
@EnableAutoConfiguration
public class DubboProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboProducerApplication.class, args);}}
```

#### 服务消费方的开发

```java
demo.service.version=1.0.0

server.port=8084

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/course_practice?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=1234

logging.pattern.console=%clr(%d{${LOG_DATEFORMAT_PATTERN:HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}

spring.application.name=course-price

#dubbo协议
dubbo.protocol.name=dubbo
dubbo.protocol.port=-1
#dubbo注册
dubbo.registry.address=zookeeper://127.0.0.1:2181
dubbo.registry.file=${user.home}/dubbo-cache/${spring.application.name}/dubbo.cache

#对于实体类用下划线连接的得转成 驼峰式
mybatis.configuration.map-underscore-to-camel-case=true

#告诉dubbo服务的位置(指定位置),才能进行扫描 (带dubbo Service的类)
dubbo.scan.base-packages=com.wei.producer.service.impl
```

```java
package com.wei.consumer.service;
import com.wei.consumer.entity.CourseAndPrice;
import com.wei.consumer.entity.CoursePrice;
import java.util.List;

/**
 * 描述：     课程价格服务
 */
public interface CoursePriceService {
    CoursePrice getCoursePrice(Integer courseId);
    List<CourseAndPrice> getCoursesAndPrice();}
```

```java
package com.wei.consumer.service.impl;
import java.util.ArrayList;
import java.util.List;
import com.wei.consumer.dao.CoursePriceMapper;
import com.wei.consumer.entity.CourseAndPrice;
import com.wei.consumer.entity.CoursePrice;
import com.wei.consumer.service.CoursePriceService;
import com.wei.producer.entity.Course;
import com.wei.producer.service.CourseListService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 描述：     课程 价格服务
 */
@Service    //要对外暴露http请求所以用Spring的Service
public class CoursePriceServiceImpl implements CoursePriceService {

    @Resource
    CoursePriceMapper coursePriceMapper;

    // 调用dubbo的服务
    @Reference(version = "${demo.service.version}")
    CourseListService courseListService;

    @Override
    public CoursePrice getCoursePrice(Integer courseId) {
        return coursePriceMapper.findCoursePrices(courseId);}

    @Override
    public List<CourseAndPrice> getCoursesAndPrice() {
        List<CourseAndPrice> courseAndPriceList = new ArrayList<>();
        List<Course> courseList = courseListService.getCourseList();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            if (course != null) {
                CoursePrice price = getCoursePrice(course.getCourseId());
                if (price != null && price.getPrice() > 0) {
                    CourseAndPrice courseAndPrice = new CourseAndPrice();
                    courseAndPrice.setId(course.getId());
                    courseAndPrice.setCourseId(course.getCourseId());
                    courseAndPrice.setName(course.getName());
                    courseAndPrice.setPrice(price.getPrice());
                    courseAndPriceList.add(courseAndPrice);
                }}}
        return courseAndPriceList; }}
```

```java
/**
 * 描述：CoursePriceController
 */
@RestController
public class CoursePriceController {

    @Resource
    CoursePriceService coursePriceService;

    @GetMapping({"/price"})
    public Integer getCoursePrice(Integer courseId) {
        CoursePrice coursePrice = coursePriceService.getCoursePrice(courseId);
        if (coursePrice != null) {
            return coursePrice.getPrice();
        } else {
            return -1;} }

    @GetMapping({"/coursesAndPrice"})
    public List<CourseAndPrice> getcoursesAndPrice() {
        return coursePriceService.getCoursesAndPrice();}}
```

```java
/**
 * Spring Boot启动类
 */
//@EnableAutoConfiguration
@SpringBootApplication //要对外边暴露服务的所以使用这个
public class DubboConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);}}
```

#### dubbo总结









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

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230223232507424.png" alt="image-20230223232507424" style="zoom:33%;" />

```java
//通过在application.properties修改这个属性来切换不同配置文件
spring.profiles.active=pre
```



#### 项目中如何处理异常

##### 实际工作中,如何全局处理异常?

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230224160728331.png" alt="image-20230224160728331" style="zoom:33%;" />

```
首先,不进行处理的话,,那么很有可能这个异常会把整个堆栈都抛出去,这是默认的情况,一旦发生异常,用户或者是别有用心的人黑客,他们就可以看到详细的异常发生的情况,详细的错误信息甚至是代码的行数...有一定的安全隐患所以异常是一定需要处理的。

为什么要统一处理?
有了GlobalExceltionHandler就可以比较轻松的去针对不同类型的异常去做出定制化的解决方案,不当可以增加安全性而且对用户比较友好,用户可以知道错误信息并进行调整并且不会暴露关键的敏感信息,这是工作中正确处理异常的方式
```

```java
package com.imooc.mall.exception;

/**
 * 处理统一异常的handler
 */
//这个ControllerAdvice注解的作用就是拦截这些异常单的
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //目前有两种类型的异常需要拦截: 1.系统异常  2.业务异常

    @ExceptionHandler(Exception.class)  //异常的类型
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception : ", e);
        return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);}

    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public Object handleImoocMallException(ImoocMallException e) {
        log.error("ImoocMallException : ", e);
        //这里传进来的是什么就正常打印出去
        return ApiRestResponse.error(e.getCode(), e.getMessage());}

    //处理方法的参数不合规的异常(情况)
    @ExceptionHandler(MethodArgumentNotValidException.class)    //异常的类型
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : ", e);
        return handleBindingResult(e.getBindingResult());}

    //在绑定出问题的时候,我们来把这个异常处理成一个返回的ApiRestResponse,,,
    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {   //是否包含错误
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();   //getDefaultMessage 拿到错误信息
                list.add(message);
            }
            if (list.size() == 0) {
                return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR); }}
        return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString())}}
```



#### 线程如何启动

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230224162648578.png" alt="image-20230224162648578" style="zoom:33%;" />

```
如果直接调用run方法,其实就是一个普通的java方法,最重要的缺点在于不会真正的启动线程,只执行一次而且是在主线程中执行的,没有起到创建线程的效果

两个调用start()方法会出现什么情况?  会抛出一个异常 IllegalThreadStateException(); 之所以会抛出这个异常是因为在start()的时候会首先进行状态的检测,只有是new的时候才能正常启动不允许启动二次,
```

#### 哪种实现多线程的方法更好?

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230224164449881.png" alt="image-20230224164449881" style="zoom:33%;" />

```java
//用Runnable付费实现线程
package com.wei.interview.createthreads;
public class RunnableStyle implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();}

    @Override
    public void run() {
        System.out.println("用Runnable付费实现线程");}}
```

```java
//用Thread类实现线程
package com.wei.interview.createthreads;
public class ThreadStyle extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("用Thread类实现线程");}

    public static void main(String[] args) {
        new ThreadStyle().start();}}
```

##### 两种方法的对比

方法一(实现Runnable接口)更好

```
1.从代码架构角度
	接口形式更能实现解耦
2.新建线程的损耗
	接口形式用固定的线程来执行任务
3.java不支持多继承
	可扩展性少了一些。可以实现多接口
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230228232149420.png" alt="image-20230228232149420" style="zoom:43%;" />

```
可以用ctrl+f12,看当前的源码的方法名全部列出来  。偶尔感觉比直接搜索ctrl F好用,因为是直接搜索方法名
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230228232726733.png" alt="image-20230228232726733" style="zoom:50%;" />

```
根据java语法重写方法的话,父类的代码是可以完全不执行的
```

##### 思考题: 同时用两种方法会怎么样?

```
当同时执行两种方法的时候,由于已经把父类的run()方法覆盖了,所以真正执行的还是覆盖了Thread那个方法,
```

##### 用定时器算是新建线程的一种方式嘛?

```java
package com.wei.interview.createthreads;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 利用定时器新建线程。  Timer定时器其底层也是继承了 Thread类
 */
public class TimerDemo {
    public static void main(String[] args) {
        //打印主线程
        System.out.println(Thread.currentThread().getName());
        Timer timer = new Timer();
        //定时的执行内容
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }},1000,1000);}}
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230301000033721.png" alt="image-20230301000033721" style="zoom:33%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230301000330383.png" alt="image-20230301000330383" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230301000404530.png" alt="image-20230301000404530" style="zoom:43%;" />



#### 分布式的面试题

```
分布式和单体结构,哪一个会更好?
一个坑。脱离业务的技术选型是没有意义的,在对比某些技术哪个更好哪个更差的时候,应该要结合业务,结合具体的场景,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303005413685.png" alt="image-20230303005413685" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303004748145.png" alt="image-20230303004748145" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303004812883.png" alt="image-20230303004812883" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303004841928.png" alt="image-20230303004841928" style="zoom:40%;" />

##### CAP理论

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303005059417.png" alt="image-20230303005059417" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303005320301.png" alt="image-20230303005320301" style="zoom:43%;" />



#### docker面试题

```
为什么需要docker
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303010225509.png" alt="image-20230303010225509" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303010308437.png" alt="image-20230303010308437" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303010347031.png" alt="image-20230303010347031" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303010158593.png" alt="image-20230303010158593" style="zoom:43%;" />

#### Nginx和Zookeeper的面试题

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303012458764.png" alt="image-20230303012458764" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303012956889.png" alt="image-20230303012956889" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013020966.png" alt="image-20230303013020966" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013107234.png" alt="image-20230303013107234" style="zoom:43%;" />



##### Zookeeper有哪些类型的节点

```
采用树结构 
	1.持久节点
	2.临时节点
	3.顺序节点
```

#### RabbitMQ的面试题

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013259464.png" alt="image-20230303013259464" style="zoom:43%;" />



<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013440029.png" alt="image-20230303013440029" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013654823.png" alt="image-20230303013654823" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013546315.png" alt="image-20230303013546315" style="zoom:43%;" />

#### 微服务面试题

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013833268.png" alt="image-20230303013833268" style="zoom:33%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303013930846.png" alt="image-20230303013930846" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303014051610.png" alt="image-20230303014051610" style="zoom:33%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303014154242.png" alt="image-20230303014154242" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303014305927.png" alt="image-20230303014305927" style="zoom:40%;" />

发现这个服务不可以用可以摘除。不至于影响到其他的流程

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303014446429.png" alt="image-20230303014446429" style="zoom:43%;" />





#### 优质学习路径

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230306222406235.png" alt="image-20230306222406235" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230306222755567.png" alt="image-20230306222755567" style="zoom:40%;" />





#### Hashmap为什么不安全

```
指的是线程不安全
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303015343875.png" alt="image-20230303015343875" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303015311866.png" alt="image-20230303015311866" style="zoom:43%;" />



#### final有什么用

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303015427546.png" alt="image-20230303015427546" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303015451869.png" alt="image-20230303015451869" style="zoom:40%;" />



#### 单例模式的8中写法

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303015843929.png" alt="image-20230303015843929" style="zoom:40%;" />

```
分身很多,但真身只有一个
```

```java
package com.wei.interview.singleton;

/**
 * 饿汉式 (静态常量) (可用)
 */
public class Singleton1 {

    private Singleton1() {

    }

    private final static Singleton1 INSTANCE = new Singleton1();

    public static Singleton1 getInstance() {
        return INSTANCE;
    }}
```

<hr>

#### 面试避坑指南



```
面试官:你有没有什么想问的?
	对于公司的了解
	我们公司未来的战略是怎么样的? 
	我们部门在公司中的定位是什么? 
	我们的技术栈是怎么样的?我们的团队成员有几位?
	
	避免谈薪资。
```

#### 哪些软素质最受面试官的认可?

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303021212411.png" alt="image-20230303021212411" style="zoom:50%;" />

#### 面试课总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230303021251683.png" alt="image-20230303021251683" style="zoom:40%;" />