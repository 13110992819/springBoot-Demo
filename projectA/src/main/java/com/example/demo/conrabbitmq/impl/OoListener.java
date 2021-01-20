package com.example.demo.conrabbitmq.impl;

import com.example.demo.entity.RabbitEntity;
import com.example.demo.conrabbitmq.RabbitConBinder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-30 19:40
 */
@Component
//@EnableBinding(RabbitConBinder.class)
public class OoListener {
//	@StreamListener("chenshan")
	public void test(RabbitEntity rabbitEntity){
		System.out.println(rabbitEntity.toString());
	}
}
