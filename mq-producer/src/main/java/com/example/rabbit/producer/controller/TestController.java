package com.example.rabbit.producer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.rabbit.producer.pro.impl.StreamInImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-31 16:13
 */
@RestController
public class TestController {
	@Resource
	StreamInImpl streamInImpl;
	@RequestMapping("message")
	public String sendMessage(){
		streamInImpl.send();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code",1);
		jsonObject.put("msg","成功");
		return JSON.toJSONString(jsonObject);
	}
}
