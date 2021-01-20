package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-30 16:30
 */
@Getter
@Setter
@Data
public class RabbitEntity implements Serializable {
	private static final long serialVersionUID = -1908544046021344687L;
	
	private String name;
	private Integer number;
	public RabbitEntity(){
	}
	public RabbitEntity(String name, Integer number) {
		this.name = name;
		this.number = number;
	}
}
