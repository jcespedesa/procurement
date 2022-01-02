package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.SettingsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SettingsService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/settings")
public class SettingsController 
{

	@Autowired
	SettingsService service;
	
	@Autowired
	UsersService serviceUsers;
	
	//CRUD operations for Settings
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllSettings(Model model, Long quserId)
	{
		List<SettingsEntity> list=service.getAllSettings();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("settings",list);
		
		return "settingsList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editSettingsById(Model model, Optional<Long> id, Long quserId) throws RecordNotFoundException 
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
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "settingsAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteSettingById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		
		String message=null;
		
		service.deleteSettingById(id);
		
		message="Setting was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "settingsRedirect";
		
	}
	
	@RequestMapping(path="/createSetting", method=RequestMethod.POST)
	public String createOrUpdateSetting(Model model, SettingsEntity setting, Long quserId)
	{
		String message=null;
		
		service.createOrUpdate(setting);
		
		message="List was updated...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "settingsRedirect";
		
		
	}

	
}
