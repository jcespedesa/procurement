package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.LogsEntity;
import com.trc.entities.TitlesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
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
	
	@Autowired
	LogsService serviceLogs;
	
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
		
		//Retrieving title identity
        TitlesEntity title=service.getTitleById(id);
		
		service.deleteTitleById(id);
		
		message="Title was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Deleting Title record from the database. Title ID was "+ title.getTitleNum());
      	log.setObject(title.getTitleDesc());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "titlesRedirect";
		
	}
	
	@RequestMapping(path="/createTitle", method=RequestMethod.POST)
	public String createOrUpdateTitle(Model model, TitlesEntity title, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localTitle=null;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
		
        if(title.getTitleid()!=null)
        {
        	//Modify the record
        
        	service.createOrUpdate(title);
		
        	message="Record was successful updated...";
		
			//Processing logs
	      	LogsEntity log=new LogsEntity();
	      	log.setSubject(quser.getEmail());
	      	log.setAction("Modifying Title record in the database. Title ID was "+ title.getTitleNum());
	      	log.setObject(title.getTitleDesc());
	      	serviceLogs.saveLog(log);
	        
        }
        else
        {
        	//Creating a new record
        	
        	//Checking if this item is not already in the system
        	localTitle=title.getTitleNum();
        	priznakDuplicate=service.findDuplicates(localTitle);
        	
        	if(priznakDuplicate==0)
        	{
        		service.createOrUpdate(title);
				
        		message="New title was created successfully...";
        		
		   		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new title record in the database. Title ID is "+ title.getTitleNum());
        		log.setObject(title.getTitleDesc());
        		serviceLogs.saveLog(log);
        	}
        	else
        	{
        		message="Error: Duplicate Title was found, new record was not created at this time. Please review the list of titles again...";
				
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new title due to duplicity: "+ title.getTitleNum());
        		log.setObject(title.getTitleDesc());
        		serviceLogs.saveLog(log);
        	}
        	
        	
        }
	      	
	    model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
			
		model.addAttribute("message",message);
		
		return "titlesRedirect";
		
	}
}
