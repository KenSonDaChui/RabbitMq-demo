package com.jiucheng.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @功能描述:
 * @Auther: Administrator
 * @Date: 2019/9/14 13:05
 */

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";  //队列名称
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";      //队列名称
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";  //交换机
    public static final String ROUTINGSKEY_EMAIL = "inform.#.email.#";   //路由
    public static final String ROUTINGSKEY_SMS = "inform.#.sms.#";       //路由
    //交换机注入容器
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(false).build();
    }

    //设置队列,邮件
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    //绑定队列到交换机EMAIL
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                               @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange Exchange_topic_inform) {
        return  BindingBuilder.bind(queue).to(Exchange_topic_inform).with(ROUTINGSKEY_EMAIL).noargs();
    }
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange Exchange_topic_inform) {
        return BindingBuilder.bind(queue).to(Exchange_topic_inform).with(ROUTINGSKEY_SMS).noargs();
    }
}
