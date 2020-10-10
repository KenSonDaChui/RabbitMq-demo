package com.jiucheng;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @功能描述: 消费者01
 * @Auther: Administrator
 * @Date: 2019/9/13 12:28
 */
public class Consumer01 {

    private static final String QUEUE= "hello , xiaokeai";
    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置链接
        connectionFactory.setHost("192.168.241.128");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置用户名,密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        //设置虚拟机
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            //获取链接
            connection = connectionFactory.newConnection();
            //获得链接通道
            channel = connection.createChannel();
            //声明链接
            //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            channel.queueDeclare(QUEUE, true, false, false, null);
            //监听消息String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, Consumer callback


            /**
             * 监听消息,如果消息消费成功,会执行defaultConsumer里面的方法
             */
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
                /**
                 *
                 * @param consumerTag   标识消费者id,可以获得消费者信息
                 * @param envelope      信封,可以获得信息
                 * @param properties    发送消息时的设置的属性,比如设置了消息的存活时间
                 * @param body          消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("========================消费成功=============================");
                    System.out.println("消费者id:"+consumerTag);
                    System.out.println("交换机:"+envelope.getExchange());
                    System.out.println("路由:"+envelope.getRoutingKey());
                    System.out.println( new String(body,"utf-8"));
                    System.out.println("==============================================================");
                }
            };

            //消费消息
            channel.basicConsume(QUEUE,true,defaultConsumer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
}
