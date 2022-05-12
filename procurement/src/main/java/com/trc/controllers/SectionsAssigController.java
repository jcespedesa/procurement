package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.LogsEntity;
import com.trc.entities.SectionAssigEntity;
import com.trc.entities.SectionsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SectionsAssigService;
import com.trc.services.SectionsService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/sectionsAssig")
public class SectionsAssigController 
{
	@Autowired
	SectionsAssigService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	SectionsService serviceSections;
	
	@Autowired
	LogsService serviceLogs;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAssigsByAssetId(Model model,String userId,Long quserId)
	{
		Long userIdLong=null;
		
		String sectionNumber=null;
		String sectionName=null;
		
		userIdLong=Long.parseLong(userId);
		
		List<SectionAssigEntity> list=service.getAssigById(userId);
		
		for(SectionAssigEntity section : list)
		{
				
			//finding section name
			sectionNumber=section.getAssigSectionNumber();
			sectionName=serviceSections.getSectionByNumber(sectionNumber);
			
			section.setUsername(sectionName);
		}	
		
		//Retrieving user data
        UsersEntity user=serviceUsers.getUserById(userIdLong);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("sections",list);
		model.addAttribute("userId",userId);
		model.addAttribute("user",user);
			
		return "sectionsAssigView";
		
		
	}
	
	@RequestMapping(path="/add", method=RequestMethod.POST)
	public String assigSections(Model model,String userId,Long quserId)
	{
		Long userIdLong=null;
					
		userIdLong=Long.parseLong(userId);
		
		List<SectionsEntity> list=serviceSections.getAllActives();
		
		//Retrieving user data
        UsersEntity user=serviceUsers.getUserById(userIdLong);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Creating the object for the form
        model.addAttribute("assigSection",new SectionAssigEntity());
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("sections",list);
		model.addAttribute("userId",userId);
		model.addAttribute("user",user);
			
		return "sectionsAssigAdd";
		
	}

	@RequestMapping(path="/addSection", method=RequestMethod.POST)
	public String addSection(Model model,String userId,Long quserId,SectionAssigEntity newSection)
	{
		
		int priznakDuplicate=0;
		
		String message=null;
		
			
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Checking if the section is not already in added for this user
    	priznakDuplicate=service.findDuplicates(userId,newSection.getAssigSectionNumber());
    	
    	if(priznakDuplicate==0)
    	{
    		//Creating the new assignation
    		service.createAssig(newSection);
		
    		message="New Section was assignated successfully...";
		
    		//Processing logs
    		LogsEntity log=new LogsEntity();
    		log.setSubject(quser.getEmail());
    		log.setAction("Assigning new section for the user. User ID is "+ userId);
    		log.setObject("And section ID is: "+ newSection.getSectionid());
    		serviceLogs.saveLog(log);
	
    	}
    	else
    		message="Section is already assigned to this user. No changes were made this time...";
                        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("message",message);
		model.addAttribute("userId",userId);
					
		return "sectionsAssigRedirect";
		
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String removeSection(Model model,String userId,Long quserId,String sectionNumber) throws RecordNotFoundException
	{
		String message="Section was removed successfully...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Retrieving section identity
        String section=serviceSections.getSectionByNumber(sectionNumber);
        
        //Removing assigned section
        service.deleteSectionAssigByNumber(sectionNumber);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Removing assigned section for the user. User ID is "+ userId);
		log.setObject("And section ID is: "+ section);
		serviceLogs.saveLog(log);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("message",message);
		model.addAttribute("userId",userId);
		
		return "sectionsAssigRedirect";
		
	}


}
