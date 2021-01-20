package com.example.zuulgateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zuulgateway.service.UserService;
import org.springframework.stereotype.Component;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-16 17:01
 */
@Component
public class UserServiceImpl implements UserService {
	@Override
	public String test() {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("fail","fail");
		return JSON.toJSONString(jsonObject);
	}
}
