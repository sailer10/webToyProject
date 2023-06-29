package com.mysite.PP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBootApplication : 이 에너테이션을 통해 스프링부트의 모든 설정이 관리됨. 프로젝트의 시작을 담당하는 클래스에 적용되있음.
@SpringBootApplication
public class PortfolioProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioProjectApplication.class, args);
	}

}
