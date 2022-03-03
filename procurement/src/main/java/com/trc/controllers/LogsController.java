package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.entities.LogsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/logs")
public class LogsController 
{
	@Autowired
	LogsService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@GetMapping("/view")
	public String getAllLogs(Model model,Long quserId)
	{
				
		List<LogsEntity> list=service.getAllLogs();
		
		//Retrieving user information
		UsersEntity quser=serviceUsers.getUserById(quserId);
								
		model.addAttribute("quser",quser);
		model.addAttribute("quserId",quserId);
		
		model.addAttribute("logs",list);
			
		return "logsView";
			
			
	}
	
}
