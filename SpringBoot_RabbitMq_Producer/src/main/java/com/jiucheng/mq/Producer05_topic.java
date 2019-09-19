package com.jiucheng.mq;

/**
 * @功能描述:
 * @Auther: Administrator
 * @Date: 2019/9/14 13:39
 */

import com.jiucheng.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topic {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendEmail(){
        String message = "inform.#.email";
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,RabbitMqConfig.ROUTINGSKEY_EMAIL,message);
        System.out.println("===============发送email===============");
    }
    @Test
    public void sendSms(){
        String message = "inform.#.sms.email";
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,RabbitMqConfig.ROUTINGSKEY_SMS,message);
        System.out.println("===============发送sms===============");
    }
}