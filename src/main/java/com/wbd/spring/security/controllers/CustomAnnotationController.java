package com.wbd.spring.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.spring.security.annotation.CustomAnnotation;

/**
 * spring的自定义注解分为三部分
 * 1.声明注解类
 * 2.注解实现类
 * 3.使用注解实例
 * @author zgh
 *
 */
@RestController
public class CustomAnnotationController {

	@CustomAnnotation(requestUrl ="/test/annotation请求")
	@GetMapping("/test/annotation")
	public Object testAnnotation() {
		
		System.out.println("spring的自定义注解");
		
		return "java annotation";
	}
	
}
