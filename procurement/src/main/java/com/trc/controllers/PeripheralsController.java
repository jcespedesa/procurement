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

import com.trc.entities.AssetsEntity;
import com.trc.entities.ItemsEntity;
import com.trc.entities.PeripheralsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.services.AssetsService;
import com.trc.services.ItemsService;
import com.trc.services.PeripheralsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.TitlesService;


@Controller
@RequestMapping("/procurement/peripherals")
public class PeripheralsController 
{
	@Autowired
	PeripheralsService service;
	
	@Autowired
	ItemsService serviceItems;
	
	@Autowired
	AssetsService serviceAssets;
	
	@Autowired
	TitlesService serviceTitles;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	SitesService serviceSites;
	
	//CRUD operations for peripherals
	
	@GetMapping("/list")
	public String getAllPeripherals(Model model) throws RecordNotFoundException
	{
		List<PeripheralsEntity> list=service.getAllPeripherals();
		
		String description=null;
		
		//Obtaining descriptions
		for(PeripheralsEntity periph : list)
		{
			description=serviceItems.getItemByNumber(periph.getPeripheralNum());
			periph.setDescription(description);
			
			System.out.println(description);
		}
			
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
		String itemNumber=null;
		
				
		itemNumber=peripheral.getPeripheralNum();
		
						
		//Obtaining peripheral description
		description=serviceItems.getItemByNumber(itemNumber);
		
		peripheral.setAssetId(assetId);
		peripheral.setKluch(kluch);
		peripheral.setDescription(description);
		
		service.createOrUpdate(peripheral);
		
		model.addAttribute("assetId",assetId);
		model.addAttribute("message",message);
		
		return "peripheralsRedirect";
		
		
	}
	
	@GetMapping("/list/{id}")
	public String getPeripheralsByAssetId(Model model, @PathVariable("id") String id) throws RecordNotFoundException
	{
		List<PeripheralsEntity> list=service.getByAssetId(id);
		
		String description=null;
		
		//Obtaining descriptions
		for(PeripheralsEntity periph : list)
		{
			description=serviceItems.getItemByNumber(periph.getPeripheralNum());
			periph.setDescription(description);
				
		}
			
		model.addAttribute("peripherals",list);
		model.addAttribute("assetId",id);
		
		return "peripheralsList";
		
		
	}
	
	@GetMapping("/promote")
	public String promotePeriphToAsset(Model model, String assetId, String itemId) throws RecordNotFoundException
	{
		Long assetIdLong=null;
		Long itemIdLong=null;
		
		String priznakNew="Yes";
		
		String titleName=null;
		String projectName=null;
		String itemName=null;
		
		String peripheralName=null;
		
		//Preparing list of items
		List<ItemsEntity> items=serviceItems.getAllMainItems();
		
		//Preparing list of sites
		List<SitesEntity> sites=serviceSites.getAllHHSsites();
		
		//Preparing list of active projects by udelny bes
		List<ProjectsEntity> projects=serviceProjects.getAllHHSbyUB();
		
		//Preparing list of titles
		List<TitlesEntity> titles=serviceTitles.getAllTitles();
		
		
		assetIdLong=Long.parseLong(assetId);
		itemIdLong=Long.parseLong(itemId);
		
		//System.out.println("Parent Asset ID is "+ assetId);
		//System.out.println("Peripheral ID is "+ itemId);
		
		AssetsEntity oldAsset=serviceAssets.getAssetById(assetIdLong);
		PeripheralsEntity periph=service.getPeripheralById(itemIdLong);	
		
		//System.out.println("Asset is "+ oldAsset);
		//System.out.println("Peripheral is "+ periph);
		
		titleName=serviceTitles.getTitleByNumber(oldAsset.getTitle());
		projectName=serviceProjects.getProjectByNum(oldAsset.getProject());
		itemName=serviceItems.getItemByNumber(oldAsset.getItem());
		
		peripheralName=serviceItems.getItemByNumber(periph.getPeripheralNum());
		
		model.addAttribute("titleName",titleName);
		model.addAttribute("projectName",projectName);
		model.addAttribute("itemName",itemName);
		
		model.addAttribute("periphName",peripheralName);
		
		model.addAttribute("asset",new AssetsEntity());
		
		model.addAttribute("periph",periph);
		model.addAttribute("oldAsset",oldAsset);
		model.addAttribute("priznakNew",priznakNew);
		
		model.addAttribute("sites",sites);
		model.addAttribute("projects",projects);
		model.addAttribute("titles",titles);
		model.addAttribute("items",items);
		
		return "peripheralsPromo";
		
		
	}
	
	@RequestMapping(path="/promoteAsset", method=RequestMethod.POST)
	public String promoteAsset(Model model, AssetsEntity asset)
	{
	
		serviceAssets.createAsset(asset);
		
		return "assetsList";
	}

}
