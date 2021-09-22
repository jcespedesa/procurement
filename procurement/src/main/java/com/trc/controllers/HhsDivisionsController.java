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

import com.trc.entities.HhsDivisionsEntity;
import com.trc.services.HhsDivisionsService;
import com.trc.services.RecordNotFoundException;


@Controller
@RequestMapping("/procurement/hhsDivisions")
public class HhsDivisionsController 
{
	@Autowired
	HhsDivisionsService service;
	
	//CRUD operations for HHS Divisions
	
		@GetMapping("/list")
		public String getAllDivisions(Model model)
		{
			//List<HhsDivisionsEntity> list=service.getAllDivisions();
			
			List<HhsDivisionsEntity> list=service.getAllByName();
			
			model.addAttribute("divisions",list);
			
			return "hhsDivisionsList";
			
			
		}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editDivisionsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
			
						
			return "hhsDivisionsAddEdit";
		}
		
		@RequestMapping(path="/delete/{id}")
		public String deleteSiteById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
		{
			
			service.deleteDivisionById(id);
			
			return "redirect:/procurement/hhsDivisions/list";
			
		}
		
		@RequestMapping(path="/createDivision", method=RequestMethod.POST)
		public String createOrUpdateDivision(HhsDivisionsEntity division)
		{
			//System.out.println("Inside the controller to update or create. Object is: "+ division);
			
			service.createOrUpdate(division);
			
			return "redirect:/procurement/hhsDivisions/list";
			
			
		}
}
