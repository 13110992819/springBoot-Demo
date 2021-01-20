package com.example.rabbit.consumer.con;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-31 15:38
 */
@Service("streamOut")
public interface StreamOut {
	@Input("input")
	SubscribableChannel input();
}
