package com.wbd.spring.security.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.spring.security.utils.JwtUtils;

@RestController
public class SystemController {
	private final String USER_NAME_KEY = "USER_NAME";
	private final Long EXP_IMT = 3600_000L;

	@GetMapping("/api/test")
	public Object helloWorld(@RequestAttribute(value = USER_NAME_KEY) String username) {

		return "jwd your username=" + username;
	}

	@PostMapping("/test/login")
	public Object login(String name, String pwd) {

		if (isValidPassword(name, pwd)) {

			// 将用户的信息存入到jwt中
			Map<String, Object> map = new HashMap<String, Object>();

			map.put(USER_NAME_KEY, name);
			String jwt = JwtUtils.sign(map, EXP_IMT);

			return new HashMap<String, String>() {
				{
					put("token", jwt);
				}

			};
		} else {

			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 验证密码是否正确，模拟
	 */
	private boolean isValidPassword(String name, String password) {
		return "admin".equals(name) && "admin".equals(password);
	}

}
