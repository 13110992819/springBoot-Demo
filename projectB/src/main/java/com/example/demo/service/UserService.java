package com.example.demo.service;

import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-16 09:21
 */
@FeignClient(value = "register-customer",fallback = UserServiceImpl.class)
public interface UserService {
	@RequestMapping("/hello/test")
	String test();
}
