package com.sixi.oaplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wangwei
 */
@SpringBootApplication(
		exclude = {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class}
		)
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients("com.sixi.feignservice")
@ComponentScan(basePackages = {"com.sixi.oaplatform"})
public class OaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OaGatewayApplication.class, args);
	}

}
