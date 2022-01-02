package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DivisionsService;
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
				
		service.deleteSiteById(id);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		message="Site was deleted...";
		
		model.addAttribute("message",message);
		
		return "sitesRedirect";
		
	}
	
	@RequestMapping(path="/createSite", method=RequestMethod.POST)
	public String createOrUpdateSite(Model model, SitesEntity site, Long quserId)
	{
		String message=null;
		
		service.createOrUpdate(site);
		
		message="List was updated...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
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
