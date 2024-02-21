package com.hna.hka.archive.management.system.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: RabbitMQUtil
 * @Author: 郭凯
 * @Description: 消息队列工具类
 * @Date: 2021/6/30 15:57
 * @Version: 1.0
 */
public class RabbitMQUtil {

    public static Connection getConnection()
    {
        try
        {
            Connection connection = null;
            //定义一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("140.143.148.246");
            factory.setUsername("jxzy");
            factory.setPassword("jxzy001.");
            factory.setVirtualHost("JXZY");
            factory.setPort(5672);
            connection = factory.newConnection();
            return connection;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    //队列名称
    private static final String QUEUE_NAME = "A";

    public static void Send(){
        try
        {
            //获取连接
            Connection connection = RabbitMQUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "This is simple queue";
            //发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
            System.out.println("[send]：" + message + "生产");
            channel.close();
            connection.close();
        }
        catch (IOException | TimeoutException e)
        {
            e.printStackTrace();
        }
    }

    public static void Receive(){
        try
        {
            //获取连接
            Connection connection = RabbitMQUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //定义消费者
            DefaultConsumer consumer = new DefaultConsumer(channel)
            {
                //当消息到达时执行回调方法
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                           byte[] body) throws IOException
                {
                    String message = new String(body, "utf-8");
                    System.out.println("[Receive]：" + message + "消费");
                }
            };
            //监听队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
        }
        catch (IOException | ShutdownSignalException | ConsumerCancelledException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Receive();

    }

}
