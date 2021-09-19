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

import com.trc.entities.SettingsEntity;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SettingsService;

@Controller
@RequestMapping("/procurement/Settings")
public class SettingsController 
{

	@Autowired
	SettingsService service;
	
	//CRUD operations for Settings
	
	@GetMapping("/list")
	public String getAllSettings(Model model)
	{
		List<SettingsEntity> list=service.getAllSettings();
		
		model.addAttribute("settings",list);
		
		return "settingsList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editSettingsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
	{
		
		if(id.isPresent())
		{
			SettingsEntity entity=service.getSettingById(id.get());
			model.addAttribute("setting",entity);
		}
		else
		{
			model.addAttribute("setting",new SettingsEntity());
			
		}
		return "settingsAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteSettingById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteSettingById(id);
		
		return "redirect:/procurement/Settings/list";
		
	}
	
	@RequestMapping(path="/createSetting", method=RequestMethod.POST)
	public String createOrUpdateSetting(SettingsEntity setting)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ site);
		
		service.createOrUpdate(setting);
		
		return "redirect:/procurement/Settings/list";
		
		
	}

	
}
