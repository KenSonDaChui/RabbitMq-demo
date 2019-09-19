package com.jiucheng.mq;

import com.jiucheng.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @功能描述:
 * @Auther: Administrator
 * @Date: 2019/9/14 14:11
 */
@Component
public class ReceiveHandler {


    @RabbitListener(queues={RabbitMqConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(String msg,Message message){
        System.out.println("接收email消息:"+message.toString());
    }

    @RabbitListener(queues={RabbitMqConfig.QUEUE_INFORM_SMS})
    public void receive_sms(String msg, Message message){
        System.out.println("接收sms消息:"+msg);
    }
}
