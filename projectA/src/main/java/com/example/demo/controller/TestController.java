package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.es.impl.ESHelper;
import com.example.demo.entity.RabbitEntity;
import com.example.demo.prorabbitmq.RabbitProBinder;
import com.example.demo.prorabbitmq.impl.RabbitmqHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-09 11:44
 */
@RestController
@RequestMapping("/hello")
public class TestController {
	@Resource
	DiscoveryClient client;
	@Resource
	ESHelper esHelper;
	@Resource
	RabbitmqHelper rabbitmqHelper;
	@RequestMapping("hello")
	public String hello(){
		List<String> o=client.getServices();
		return JSON.toJSONString(o);
	}
	@RequestMapping("test")
	public String test(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("test","test");
		throw new RuntimeException();
	}
	@RequestMapping("save")
	public String save(@RequestBody String body) throws Exception{
		JSONObject json=JSONObject.parseObject(body);
		String corpid=json.getString("corpid");
		String id = json.getString("id");
		String str=json.getString("str");
		String keyword=json.getString("keyword");
		Map<String,Object> param=esHelper.test(corpid,id,str,keyword);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code","1");
		jsonObject.put("msg","成功");
		jsonObject.put("data",param);
		return JSON.toJSONString(jsonObject);
	}
	
	@RequestMapping("message")
	public String message(){
		rabbitmqHelper.sendMessage();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code","1");
		jsonObject.put("msg","成功");
		return JSON.toJSONString(jsonObject);
	}
}
