package com.jiucheng;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @功能描述:
 * @Auther: Administrator
 * @Date: 2019/9/14 12:18
 */

@SuppressWarnings("all")
public class Producer04_topics {

    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";  //队列名称
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";      //队列名称
    private static final String QUEUE_TOPICS_INFORM = "exchange_topics_inform";  //交换机
    private static final String ROUTINGSKEY_EMAIL = "inform.#.email.#";   //路由
    private static final String ROUTINGSKEY_SMS = "inform.#.sms.#";       //路由


    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置链接
        connectionFactory.setHost("127.0.0.1");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置用户名,密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel =null;
        try {
            //获取链接
            connection = connectionFactory.newConnection();
            //获得链接通道
            channel = connection.createChannel();
            //声明交换机名称和类型
            channel.exchangeDeclare(QUEUE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);
            //声明链接
            //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE_INFORM_EMAIL ,true, false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS ,true, false,false,null);
            //发送消息到交换机之前,设置交换机属性
            /************************************这里没有设置,使用默认交换机***************************************
             *channel.exchangeBind()
             * 设置交换机方法
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,QUEUE_TOPICS_INFORM,ROUTINGSKEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS,QUEUE_TOPICS_INFORM,ROUTINGSKEY_SMS);
            //发送消息String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String message = ("我是九城:" + format);
            for (int i = 0; i < 5; i++) {
                channel.basicPublish(QUEUE_TOPICS_INFORM,"inform.email",null,message.getBytes());
                System.out.println("========================消息发送成功:生产者01===inform.email======================"+i);
            }
//            for (int i = 0; i < 5; i++) {
//                channel.basicPublish(QUEUE_TOPICS_INFORM,"inform.sms",null,message.getBytes());
//                System.out.println("========================消息发送成功:生产者01==inform.sms======================="+i);
//            }
            for (int i = 0; i < 5; i++) {
                channel.basicPublish(QUEUE_TOPICS_INFORM,"inform.sms.email",null,message.getBytes());
                System.out.println("========================消息发送成功:生产者01==inform.sms.email======================="+i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
