package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.ItemsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.ItemsService;
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
			
			service.deleteItemById(id);
			
			message="Items was successful removed...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "itemsRedirect";
			
		}
		
		@RequestMapping(path="/createItem", method=RequestMethod.POST)
		public String createOrUpdateItem(Model model, ItemsEntity item, Long quserId)
		{
			String message=null;
			
			service.createOrUpdate(item);
			
			message="List was updated successfully...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "itemsRedirect";
			
			
		}
		

	
}
