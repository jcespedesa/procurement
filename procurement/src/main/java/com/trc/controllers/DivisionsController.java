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
@RequestMapping("/procurement/divisions")
public class DivisionsController 
{
	@Autowired
	DivisionsService service;
	
	@Autowired
	UsersService serviceUsers;
	
	//CRUD operations for divisions
	
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllDivisions(Model model,Long quserId)
	{
				
		List<DivisionsEntity> list=service.getAllByName();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("divisions",list);
		
		return "divisionsList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editDivisionsById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		
		if(id.isPresent())
		{
			DivisionsEntity entity=service.getDivisionById(id.get());
			model.addAttribute("division",entity);
		}
		else
		{
			model.addAttribute("division",new DivisionsEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "divisionsAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteDivisionById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message="Item was successfully deleted...";
		
		service.deleteDivisionById(id);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "divisionsRedirect";
		
	}
	
	@RequestMapping(path="/createDivision", method=RequestMethod.POST)
	public String createOrUpdateDivision(Model model, DivisionsEntity division, Long quserId)
	{
		String message=null;
				
		service.createOrUpdate(division);
		
		message="List was updated successfully...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "divisionsRedirect";
		
		
	}
}
