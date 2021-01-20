package com.example.rabbit.consumer.con.impl;

import com.alibaba.fastjson.JSON;
import com.example.rabbit.consumer.StringConstant;
import com.example.rabbit.consumer.con.StreamOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
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
 * @since 2019-07-31 16:43
 */
@Slf4j
@Component
@EnableBinding(StreamOut.class)
public class StreamOutImpl {
	
	@StreamListener("input")
	public void getMessage(Message<String> message){
		System.out.println(message.getPayload());
		MessageHeaders messageHeaders=message.getHeaders();
		System.out.println(messageHeaders.get("amqp_receivedRoutingKey"));
	}
}
