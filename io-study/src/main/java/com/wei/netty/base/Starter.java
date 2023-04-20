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
    }
}
