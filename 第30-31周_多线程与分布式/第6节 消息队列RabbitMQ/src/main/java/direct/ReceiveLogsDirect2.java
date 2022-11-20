package direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接收1种等级的日志
 */
public class ReceiveLogsDirect2 {

    private static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("175.178.91.105");
        factory.setUsername("admin");
        factory.setPassword("password");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //定义一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //需要定义一个队列,因为接收消息和队列绑定不和交换机绑定。  生成一个随机的临时的queue,日志具有很强的时效性,用自动生成的临时队列更符合业务需求(非持久会定期清除)
        String queueName = channel.queueDeclare().getQueue();
        //一个交换机同时绑定1个queue
        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println("开始接收消息");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("收到消息: " + message);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
