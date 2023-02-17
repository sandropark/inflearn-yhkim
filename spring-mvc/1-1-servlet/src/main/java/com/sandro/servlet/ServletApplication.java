package com.sandro.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan		// 서블릿 자동생성
@SpringBootApplication
public class ServletApplication {

	// TODO : 설정 다시 해보기
	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

}
