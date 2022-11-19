package workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 任务有所耗时,多个任务
 */
public class NewTask {

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
        for (int i = 0; i < 10; i++) {
            String message;
            if (i % 2 == 0) {
                message = i + "...";
            } else {
                message = String.valueOf(i);
            }
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("发送了消息: " + message);
        }
        channel.close();
        connection.close();
    }
}
