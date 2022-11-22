package com.wei.springbootrabbitmqconsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者1
 */
@Component
@RabbitListener(queues = "queue1")
public class Receiver1 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("Receiver1收到了: " + message);
    }
}
