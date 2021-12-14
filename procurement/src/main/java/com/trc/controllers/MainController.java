package com.trc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement")
public class MainController 
{
	@Autowired
	UsersService serviceUsers;
	
	@GetMapping("/login")
	public String login()
	{
				
		return "login";
		
		
	}
	
	@GetMapping("/index")
	public String index()
	{
		
		return "index";
		
		
	}
	
	@GetMapping("/test32")
	public String test32()
	{
		
		return "test32";
		
		
	}
	
	
	
}
