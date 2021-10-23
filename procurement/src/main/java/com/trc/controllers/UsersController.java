package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/list")
	public String getAllUsers(Model model)
	{
		
		List<UsersEntity> list=service.getAllUsers();
		
		model.addAttribute("users",list);
		
		return "usersList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editUsersById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
		
		model.addAttribute("divisions",divisions);
		
		return "usersAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteUserById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteUserById(id);
		
		return "redirect:/procurement/users/list";
		
	}
	
	@RequestMapping(path="/createUser", method=RequestMethod.POST)
	public String createOrUpdateUser(UsersEntity user)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ site);
		
		service.createOrUpdate(user);
		
		return "redirect:/procurement/users/list";
		
		
	}
	
	@RequestMapping(path={"/resetPass/{id}"})
    public String resetPassById(Model model, @PathVariable("id") Long id) 
    		throws RecordNotFoundException 
    {
    	    	
    	UsersEntity entity=service.getUserById(id);
    	    	
        model.addAttribute("user",entity);
                
        return "usersPassReset";
    }
	
	@RequestMapping(path="/changePass", method=RequestMethod.POST)
    public String changePass(Model model,@RequestParam("id") Long id) throws RecordNotFoundException 
    {
    	service.resetPass(id);
    	
    	return "redirect:/procurement/users/list";
    }
	
	@RequestMapping(path="/setPass", method=RequestMethod.POST)
    public String setPass(Model model,@RequestParam("id") Long id,@RequestParam("newPass") String newPass) throws RecordNotFoundException 
    {
    	service.setPass(id,newPass);
    	
    	return "redirect:/procurement/users/list";
    }
	
}
