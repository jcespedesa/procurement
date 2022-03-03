package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DivisionsService;
import com.trc.services.LogsService;
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
	
	@Autowired
	LogsService serviceLogs;
	
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
		
		//Retrieving division identity
        DivisionsEntity division=service.getDivisionById(id);
		
		service.deleteDivisionById(id);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Deleting Division from the database. Item ID is "+ division.getDivisionid());
		log.setObject(division.getDname());
		serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "divisionsRedirect";
		
	}
	
	@RequestMapping(path="/createDivision", method=RequestMethod.POST)
	public String createOrUpdateDivision(Model model, DivisionsEntity division, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localDivision=null;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        if(division.getDivisionid()!=null)
        {
        	//Modify the record
        	
        	service.createOrUpdate(division);
        	
        	message="Division record was modified successfully...";
			
    		//Processing logs
    		LogsEntity log=new LogsEntity();
    		log.setSubject(quser.getEmail());
    		log.setAction("Modifying division. Item ID is "+ division.getDivisionid());
    		log.setObject(division.getDname());
    		serviceLogs.saveLog(log);
        	
        }
        else
        {
		
        	//Checking if this period is not already in the system
        	localDivision=division.getDnumber();
        	priznakDuplicate=service.findDuplicates(localDivision);
					
        	if(priznakDuplicate==0)
        	{
		
        		service.createOrUpdate(division);
		
        		message="New Division was added successfully...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new Division. Item ID is "+ division.getDnumber());
        		log.setObject(division.getDname());
        		serviceLogs.saveLog(log);
		
        	}	
        	else
        	{
        		message="Error: Division duplicate found, new record was not created at this time. Please review the list of divisions again...";
			
			//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new division due to duplicity: "+ division.getDnumber());
        		log.setObject(division.getDname());
        		serviceLogs.saveLog(log);
			
        	}
        }	
		        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "divisionsRedirect";
		
		
	}
}
