# Netty+SpringBoot 开发即时通讯系统



### 介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415001315076.png" alt="image-20230415001315076" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415001449828.png" alt="image-20230415001449828" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415001645064.png" alt="image-20230415001645064" style="zoom:40%;" />



### 第2章 为什么要自研一套即时通讯系统 ?

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415002829472.png" alt="image-20230415002829472" style="zoom:43%;" />

#### 如何自研一套即时通讯系统

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415003337069.png" alt="image-20230415003337069" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415003734435.png" alt="image-20230415003734435" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415003830652.png" alt="image-20230415003830652" style="zoom:50%;" />

```
如果需要打造一款即时通讯系统我们需要做哪些?

首先需要一个接入层去维护我们客户端的长连接和消息收发,在协议的选择上可以优先选择TCP。再者需要选择一个合理的应用层协议,比如说是选择mqpp、还是私有协议。接入层还要做好用户Session的维护,因为接入层和传统web开发不一样,接入层是有状态的服务,而传统的http是无状态的服务

其次是逻辑层,逻辑层要处理消息收发的核心逻辑,配合接入层和存储层做到消息的不丢、不漏、不串

最后是存储层,存储层需要有合理的设计。为逻辑层提供数据服务。
```



### 第3章 基础数据开发-用户关系群组

#### 介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415005028512.png" alt="image-20230415005028512" style="zoom:40%;" />

```
大致有三个模块。用户模块、关系链、群组。

业务系统和数据表会参考腾讯云即时通信
用户模块:
关系链 :分为强好友和弱好友两种方式。我加了谁和谁加了我两个维度的查询
群组 : 即时通许中最为复杂的模块。不光是基础业务的复杂。消息模块更是重中之重

```

#### 业务系统的基石 - 用户模块业务分析&数据库设计



#### 检验好友关系比想象中复杂

```
应该是有一个接口去批量校验是否好友,而不是一个个校验,,
```



## 第4章BIO、NIO Netty入门

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230420154834382.png" alt="image-20230420154834382" style="zoom:50%;" />

##### BIO

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230420154927826.png" alt="image-20230420154927826" style="zoom:33%;" />

```
同步阻塞IO    Blocking IO
```

##### NIO

```
同步非阻塞IO    Non Blocking IO   (也叫new io?)
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230420160428148.png" alt="image-20230420160428148" style="zoom:33%;" />

```
带着问题学习
1.什么是nio?什么是netty?nio和netty是什么关系?
2.什么应用场景下会用到netty?
```

#### BIO和NIO总结



#### 详解Netty

```
Netty基于NIO开发
```



```java
telnet 127.0.0.1 9001

注意点:
1.在命令窗口中用telnet命令时, 命令无法执行,提示:“'telnet' 不是内部或外部命令，也不是可运行的程序或批处理文件”。原因分析：windows带有telnet，只是默认没有安装而已。解决办法：控制面板|程序|程序和功能|打开和关闭Windows功能，勾选Telnet客户端。确定保存即可。
2.连接成功之后,默认是看到不了自己输入的字符的,需要按 ctrl+],另外也要处理ByteBuf转为 可读字符串(中文会乱码)
    
            // PooledUnsafeDirectByteBuf 把byte转为 string
        ByteBuf out = (ByteBuf) msg;
        System.out.println("msg is :  " + out.toString(StandardCharsets.UTF_8));
```



#### 章节总结

```java
//更多是模板代码  DiscardServer
package com.wei.netty.base.server;
import com.wei.netty.base.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-20 22:13
 **/
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        // netty会从这两个线程池里面取线程
        EventLoopGroup baseGroup = new NioEventLoopGroup(1);  //这里不设置的话,默认是cpu核数的两倍
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(baseGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            System.out.println("--tcp server stating--");
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            baseGroup.shutdownGracefully();
        }}}
```

```java
//DiscardServerHandler 对于各种事件的处理器。封装好就是方便
package com.wei.netty.base.handler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-20 22:32
 **/
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    //有客户端连接进来就会触发这个方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接了");
    }

    //有读写事件发生就会触发这个方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // PooledUnsafeDirectByteBuf 把byte转为 string
        ByteBuf out = (ByteBuf) msg;
        System.out.println("msg is :  " + out.toString(CharsetUtil.UTF_8)); //解决中文乱码问题
    }}
```

```java
//启动入口
package com.wei.netty.base;
import com.wei.netty.base.server.DiscardServer;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-20 22:12
 **/
public class Starter {
    public static void main(String[] args) throws InterruptedException {
        DiscardServer server = new DiscardServer(9001);
        server.run();
//        new DiscardServer(9001).run();
    }}
```

```java
1.什么是nio?什么是netty?nio和netty是什么关系?
    nio是一种线程模型,netty是基于nio开发的一款异步时间驱动框架。netty简化了nio网络编程的开发。,,,,,
2.什么应用场景下会用到netty?
    (1).开发任何网络编程。实现自己的rpc框架
    (2).能够转为一些公有协议的broker组件。如mqtt、http
    (3).不少的开源软件及大数据领域间的通信会用到netty
```

