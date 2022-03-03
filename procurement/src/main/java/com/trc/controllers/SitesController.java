package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DivisionsService;
import com.trc.services.LogsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/sites")
public class SitesController 
{

	@Autowired
	SitesService service;
	
	@Autowired
	DivisionsService serviceDivisions;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
	//CRUD operations for sites
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllSites(Model model, Long quserId)
	{
		//List<SitesEntity> list=service.getAllSites();
		
		List<SitesEntity> list=service.getAllByName();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("sites",list);
		
		return "sitesList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editSitesById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		
		//Preparing list of divisions
		List<DivisionsEntity> divisions=serviceDivisions.getAllDivisions();
		
		if(id.isPresent())
		{
			SitesEntity entity=service.getSiteById(id.get());
			model.addAttribute("site",entity);
		}
		else
		{
			model.addAttribute("site",new SitesEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("divisions",divisions);
		
		return "sitesAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteSiteById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving site identity
        SitesEntity site=service.getSiteById(id);
				
		service.deleteSiteById(id);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Deleting Site record from the database. Site ID was "+ site.getSiteNumber());
		log.setObject(site.getSname());
		serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		message="Site was deleted...";
		
		model.addAttribute("message",message);
		
		return "sitesRedirect";
		
	}
	
	@RequestMapping(path="/createSite", method=RequestMethod.POST)
	public String createOrUpdateSite(Model model, SitesEntity site, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localSite=null;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
		
        
		if(site.getSiteid()!=null)
        {
        	//Modify the record
        	
        	service.createOrUpdate(site);
        	
        	message="Site was updated successfully...";
			
    		//Processing logs
    		LogsEntity log=new LogsEntity();
    		log.setSubject(quser.getEmail());
    		log.setAction("Modifying Site record. Item ID is "+ site.getSiteid());
    		log.setObject(site.getSname());
    		serviceLogs.saveLog(log);
        	
        }
		else
        {
        	//Creating a new record
        	
        	//Checking if this item is not already in the system
        	localSite=site.getSiteNumber();
        	priznakDuplicate=service.findDuplicates(localSite);
        	
        	if(priznakDuplicate==0)
        	{
        		service.createOrUpdate(site);
				
        		message="Site was created successfully...";
        		
		   		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new site record in the database. Site ID is "+ site.getSiteNumber());
        		log.setObject(site.getSname());
        		serviceLogs.saveLog(log);
        	}
        	else
        	{
        		message="Error: Duplicate Site was found, new record was not created at this time. Please review the list of sites again...";
				
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new site due to duplicity: "+ site.getSiteNumber());
        		log.setObject(site.getSname());
        		serviceLogs.saveLog(log);
        	}
        }	
        		
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "sitesRedirect";
		
		
	}
	
	
	@RequestMapping(path="/search", method=RequestMethod.POST)
	public String search(Model model, Long quserId)
	{
				
		List<DivisionsEntity> list=serviceDivisions.getAllDivisions();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("divisions",list);
		
		return "searchFormSites";
		
		
	}
	
	@RequestMapping(path="/searchDivision", method=RequestMethod.POST)
	public String searchByDivision(Model model,String stringSearch, Long quserId)
	{
				
		List<SitesEntity> list=service.searchByDivision(stringSearch);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("sites",list);
		model.addAttribute("stringSearch",stringSearch);
		
		return "sitesList";
		
		
	}
	
		
}
