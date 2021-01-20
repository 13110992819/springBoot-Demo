package com.example.demo.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.client.RestTemplate;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-16 15:37
 */
@RibbonClient("test")
public class ConsumerServiceImpl {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String consumer() {
		return restTemplate.getForObject("http://eureka-client/dc", String.class);
	}
	
	public String fallback() {
		return "fallback";
	}
		
}
