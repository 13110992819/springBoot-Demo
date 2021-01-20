package com.example.demo.conrabbitmq.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-29 17:07
 */
//@Component
//@RabbitListener(queues="tm.order_syn")
//public class RabbitCousmer {
//	@Resource
//	private RabbitTemplate rabbitTemplate;
//	@RabbitHandler
//	private void getMessage(String content){
//		System.out.println(content);
//	}
//}
