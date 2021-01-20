package com.example.demo.prorabbitmq.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.RabbitEntity;
import com.example.demo.prorabbitmq.RabbitProBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-29 10:47
 */
@Component
@EnableBinding(RabbitProBinder.class)
public class RabbitmqHelper {
	private static final Logger LOG= LoggerFactory.getLogger(RabbitmqHelper.class);
	@Resource
	private RabbitTemplate rabbitTemplate;
	
//	@Bean
//	public ConnectionFactory connectionFactory() {
//		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("121.196.236.164",5672);
//		connectionFactory.setUsername("guest");
//		connectionFactory.setPassword("guest");
//		connectionFactory.setVirtualHost("/");
//		connectionFactory.setPublisherConfirms(true);
//		return connectionFactory;
//	}
//	@Bean
//	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	//必须是prototype类型
//	public RabbitTemplate rabbitTemplate() {
//		RabbitTemplate template = new RabbitTemplate(connectionFactory());
//		return template;
//	}
	
	@Resource
	RabbitProBinder proBinder;
	
	public void sendMessage(){
		// 声明交换器
		String exchangeName = "tmorder-exchange";
		String queueName = "tm.order_syn";
		String routingKey = "tmorder";
		for ( int i = 0; i <= 10000; i++ ) {
			MessageBuilder messageBuilder;
			MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
			messageHeaderAccessor.setHeader("type","test2");
			RabbitEntity rabbitEntity=new RabbitEntity();
			messageBuilder= MessageBuilder.withPayload(rabbitEntity).setHeaders(messageHeaderAccessor);
			proBinder.messageChannel().send(messageBuilder.build());
			rabbitTemplate.convertAndSend("tmorder-exchange", "tmorder", JSON.toJSONString(new RabbitEntity("chenshan",i)));
		}
	}
	
	public void getMessage(){
		
		MessageConverter messageConverter= rabbitTemplate.getMessageConverter();
		System.out.println(messageConverter);
	}
	
	
}
