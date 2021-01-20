package com.example.rabbit.producer.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-31 16:02
 */
@Getter
@Setter
@ToString
@Data
public class RabbitEntity implements Serializable {
	private static final long serialVersionUID = -1908544046021344687L;
	private String name;
	private Integer age;
	
	public RabbitEntity(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
}
