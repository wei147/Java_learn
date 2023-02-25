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
        System.out.println(zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("收到了通知: "+watchedEvent);  //watchedEvent是通知内容
    }
}
