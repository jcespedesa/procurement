package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.TitlesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.RecordNotFoundException;
import com.trc.services.TitlesService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/titles")
public class TitlesController 
{
	@Autowired
	TitlesService service;
	
	@Autowired
	UsersService serviceUsers;
	
	//CRUD operations for titles
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllTitles(Model model, Long quserId)
	{
				
		List<TitlesEntity> list=service.getAllByName();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("titles",list);
		
		return "titlesList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editTitlesById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		
		if(id.isPresent())
		{
			TitlesEntity entity=service.getTitleById(id.get());
			model.addAttribute("title",entity);
		}
		else
		{
			model.addAttribute("title",new TitlesEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "titlesAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteTitleById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		service.deleteTitleById(id);
		
		message="Title was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "titlesRedirect";
		
	}
	
	@RequestMapping(path="/createTitle", method=RequestMethod.POST)
	public String createOrUpdateTitle(Model model, TitlesEntity title, Long quserId)
	{
		String message=null;
		
		service.createOrUpdate(title);
		
		message="List was successful updated...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "titlesRedirect";
		
	}
}
