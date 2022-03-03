package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.LogsEntity;
import com.trc.entities.SettingsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
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
	
	@Autowired
	LogsService serviceLogs;
	
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
		
		//Retrieving setting identity
        SettingsEntity setting=service.getSettingById(id);
		
		service.deleteSettingById(id);
		
		message="Setting was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Deleting Setting definition from the database. Setting was ID:  "+ setting.getSettingid());
		log.setObject(setting.getSname());
		serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "settingsRedirect";
		
	}
	
	@RequestMapping(path="/createSetting", method=RequestMethod.POST)
	public String createOrUpdateSetting(Model model, SettingsEntity setting, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localSetting=null;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
		
        if(setting.getSettingid()!=null)
        {
        	//Modify the record
		
        	service.createOrUpdate(setting);
		
        	message="Setting information was updated successfully...";
		
        	//Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Modifying a Setting in the system. Setting ID is "+ setting.getSettingid());
			log.setObject(setting.getSname());
			serviceLogs.saveLog(log);
        }
        else
        {
        	//Creating a new record
        	
        	//Checking if this item is not already in the system
        	localSetting=setting.getSname();
        	priznakDuplicate=service.findDuplicates(localSetting);
		
        	if(priznakDuplicate==0)
        	{
        
        		service.createOrUpdate(setting);
			
        		message="New setting was created successfully...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new setting in the system. Setting ID is "+ setting.getSettingid());
        		log.setObject(setting.getSname());
        		serviceLogs.saveLog(log);
		
        	}
        	else
        	{
        		message="Error: Setting duplicated found, new record was not created at this time. Please review the list of settings again...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new setting due to duplicity");
        		log.setObject(setting.getSname());
        		serviceLogs.saveLog(log);
			
        	}	
        
        }	
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "settingsRedirect";
		
		
	}

	
}
