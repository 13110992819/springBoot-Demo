package com.example.rabbit.producer.pro.impl;

import com.example.rabbit.producer.entity.RabbitEntity;
import com.example.rabbit.producer.pro.StreamIn;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-31 15:58
 */
@Service("streamInImpl")
@EnableBinding(StreamIn.class)
public class StreamInImpl {
	@Resource
	StreamIn streamIn;
	
	public void send(){
		for ( int i=0;i<=10000;i++ ){
			MessageBuilder messageBuilder;
			MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
			messageHeaderAccessor.setHeader("type","input");
			RabbitEntity rabbitEntity=new RabbitEntity("chenshan",i);
			messageBuilder= MessageBuilder.withPayload(rabbitEntity).setHeaders(messageHeaderAccessor);
			Boolean flag=streamIn.output().send(messageBuilder.build());
			System.out.println(flag);
		}
	}
}
