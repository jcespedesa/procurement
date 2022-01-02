package com.trc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.UsersEntity;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement")
public class MainController 
{
	@Autowired
	UsersService serviceUsers;
	
	//@Autowired
    private PasswordEncoder passwordEncoder;
	
		
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
	
	@RequestMapping(path="/logOut",method=RequestMethod.POST)
	public String logout(Model model, Long quserId)
	{
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "login";
		
		
	}
	
	
	@RequestMapping(path="/changePass",method=RequestMethod.POST)
	public String changePassSel(Model model, Long quserId)
	{
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "changePassForm";
	
	}
	
	@RequestMapping(path="/changePassExe", method=RequestMethod.POST)
	public String changePassExe(Model model, String pass1, String pass2,Long quserId)
	{
		
		String message=null;
		String priznak=null;
		String fileLocator=null;
		String encodedPass=null;
		
		System.out.println(pass1);
					
		if(pass1.equals(pass2))
		{
			
			if(pass1.length() < 6)
			{
				message="Password is too short...";
				fileLocator="false";
			}
			else
			{	
				//Checking for at least one capital letter and one number
				priznak=serviceUsers.checkString(pass1);
								
				if(priznak.equals("false"))
				{
					message="Password is invalid...";
					fileLocator="false";
				}
				if(priznak.equals("true"))
				{
					//Encoding password
			    	encodedPass=passwordEncoder.encode(pass1);
			    				    	
					serviceUsers.setPass(quserId,encodedPass);
					message="Password was updated successfully...";
					fileLocator="true";
					
				}
				
			}
			
		}
		else
		{
			message="Password fields do not match...";
			fileLocator="false";
		}
			
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		model.addAttribute("fileLocator",fileLocator);
					
				
		return "usersRedirect";
				
	}
	
}
