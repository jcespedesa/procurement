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

import com.trc.entities.ProvidersEntity;
import com.trc.entities.SitesEntity;
import com.trc.services.ProvidersService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;



@Controller
@RequestMapping("/procurement/Providers")
public class ProvidersController 
{
	@Autowired
	ProvidersService service;
	
	@Autowired
	SitesService serviceSites;
	
	//CRUD operations for providers
	
		@GetMapping("/list")
		public String getAllProviders(Model model)
		{
			//List<ProvidersEntity> list=service.getAllProviders();
			
			List<ProvidersEntity> list=service.getAllProvidersListByName();
			
			model.addAttribute("providers",list);
			
			return "providersList";
			
			
		}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editProvidersById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
		{
			List<SitesEntity> list=serviceSites.getAllByName();
			
			String site="";
			
			if(id.isPresent())
			{
				ProvidersEntity entity=service.getProviderById(id.get());
				model.addAttribute("provider",entity);
				
				site=service.getSiteBySiteNumber(entity.getSiteNum());
				model.addAttribute("site1",site);
				
				
			}
			else
			{
				model.addAttribute("provider",new ProvidersEntity());
				
			}
			
			model.addAttribute("sites",list);
			
			return "providersAddEdit";
		}
		
		@RequestMapping(path="/delete/{id}")
		public String deleteProviderById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
		{
			
			service.deleteProviderById(id);
			
			return "redirect:/procurement/Providers/list";
			
		}
		
		@RequestMapping(path="/createProvider", method=RequestMethod.POST)
		public String createOrUpdateProvider(ProvidersEntity provider)
		{
			//System.out.println("Inside the controller to update or create. Object is: "+ provider);
			
			String site=null;
			Long id=null;
			
			site=service.getSiteBySiteNumber(provider.getSiteNum());
			id=provider.getProviderid();
			
			service.createOrUpdate(provider);
			service.updateSite(site,id);
			
			return "redirect:/procurement/Providers/list";
			
			
		}
		
}
