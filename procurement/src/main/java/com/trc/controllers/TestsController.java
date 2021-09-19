package com.trc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.services.EmailService;
import com.trc.services.TestsService;


@Controller
@RequestMapping("/procurement/tests")
public class TestsController 
{
	@Autowired
	TestsService service;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping("/emailSel")
	public String testEmailSel()
	{
			
		return "testEmailSel";
			
	}
	
	@RequestMapping(path="/testEmailSending", method=RequestMethod.POST)
	public String testEmail(Model model, String toEmail)
	{
		String subject="Sending a test email";
		String body="This is a test, please do not respond...";
		
		//Sending the test email
		emailService.sendMail(toEmail,subject,body);
		
		model.addAttribute("toEmail",toEmail);
		
		return "testEmailRedirect";
		
	}
	
}
