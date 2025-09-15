package aq.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.service.HelloService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {

	private final HelloService helloService;
	
	@GetMapping("/hello")
	public String sayHello() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return helloService.sayHello(authentication);
	}
}
