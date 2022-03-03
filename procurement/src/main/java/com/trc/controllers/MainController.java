package com.trc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.LogsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement")
public class MainController 
{
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
			
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
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("User is leaving the system, using the LogOff button");
		log.setObject("");
		serviceLogs.saveLog(log);
        
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
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
				
		//System.out.println(pass1);
        LogsEntity log=new LogsEntity();
					
		if(pass1.equals(pass2))
		{
			
			if(pass1.length() < 6)
			{
				message="Password is too short...";
				fileLocator="false";
				
				//Processing logs
				log.setSubject(quser.getEmail());
				log.setAction("User failing to change password");
				log.setObject("Reported error is: Password is too short... ");
				serviceLogs.saveLog(log);
			}
			else
			{	
				//Checking for at least one capital letter and one number
				priznak=serviceUsers.checkString(pass1);
								
				if(priznak.equals("false"))
				{
					message="Password is invalid...";
					fileLocator="false";
					
					//Processing logs
					log.setSubject(quser.getEmail());
					log.setAction("User failing to change password");
					log.setObject("Reported error is: Password is not meeting the required format... ");
					serviceLogs.saveLog(log);
					
				}
				if(priznak.equals("true"))
				{
								    				    	
					serviceUsers.setPass(quserId,pass1);
					message="Password was updated successfully...";
					fileLocator="true";
					
					//Processing logs
					log.setSubject(quser.getEmail());
					log.setAction("User successfully changed her/his password");
					log.setObject("User is using the 'Change My Password' feature from the main menu");
					serviceLogs.saveLog(log);
					
				}
				
			}
			
		}
		else
		{
			message="Password fields do not match...";
			fileLocator="false";
			
			//Processing logs
			log.setSubject(quser.getEmail());
			log.setAction("User failed to change his/her password");
			log.setObject("Reported error: The two strings do not match..");
			serviceLogs.saveLog(log);
		}
		                
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		model.addAttribute("fileLocator",fileLocator);
					
				
		return "usersPCredirect";
				
	}
	
	@RequestMapping(path="/about",method=RequestMethod.POST)
	public String about(Model model, Long quserId)
	{
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("User is visiting the 'About' section");
		log.setObject("");
		serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "about";
			
	}
	
}
