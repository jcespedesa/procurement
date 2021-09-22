package com.trc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.AssetsEntity;
import com.trc.entities.ItemsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.services.AssetsService;
import com.trc.services.ItemsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.TitlesService;


@Controller
@RequestMapping("/procurement/Assets")
public class AssetsController 
{
	@Autowired
	AssetsService service;
	
	@Autowired
	ItemsService serviceItems;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	SitesService serviceSites;
	
	@Autowired
	TitlesService serviceTitles;
	
	//CRUD operations for assets
	
		@GetMapping("/list")
		public String getAllAssets(Model model)
		{
									
			List<AssetsEntity> list=service.getAllAssets();
						
			model.addAttribute("assets",list);
						
			return "assetsList";
			
			
		}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editAssetsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
		{
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllItems();
			
			//Preparing list of sites
			List<SitesEntity> sites=serviceSites.getAllHHSsites();
			
			//Preparing list of projects actives by udelny bes
			List<ProjectsEntity> projects=serviceProjects.getAllHHSbyUB();
			
			//Preparing list of titles
			List<TitlesEntity> titles=serviceTitles.getAllTitles();
			
			String site=null;
			String project=null;
						
			if(id.isPresent())
			{
				AssetsEntity entity=service.getAssetById(id.get());
				
				//Finding names or descriptions
				
				site=serviceSites.getSiteByNumber(entity.getSite());
				project=serviceProjects.getSiteBySiteNumber1(entity.getProject());
				
				model.addAttribute("asset",entity);
				
				model.addAttribute("siteName",site);
				model.addAttribute("projectName",project);
			}
			else
			{
				String todayDate=null;
				
				LocalDateTime now=LocalDateTime.now(); 
				
				//Generating today's date
				DateTimeFormatter dtfToday=DateTimeFormatter.ofPattern("MM/dd/yyyy");  
				todayDate=dtfToday.format(now);
				
				model.addAttribute("asset",new AssetsEntity());
				
				//Generating a random value for record identification
				Random r=new Random();
				int seed=r.nextInt();
				
				model.addAttribute("seed",seed);
				model.addAttribute("kluch",todayDate);
				
			}
			
			model.addAttribute("items",items);
			model.addAttribute("sites",sites);
			model.addAttribute("projects",projects);
			model.addAttribute("titles",titles);
			
			return "assetsAddEdit";
		}
		
		@RequestMapping(path="/delete/{id}")
		public String deleteAssetById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
		{
			
			service.deleteAssetById(id);
			
			return "redirect:/procurement/Assets/list";
			
		}
		
		@RequestMapping(path="/createAsset", method=RequestMethod.POST)
		public String createOrUpdateAsset(AssetsEntity asset)
		{
			//System.out.println("Inside the controller to update or create. Object is: "+ asset);
			
			service.createOrUpdate(asset);
			
			return "redirect:/procurement/Assets/list";

		}
			
}
