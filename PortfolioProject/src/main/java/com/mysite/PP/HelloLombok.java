package com.mysite.PP;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

/*
 * 기본기능 테스트 클래스
 *  @RequiredArgsConstructor: 인스턴스에 final을 붙이면 자동으로 생성자를 만들어줌
 */
@RequiredArgsConstructor
@Getter
@Setter
public class HelloLombok {
	
	private String hello;
	private int lombok;
	
	public static void main(String[] args) {
		HelloLombok helloLombok = new HelloLombok();
		helloLombok.setHello("헬로우");
		helloLombok.setLombok(5);
		
		System.out.println(helloLombok.getHello() + helloLombok.getLombok());
		
		A a = new A();
		
	}

}


class A {
	static String a = "hello";
}

class B {
	String b = "h";
}