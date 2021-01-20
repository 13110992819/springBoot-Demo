package com.example.zuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author chenshan
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulGatewayApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayApplication.class, args);
	}
	
}
