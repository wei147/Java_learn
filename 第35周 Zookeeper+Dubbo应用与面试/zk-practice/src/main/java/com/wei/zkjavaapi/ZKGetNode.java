package com.wei.zkjavaapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * 和节点相关:是否存在,获取数据,加上watch
 */
public class ZKGetNode implements Watcher {

    //先声明一个常量
    public static final String SERVER_PATH = "127.0.0.1:2181";
    public static final Integer TIMEOUT = 5000;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKGetNode());
        System.out.println("客户端开始连接ZK服务器了");
        System.out.println(zooKeeper.getState()); //拿到Zookeeper的连接情况,没那么快连接上
        Thread.sleep(2000);
        System.out.println(zooKeeper.getState());

        Stat exists = zooKeeper.exists("/wei", false); //不需要对这个节点进行额外的监听
        if (exists != null) {
            //在存在的情况下获取它的版本
            System.out.println("节点的版本为 :" + exists.getAversion());
        } else {
            System.out.println("该节点不存在");
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("收到了通知: " + watchedEvent);  //watchedEvent是通知内容
    }
}
