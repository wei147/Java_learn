package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Hello World 的发送类。 连接到RabbitMQ服务端,然后发送一个条消息,接着退出
 */
public class Send {

    //QUEUE_NAME 在接收的时候要对应起来,要用同一个队列才能收到消息
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("175.178.91.105"); //RabbitMQ要想被外界访问进来要开启 5672 端口
        factory.setUsername("admin");  //RabbitMQ的guest用户只有本机能登录上去(服务器本身)
        factory.setPassword("password");
        //建立连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //声明队列 [queueDeclare()的四个参数 1.队列名  2.是否持久。即如果服务器重启了那么这个队列是否还需要存在
        // 3.是不是独有。 这个队列是否仅能给我们这个连接使用  4.是不是需要自动删除。在队列没有使用的情况下自动删除
        // 5.]
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发布消息
        String message = "Hello World";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8")); //basicPublish() 就是用来发送消息的,四个参数: 1.交换机  2.routingKey
        //3.pops,除了消息体之外,还有配置  4.body
        System.out.println("发送了消息:" + message);
        //关闭连接
        channel.close();
        connection.close();

    }
}
