package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-16 15:43
 */
@Component
public class UserServiceImpl implements UserService {
	@Override
	public String test() {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("chenshan","enen");
		return JSON.toJSONString(jsonObject);
	}
}
