package com.wei.springbootrabbitmqproducer;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置类
 */

@Configuration
public class TopicRabbitmqConfig {

    @Bean
    public Queue queue1() {
        return new Queue("queue1");  //这里queue的名字使用和方法名是同一个,这样spring能自动匹配到
    }

    @Bean
    public Queue queue2() {
        return new Queue("queue2");  //这里queue的名字使用和方法名是同一个,这样spring能自动匹配到
    }

    @Bean
    TopicExchange exchange() { //这个加了@Bean标签的exchange会直接用到下面的exchange中
        return new TopicExchange("bootExchange");
    }

    //指定topic模式的交换机。将队列queue绑定到交换机上
    @Bean
    Binding bindingExchangeMessage1(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("dog.red");
    }

    @Bean
    Binding bindingExchangeMessage2(Queue queue2, TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with("dog.#");
    }

}
