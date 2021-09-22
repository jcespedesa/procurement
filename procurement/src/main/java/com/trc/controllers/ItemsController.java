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

import com.trc.entities.ItemsEntity;
import com.trc.services.ItemsService;
import com.trc.services.RecordNotFoundException;

@Controller
@RequestMapping("/procurement/Items")
public class ItemsController 
{
	@Autowired
	ItemsService service;
	
	//CRUD operations for items
	
		@GetMapping("/list")
		public String getAllItems(Model model)
		{
						
			List<ItemsEntity> list=service.getAllItemsListDesc();
			
			model.addAttribute("items",list);
			
			return "itemsList";
			
			
		}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editItemsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
			return "itemsAddEdit";
		}
		
		@RequestMapping(path="/delete/{id}")
		public String deleteItemById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
		{
			
			service.deleteItemById(id);
			
			return "redirect:/procurement/Items/list";
			
		}
		
		@RequestMapping(path="/createItem", method=RequestMethod.POST)
		public String createOrUpdateItem(ItemsEntity item)
		{
			//System.out.println("Inside the controller to update or create. Object is: "+ provider);
			
			service.createOrUpdate(item);
			
			return "redirect:/procurement/Items/list";
			
			
		}
		

	
}
