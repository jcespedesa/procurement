package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.HhsDivisionsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.HhsDivisionsService;
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
						
			service.deleteDivisionById(id);
			
			message="Division was deleted...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "hhsDivisionsRedirect";
			
		}
		
		@RequestMapping(path="/createDivision", method=RequestMethod.POST)
		public String createOrUpdateDivision(Model model, HhsDivisionsEntity division, Long quserId)
		{
			String message=null;
			
			service.createOrUpdate(division);
			
			message="List was successful updated...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "hhsDivisionsRedirect";
			
			
		}
}
