package com.wei.zkjavaapi;

import com.wei.zkjavaapi.callback.DeleteCallBack;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

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
        ZooKeeper zooKeeper = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKOperator());
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
//        zooKeeper.create("/wei","风从虎".getBytes(),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //写入数据
        zooKeeper.setData("/wei","橘子洲头".getBytes(),0);

        //读取数据
        byte[] data = zooKeeper.getData("/wei", null, null);
        System.out.println(new String(data));
//
//        String ctx = "删除成功";
//        //在删除完成之后,会把ctx 传到DeleteCallBack(),
//        zooKeeper.delete("/test",0,new DeleteCallBack(),ctx);
//        Thread.sleep(2000); //callback是异步函数需要一定的时间

//        zooKeeper.getData("/wei",true,null);
//        countDownLatch.await();
    }

    @Override  //process执行的时候和主线程不完全是同步的,需要等待一段时间,需要等process执行完后
    // ,才能让主线程放行,以便终止我们的程序  上面用 countDownLatch.await(); 方法
    public void process(WatchedEvent watchedEvent) {
        System.out.println("收到了通知: "+watchedEvent);  //watchedEvent是通知内容
    }
}
