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
import com.trc.entities.PeripheralsEntity;
import com.trc.services.ItemsService;
import com.trc.services.PeripheralsService;
import com.trc.services.RecordNotFoundException;


@Controller
@RequestMapping("/procurement/peripherals")
public class PeripheralsController 
{
	@Autowired
	PeripheralsService service;
	
	@Autowired
	ItemsService serviceItems;
	
	//CRUD operations for Settings
	
	@GetMapping("/list")
	public String getAllPeripherals(Model model)
	{
		List<PeripheralsEntity> list=service.getAllPeripherals();
		
		model.addAttribute("peripherals",list);
		
		return "peripheralsList";
		
		
	}
	
	@RequestMapping(path={"/edit/{id}"})
	public String editPeripheral(Model model,@PathVariable("id") Optional<Long> id, String assetId) throws RecordNotFoundException 
	{
		List<ItemsEntity> items=serviceItems.getAllPeripheralsHHS();		
		
		PeripheralsEntity entity=service.getPeripheralById(id.get());
		model.addAttribute("peripheral",entity);
			
		//System.out.println("modifying old item");
		
		model.addAttribute("items",items);
		model.addAttribute("assetId",assetId);
		
		return "peripheralsAddEdit";
	}
	
	@RequestMapping(path={"/create/{assetId}"})
	public String newPeripheral(Model model,@PathVariable("assetId") String assetId) throws RecordNotFoundException 
	{
		List<ItemsEntity> items=serviceItems.getAllPeripheralsHHS();		
		
		model.addAttribute("peripheral",new PeripheralsEntity());
			
		// System.out.println("creating new item");
		
		model.addAttribute("items",items);
		model.addAttribute("assetId",assetId);
		
		return "peripheralsAddEdit";
	}
	
	
	@RequestMapping(path="/delete/{id}")
	public String deletePeripheralById(Model model, @PathVariable("id") Long id, String assetId) throws RecordNotFoundException
	{
		String message="Peripheral was deleted successfully...";
		
		service.deletePeripheralById(id);
		
		 model.addAttribute("assetId",assetId);
		 model.addAttribute("message",message);
		 
		 return "peripheralsRedirect";
		
	}
	
	@RequestMapping(path="/createPeripheral", method=RequestMethod.POST)
	public String createOrUpdatePeripheral(Model model, PeripheralsEntity peripheral, String assetId) throws RecordNotFoundException
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ peripheral);
		
		String kluch="-";
		String message="Peripheral was added/modified successfully...";
		String description=null;
		String itemId=null;
		
		Long itemIdLong=null;
		
		itemId=peripheral.getPeripheralNum();
		
		itemIdLong=Long.valueOf(itemId);
				
		//Obtaining peripheral description
		description=serviceItems.getItemDescById(itemIdLong);
		
		peripheral.setAssetId(assetId);
		peripheral.setKluch(kluch);
		peripheral.setDescription(description);
		
		service.createOrUpdate(peripheral);
		
		model.addAttribute("assetId",assetId);
		model.addAttribute("message",message);
		
		return "peripheralsRedirect";
		
		
	}
	
	@GetMapping("/list/{id}")
	public String getPeripheralsByAssetId(Model model, @PathVariable("id") String id)
	{
		List<PeripheralsEntity> list=service.getByAssetId(id);
		
			
		model.addAttribute("peripherals",list);
		model.addAttribute("assetId",id);
		
		return "peripheralsList";
		
		
	}

}
