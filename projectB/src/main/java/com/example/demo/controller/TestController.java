package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.ConsumerServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-11 15:22
 */
@RestController
@RequestMapping("/test")
public class TestController {
	@Resource
	UserService userService;
	@Resource
	ConsumerServiceImpl consumerService;
	@ResponseBody
	@RequestMapping("/test")
	public String hello(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("oo",userService.test());
		jsonObject.put("i",consumerService.consumer());
		return JSON.toJSONString(jsonObject);
	}
	
}


