package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.ItemsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.ItemsService;
import com.trc.services.LogsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/items")
public class ItemsController 
{
	@Autowired
	ItemsService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
	//CRUD operations for items
	
		@RequestMapping(path="/list", method=RequestMethod.POST)
		public String getAllItems(Model model, Long quserId)
		{
						
			List<ItemsEntity> list=service.getAllItemsListDesc();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
						
			model.addAttribute("items",list);
			
			return "itemsList";
			
			
		}
		
		
		@RequestMapping(path="/edit", method=RequestMethod.POST)
		public String editItemsById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
		{
			
			if(id.isPresent())
			{
				ItemsEntity entity=service.getItemById(id.get());
				model.addAttribute("item",entity);
			}
			else
			{
				model.addAttribute("item",new ItemsEntity());
				
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			return "itemsAddEdit";
		}
		
		
		@RequestMapping(path="/delete", method=RequestMethod.POST)
		public String deleteItemById(Model model, Long id, Long quserId) throws RecordNotFoundException
		{
			String message=null;
			
			//Retrieving asset identity
	        ItemsEntity item=service.getItemById(id);
			
			service.deleteItemById(id);
			
			message="IT Item was successful removed...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        //Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Deleting IT Item from the database. Item ID is "+ item.getItemNumber());
			log.setObject(item.getItem());
			serviceLogs.saveLog(log);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "itemsRedirect";
			
		}
		
		@RequestMapping(path="/createItem", method=RequestMethod.POST)
		public String createOrUpdateItem(Model model, ItemsEntity item, Long quserId)
		{
			int priznakDuplicate=0;
			
			String message=null;
			String localItem=null;
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
			
	        if(item.getItemid()!=null)
	        {
	        	//Modify the record
	        	
	        	service.createOrUpdate(item);
	        	
	        	message="Item was updated successfully...";
				
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Modifying IT Item. Item ID is "+ item.getItemNumber());
        		log.setObject(item.getItem());
        		serviceLogs.saveLog(log);
	        	
	        }
	        else
	        {
	        	//Creating a new record
	        	
	        	//Checking if this item is not already in the system
	        	localItem=item.getItemNumber();
	        	priznakDuplicate=service.findDuplicates(localItem);
			
	        	if(priznakDuplicate==0)
	        	{
	        
	        		service.createOrUpdate(item);
				
	        		message="New Item was created successfully...";
				
	        		//Processing logs
	        		LogsEntity log=new LogsEntity();
	        		log.setSubject(quser.getEmail());
	        		log.setAction("Creating IT Item. Item ID is "+ item.getItemNumber());
	        		log.setObject(item.getItem());
	        		serviceLogs.saveLog(log);
			
	        	}
	        	else
	        	{
	        		message="Error: Item duplicate found, new record was not created at this time. Please review the list of items again...";
				
	        		//Processing logs
	        		LogsEntity log=new LogsEntity();
	        		log.setSubject(quser.getEmail());
	        		log.setAction("Failing to create a new item due to duplicity: "+ item.getItemNumber());
	        		log.setObject(item.getItem());
	        		serviceLogs.saveLog(log);
				
	        	}	
	        
	        }	
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "itemsRedirect";
			
			
		}
		

	
}
