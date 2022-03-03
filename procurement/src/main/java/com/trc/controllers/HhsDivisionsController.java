package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.HhsDivisionsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.HhsDivisionsService;
import com.trc.services.LogsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement/hhsDivisions")
public class HhsDivisionsController 
{
	@Autowired
	HhsDivisionsService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
	//CRUD operations for HHS Divisions
	
		@RequestMapping(path="/list", method=RequestMethod.POST)
		public String getAllDivisions(Model model, Long quserId)
		{
			//List<HhsDivisionsEntity> list=service.getAllDivisions();
			
			List<HhsDivisionsEntity> list=service.getAllByName();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("divisions",list);
			
			return "hhsDivisionsList";
			
			
		}
		
		@RequestMapping(path="/edit", method=RequestMethod.POST)
		public String editDivisionsById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
		{
									
			if(id.isPresent())
			{
				HhsDivisionsEntity entity=service.getDivisionById(id.get());
				model.addAttribute("division",entity);
			}
			else
			{
				model.addAttribute("division",new HhsDivisionsEntity());
				
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
						
			return "hhsDivisionsAddEdit";
		}
		
		@RequestMapping(path="/delete", method=RequestMethod.POST)
		public String deleteSiteById(Model model, Long id, Long quserId) throws RecordNotFoundException
		{
			String message=null;
			
			//Retrieving division identity
	        HhsDivisionsEntity division=service.getDivisionById(id);
						
			service.deleteDivisionById(id);
			
			message="Division was deleted...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	      //Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Deleting HHS Division from the database. Item ID is "+ division.getDivisionNumber());
			log.setObject(division.getDivision());
			serviceLogs.saveLog(log);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "hhsDivisionsRedirect";
			
		}
		
		@RequestMapping(path="/createDivision", method=RequestMethod.POST)
		public String createOrUpdateDivision(Model model, HhsDivisionsEntity division, Long quserId)
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
			
				message="HHS Division was successfully modified...";
				        
				//Processing logs
				LogsEntity log=new LogsEntity();
				log.setSubject(quser.getEmail());
				log.setAction("Modifying HHS Division record. Item ID is "+ division.getDivisionNumber());
				log.setObject(division.getDivision());
				serviceLogs.saveLog(log);
			
	        }
	        else
	        {
	        	//Creating a new record
	        	
	        	//Checking if this item is not already in the system
	        	localDivision=division.getDivisionNumber();
	        	priznakDuplicate=service.findDuplicates(localDivision);
	        	
	        	if(priznakDuplicate==0)
	        	{
	        		service.createOrUpdate(division);
					
	        		message="New HHS Division was created successfully...";
	        		
			   		//Processing logs
	        		LogsEntity log=new LogsEntity();
	        		log.setSubject(quser.getEmail());
	        		log.setAction("Creating new HHS Division record in the database. HHS Division ID is "+ division.getDivisionNumber());
	        		log.setObject(division.getDivision());
	        		serviceLogs.saveLog(log);
	        	}
	        	else
	        	{
	        		message="Error: Duplicate HHS Division number was found. New record was not created at this time. Please review the list of HHS Divisions again...";
					
	        		//Processing logs
	        		LogsEntity log=new LogsEntity();
	        		log.setSubject(quser.getEmail());
	        		log.setAction("Failing to create a new HHS Division due to duplicity: "+ division.getDivisionNumber());
	        		log.setObject(division.getDivision());
	        		serviceLogs.saveLog(log);
	        	}
	        	        	
	        }
	        	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "hhsDivisionsRedirect";
			
			
		}
}
