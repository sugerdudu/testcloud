package com.sixi.oaservice.systemmanageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统管理微服务
 *
 * @author Administrator
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.sixi.feignservice.user"})
@ComponentScan(basePackages = {"com.sixi.oaservice","com.sixi.oaplatform"})
public class SystemManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemManageServiceApplication.class, args);
	}

}
