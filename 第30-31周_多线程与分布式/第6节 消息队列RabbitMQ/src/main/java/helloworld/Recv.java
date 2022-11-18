package helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接收消息,并打印,持续运行,
 */
public class Recv {


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
        //接收消息  (Send生产者类这里是 发布消息) [三个参数:队列名、自动进行消息的确认、用来处理消息的]
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            //重写这个方法目的就是去获取到这个消息之后,对消息进行消费,,
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("收到消息: " + message);
            }
        });

    }
}
