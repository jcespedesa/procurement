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

import com.trc.entities.PhoneAddress;
import com.trc.entities.SitesEntity;
import com.trc.services.PhoneServices;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;

@Controller
@RequestMapping("procurement/phones")
public class PhonesController 
{
	
	@Autowired
	PhoneServices service;
	
	@Autowired
	SitesService siteService;
	
	//CRUD operations for phones
	
	@GetMapping("/list")
	public String getAllPhones(Model model)
	{
				
		String siteNumber=null;
		String site=null;
		
		List<PhoneAddress> list=service.getAllPhones();
		
		for(PhoneAddress phone : list)
		{
			siteNumber=phone.getSiteNumber();
			
			site=siteService.getSiteByNumber(siteNumber);
			
			phone.setSite(site);
			
		}
			
		model.addAttribute("phones",list);
		
				
		return "phonesList";
			
			
	}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editPhonesById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
		{
			
			//Preparing list of sites
			List<SitesEntity> sitesList=siteService.getAllSites();
			
			
			model.addAttribute("sites",sitesList);
			
			if(id.isPresent())
			{
				PhoneAddress entity=service.getPhoneById(id.get());
				model.addAttribute("phone",entity);
				
								
			}
			else
			{
				model.addAttribute("phone",new PhoneAddress());
				
			}
			return "phonesAddEdit";
		}
		
		@RequestMapping(path="/delete/{id}")
		public String deletePhoneById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
		{
			
			service.deletePhoneById(id);
			
			return "redirect:/procurement/phones/list";
			
		}
		
		@RequestMapping(path="/createPhone", method=RequestMethod.POST)
		public String createOrUpdatePhone(PhoneAddress phone)
		{
			//System.out.println("Inside the controller to update or create. Object is: "+ project);
			
			service.createOrUpdate(phone);
			
			return "redirect:/procurement/phones/list";
			
			
		}
		
		@RequestMapping(path="/search")
		public String search(Model model)
		{
			
			List<SitesEntity> list=siteService.getAllByName();
			List<String> statuses=service.findDistincStatuses();
			List<String> klasses=service.findDistincKlasses();
			
			model.addAttribute("sites",list);
			model.addAttribute("statuses",statuses);
			model.addAttribute("klasses",klasses);
			
			return "searchFormPhones";
			
			
		}
		
		@RequestMapping(path="/findByPhoneNum", method=RequestMethod.POST)
		public String findByPhoneNum(Model model,String stringSearch)
		{
			
			String siteNumber=null;
			String site=null;
			
			
			List<PhoneAddress> list=service.getByPhoneNum(stringSearch);
			
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			
			//System.out.println(list);
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "phonesList";
			
			
		}
		
	
		@RequestMapping(path="/findBySiteNum", method=RequestMethod.POST)
		public String findBySite(Model model,String stringSearch)
		{
			String siteNumber=null;
			String site=null;
			
			List<PhoneAddress> list=service.getBySiteNum(stringSearch);
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			//System.out.println(list);
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "phonesList";
			
			
		}
		
		@RequestMapping(path="/findByStatus", method=RequestMethod.POST)
		public String findByStatus(Model model,String stringSearch)
		{
			String siteNumber=null;
			String site=null;
			
			List<PhoneAddress> list=service.getByStatus(stringSearch);
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			//System.out.println(list);
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "phonesList";
			
			
		}
		
		@RequestMapping(path="/findByKlass", method=RequestMethod.POST)
		public String findByKlass(Model model,String stringSearch)
		{
			String siteNumber=null;
			String site=null;
			
			List<PhoneAddress> list=service.getByKlass(stringSearch);
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			//System.out.println(list);
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "phonesList";
			
			
		}
		
		@RequestMapping(path="/findBySiteKlass", method=RequestMethod.POST)
		public String findBySiteKlass(Model model,String stringSearch1,String stringSearch2)
		{
			String siteNumber=null;
			String site=null;
			String stringSearch=null;
			String siteName=null;
			
			List<PhoneAddress> list=service.getSiteNumKlass(stringSearch1,stringSearch2);
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			//converting site number to site name
			siteName=service.getSiteNameBySiteNum(stringSearch1);
			
			//Concatenating string search
			
			stringSearch=siteName+'/'+stringSearch2;
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
									
			return "phonesList";
			
			
		}
		
		@RequestMapping(path="/findBySiteStatus", method=RequestMethod.POST)
		public String findBySiteStatus(Model model,String stringSearch1,String stringSearch2)
		{
			String siteNumber=null;
			String site=null;
			String stringSearch=null;
			String siteName=null;
			
			List<PhoneAddress> list=service.getSiteNumStatus(stringSearch1,stringSearch2);
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			//converting site number to site name
			siteName=service.getSiteNameBySiteNum(stringSearch1);
			
			//Concatenating string search
			
			stringSearch=siteName+'/'+stringSearch2;
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
									
			return "phonesList";
			
			
		}
		
		@RequestMapping(path="/findByExtension", method=RequestMethod.POST)
		public String findByExtensionNum(Model model,String stringSearch)
		{
			
			String siteNumber=null;
			String site=null;
			
			
			List<PhoneAddress> list=service.getByExtension(stringSearch);
			
			
			//Retrieving site descriptions
			for(PhoneAddress phone : list)
			{
				siteNumber=phone.getSiteNumber();
				
				site=siteService.getSiteByNumber(siteNumber);
				
				phone.setSite(site);
				
			}
			
			
			//System.out.println(list);
			
			model.addAttribute("phones",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "phonesList";
			
			
		}
}
