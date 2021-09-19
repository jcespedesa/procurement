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

import com.trc.entities.PeripheralsEntity;
import com.trc.services.PeripheralsService;
import com.trc.services.RecordNotFoundException;


@Controller
@RequestMapping("/procurement/peripherals")
public class PeripheralsController 
{
	@Autowired
	PeripheralsService service;
	
	//CRUD operations for Settings
	
	@GetMapping("/list")
	public String getAllPeripherals(Model model)
	{
		List<PeripheralsEntity> list=service.getAllPeripherals();
		
		model.addAttribute("peripherals",list);
		
		return "peripheralsList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editPeripheralsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
	{
		
		if(id.isPresent())
		{
			PeripheralsEntity entity=service.getPeripheralById(id.get());
			model.addAttribute("peripheral",entity);
		}
		else
		{
			model.addAttribute("peripheral",new PeripheralsEntity());
			
		}
		return "peripheralsAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deletePeripheralById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deletePeripheralById(id);
		
		return "redirect:/procurement/peripherals/list";
		
	}
	
	@RequestMapping(path="/createPeripheral", method=RequestMethod.POST)
	public String createOrUpdatePeripheral(PeripheralsEntity peripheral)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ peripheral);
		
		service.createOrUpdate(peripheral);
		
		return "redirect:/procurement/Peripherals/list";
		
		
	}

}
