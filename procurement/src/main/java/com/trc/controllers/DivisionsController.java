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

import com.trc.entities.DivisionsEntity;
import com.trc.services.DivisionsService;
import com.trc.services.RecordNotFoundException;

@Controller
@RequestMapping("/procurement/Divisions")
public class DivisionsController 
{
	@Autowired
	DivisionsService service;
	
	//CRUD operations for divisions
	
	@GetMapping("/list")
	public String getAllDivisions(Model model)
	{
				
		List<DivisionsEntity> list=service.getAllByName();
		
		model.addAttribute("divisions",list);
		
		return "divisionsList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editDivisionsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
		return "divisionsAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteDivisionById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteDivisionById(id);
		
		return "redirect:/procurement/Divisions/list";
		
	}
	
	@RequestMapping(path="/createDivision", method=RequestMethod.POST)
	public String createOrUpdateDivision(DivisionsEntity division)
	{
				
		service.createOrUpdate(division);
		
		return "redirect:/procurement/Divisions/list";
		
		
	}
}
