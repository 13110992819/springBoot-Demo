package com.example.zuulgateway.service;

import com.example.zuulgateway.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-16 17:00
 */
@FeignClient(value = "register-customer",fallback = UserServiceImpl.class)
public interface UserService {
	@RequestMapping("/hello/test")
	public String test();
}
