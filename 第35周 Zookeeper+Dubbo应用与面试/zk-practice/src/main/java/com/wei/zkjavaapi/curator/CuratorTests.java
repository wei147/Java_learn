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
                    }

            }
        });
        //创建一个永久节点,并加入数据
        client.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());

//        byte[] bytes = client.getData().forPath(path);
        byte[] bytes = client.getData().watched().forPath(path); //这里传入watched() 和原生api传入true开启是一样的
        System.out.println(new String(bytes));

        client.setData().forPath(path, data2.getBytes());  //这里数据更改之后,便会回到WATCHED监听器。
        client.delete().forPath(path);
        Thread.sleep(2000);
    }
}
