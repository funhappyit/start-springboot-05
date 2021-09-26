package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Controller
@Log
public class SampleController {
	
	@GetMapping("/")
	public String index() {
		log.info("index");
		return "index";
	}
	
	@RequestMapping("/guest")
	public void forGuest() {
		log.info("guest");
		
	}
	@RequestMapping("/manager")
	public void forManager() {
		log.info("manager");
		
	}
	@RequestMapping("/admin")
	public void forAdmin() {
		log.info("admin");		
	}
	@GetMapping("/login")
	public void login() {
		
	}
	
	@PostMapping("/login")
	public String loginTest() {
		return "redirect:/boards/list";
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		
	}
	@GetMapping("/logout")
	public void logout() {
		
	}
	
	
}
