package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.LogsEntity;
import com.trc.entities.SectionsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SectionsService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/sections")
public class SectionsController 
{
	
	@Autowired
	SectionsService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllSections(Model model, Long quserId)
	{
				
		List<SectionsEntity> list=service.getAllByName();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("sections",list);
		
		return "sectionsList";
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editSectionById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		
		if(id.isPresent())
		{
			SectionsEntity entity=service.getSectionById(id.get());
			model.addAttribute("section",entity);
		}
		else
		{
			model.addAttribute("section",new SectionsEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "sectionsAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteSectionById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving section identity
        SectionsEntity section=service.getSectionById(id);
		
		service.deleteSectionById(id);
		
		message="Section was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Deleting Section record from the database. Section ID was "+ section.getSectionNumber());
      	log.setObject(section.getSectionName());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "sectionsRedirect";
		
	}
	
	@RequestMapping(path="/createSection", method=RequestMethod.POST)
	public String createOrUpdateSection(Model model, SectionsEntity section, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localSection=null;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
		
        if(section.getSectionid()!=null)
        {
        	//Modify the record
        
        	service.createOrUpdate(section);
		
        	message="Record was successful updated...";
		
			//Processing logs
	      	LogsEntity log=new LogsEntity();
	      	log.setSubject(quser.getEmail());
	      	log.setAction("Modifying Section record in the database. Section ID was "+ section.getSectionNumber());
	      	log.setObject(section.getSectionName());
	      	serviceLogs.saveLog(log);
	        
        }
        else
        {
        	//Creating a new record
        	
        	//Checking if this item is not already in the system
        	localSection=section.getSectionNumber();
        	priznakDuplicate=service.findDuplicates(localSection);
        	
        	if(priznakDuplicate==0)
        	{
        		service.createOrUpdate(section);
				
        		message="New section was created successfully...";
        		
		   		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new section record in the database. Section ID is "+ section.getSectionNumber());
        		log.setObject(section.getSectionName());
        		serviceLogs.saveLog(log);
        	}
        	else
        	{
        		message="Error: Duplicate Section was found, new record was not created at this time. Please review the list of sections again...";
				
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new section due to duplicity: "+ section.getSectionNumber());
        		log.setObject(section.getSectionName());
        		serviceLogs.saveLog(log);
        	}
        	
        	
        }
	      	
	    model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
			
		model.addAttribute("message",message);
		
		return "sectionsRedirect";
		
	}

}
