package workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者,接收前面的批量消息(NewTask的批量消息)
 */
public class Worker {

    //QUEUE_NAME 在接收的时候要对应起来,要用同一个队列才能收到消息
    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2.设置RabbitMQ地址
        factory.setHost("175.178.91.105"); //RabbitMQ要想被外界访问进来要开启 5672 端口
        factory.setUsername("admin");  //RabbitMQ的guest用户只有本机能登录上去(服务器本身)
        factory.setPassword("password");
        //3.建立连接
        Connection connection = factory.newConnection();
        //4.获取信道
        Channel channel = connection.createChannel();
        //声明队列 [queueDeclare()的四个参数 1.队列名  2.是否持久。即如果服务器重启了那么这个队列是否还需要存在
        // 3.是不是独有。 这个队列是否仅能给我们这个连接使用  4.是不是需要自动删除。在队列没有使用的情况下自动删除 5.  ]
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        System.out.println("---开始接收消息---");
        channel.basicQos(1);  //表示最希望处理的消息数量,该任务未完成前不会接收新消息
                // 这里把自动接收消息关闭掉。改为手动确认
        channel.basicConsume(TASK_QUEUE_NAME, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body is  " + body);
                String message = new String(body, "UTF-8");
                System.out.println("收到了消息: " + message);
                try {
                    doWork(message);
                } finally {
                    System.out.println("消息处理完成");
                    //确认消息的工作
                    channel.basicAck(envelope.getDeliveryTag(),false); //这里的第一个参数为模板。  第二个参数为是否把多个任务一起确认
                }
            }
        });
    }

    //根据消息内容做处理
    private static void doWork(String task) {
        char[] chars = task.toCharArray();
        for (char ch : chars) {
            if (ch == '.') {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
