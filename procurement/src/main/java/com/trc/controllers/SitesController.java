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
import com.trc.entities.SitesEntity;
import com.trc.services.DivisionsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;

@Controller
@RequestMapping("/procurement/Sites")
public class SitesController 
{

	@Autowired
	SitesService service;
	
	@Autowired
	DivisionsService serviceDivisions;
	
	//CRUD operations for sites
	
	@GetMapping("/list")
	public String getAllSites(Model model)
	{
		//List<SitesEntity> list=service.getAllSites();
		
		List<SitesEntity> list=service.getAllByName();
		
		model.addAttribute("sites",list);
		
		return "sitesList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editSitesById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
		
		model.addAttribute("divisions",divisions);
		
		return "sitesAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteSiteById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteSiteById(id);
		
		return "redirect:/procurement/Sites/list";
		
	}
	
	@RequestMapping(path="/createSite", method=RequestMethod.POST)
	public String createOrUpdateSite(SitesEntity site)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ site);
		
		service.createOrUpdate(site);
		
		return "redirect:/procurement/Sites/list";
		
		
	}
	
	
	@RequestMapping(path="/search")
	public String search(Model model)
	{
				
		List<DivisionsEntity> list=serviceDivisions.getAllDivisions();
		
		model.addAttribute("divisions",list);
		
		return "searchFormSites";
		
		
	}
	
	@RequestMapping(path="/searchDivision", method=RequestMethod.POST)
	public String searchByDivision(Model model,String stringSearch)
	{
				
		List<SitesEntity> list=service.searchByDivision(stringSearch);
		
		//System.out.println(list);
		
		model.addAttribute("sites",list);
		model.addAttribute("stringSearch",stringSearch);
		
		return "sitesList";
		
		
	}
	
	
}
