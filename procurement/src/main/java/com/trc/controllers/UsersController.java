package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DivisionsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/users")
public class UsersController 
{
	@Autowired
	UsersService service;
		
	@Autowired
	DivisionsService serviceDivisions;
	
	@Autowired
	UsersService serviceUsers;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllUsers(Model model, Long quserId)
	{
		
		List<UsersEntity> list=service.getAllUsers();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("users",list);
		
		return "usersList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editUsersById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		
		//Preparing list of divisions
		List<DivisionsEntity> divisions=serviceDivisions.getAllDivisions();
		
		if(id.isPresent())
		{
			UsersEntity entity=service.getUserById(id.get());
			model.addAttribute("user",entity);
		}
		else
		{
			model.addAttribute("user",new UsersEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("divisions",divisions);
		
		return "usersAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteUserById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		service.deleteUserById(id);
		
		message="User was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "usersRedirect";
		
	}
	
	@RequestMapping(path="/createUser", method=RequestMethod.POST)
	public String createOrUpdateUser(Model model, UsersEntity user, Long quserId)
	{
		String message=null;
		
		service.createOrUpdate(user);
		
		message="List was updated...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "usersRedirect";
		
		
	}
	
	
	@RequestMapping(path="/resetPass", method=RequestMethod.POST)
    public String resetPassById(Model model, Long id, Long quserId) throws RecordNotFoundException 
    {
    	UsersEntity entity=service.getUserById(id);
    	
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
				
        model.addAttribute("user",entity);
                
        return "usersPassReset";
    }
	
	@RequestMapping(path="/changePass", method=RequestMethod.POST)
    public String changePass(Model model, Long id, Long quserId) throws RecordNotFoundException 
    {
		String message=null;
		
    	service.resetPass(id);
    	
    	message="Password was reset...";
    	
    	//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
    	
    	model.addAttribute("message",message);
    	
    	return "usersRedirect";
    }
	
	@RequestMapping(path="/setPass", method=RequestMethod.POST)
    public String setPass(Model model,Long id, String newPass, Long quserId) throws RecordNotFoundException 
    {
		String message=null;
		String priznak=null;
		
		
		//Checking for at least one capital letter and one number
		priznak=service.checkString(newPass);
		    	
    	if(priznak.equals("false"))
    	{
    		message="The password is invalid. No changes were made this time. Please try again...";
    		
    	}
    	else
    	{
    		service.setPass(id,newPass);
    		message="Password was set...";
    	}
    	
    	
    	//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
    	
    	model.addAttribute("message",message);
    	
    	return "usersRedirect";
    }
	
}
