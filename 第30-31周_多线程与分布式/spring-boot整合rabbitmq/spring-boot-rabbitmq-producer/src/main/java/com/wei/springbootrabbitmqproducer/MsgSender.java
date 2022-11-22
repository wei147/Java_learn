package com.wei.springbootrabbitmqproducer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送消息
 */
@Component
public class MsgSender {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send1() {
        String message = "This is message 1, routing key is dog.red";
        System.out.println("发送了: " + message);

        //发送操作
        this.rabbitmqTemplate.convertAndSend("bootExchange", "dog.red", message);
    }

    public void send2() {
        String message = "This is message 2, routing key is dog.black";
        System.out.println("发送了: " + message);

        //发送操作
        this.rabbitmqTemplate.convertAndSend("bootExchange", "dog.black", message);
    }
}
