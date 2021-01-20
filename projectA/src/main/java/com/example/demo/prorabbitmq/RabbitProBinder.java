package com.example.demo.prorabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-30 19:29
 */
@Service("rabbitProBinder")
public interface RabbitProBinder {
	@Output("test2")
	MessageChannel messageChannel();
}
