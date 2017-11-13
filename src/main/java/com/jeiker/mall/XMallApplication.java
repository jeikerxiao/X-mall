package com.jeiker.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jeiker.mall.mapper")
public class XMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(XMallApplication.class, args);
	}
}
