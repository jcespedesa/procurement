package com.trc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping("/portal/assets")
public class PortalController 
{
	@Autowired
	AssetsService service;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	SitesService serviceSites;
	
	@Autowired
	TitlesService serviceTitles;
	
	@Autowired
	ItemsService serviceItems;
	
	

	@RequestMapping(path={"/menu"})
	public String portalAssets(Model model,String kluch)
	{
		String dateRecord=null;
		
		//System.out.println("Arrived value of kluch in portal menu is "+ kluch);
		
		//If the session is new, a new kluch will be created
		if(kluch==null)
		{
			LocalDateTime now=LocalDateTime.now(); 
			
			//Generating today's date
			DateTimeFormatter dtfRecord=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
			dateRecord=dtfRecord.format(now);
			
			//Generating a random value for record identification
			Random r=new Random();
			int seed=r.nextInt();
			
			//Converting seed in always a positive number
			if(seed<0)
				seed=-seed;
			
			kluch=dateRecord +"-"+ seed;
			
		}
		else
		{
			//Preparing list of items already input in this session
			List<AssetsEntity> assets=service.findThisSessionAssets(kluch);
			
			model.addAttribute("assets",assets);
		}
		
		model.addAttribute("kluch",kluch);
		
		
		return "assetsPortalMenu";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{kluch}"})
	public String portalAssetsAddEdit(Model model,@PathVariable("kluch") String kluch) throws RecordNotFoundException 
	{
		String todayDate=null;
		
		
		LocalDateTime now=LocalDateTime.now(); 
		
		//Generating today's date
		DateTimeFormatter dtfToday=DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		todayDate=dtfToday.format(now);
		
				
		//System.out.println("Today Date is "+ todayDate); 
		//System.out.println("Record ID is "+ kluch); 
		
		
		//Preparing list of items
		List<ItemsEntity> items=serviceItems.getAllItems();
		
		//Preparing list of sites
		List<SitesEntity> sites=serviceSites.getAllHHSsites();
		
		//Preparing list of projects
		List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
		
		//Preparing list of titles
		List<TitlesEntity> titles=serviceTitles.getAllTitles();
		
		model.addAttribute("asset",new AssetsEntity());
		
		model.addAttribute("items",items);
		model.addAttribute("sites",sites);
		model.addAttribute("projects",projects);
		model.addAttribute("titles",titles);
		
		model.addAttribute("kluch",kluch);
		model.addAttribute("todayDate",todayDate);
		
		return "assetsPortalAddEdit";
		
		
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteAssetById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteAssetById(id);
		
		return "redirect:/portal/assets/menu";
		
	}
	
	@RequestMapping(path="/createAsset", method=RequestMethod.POST)
	public String createOrUpdateAsset(Model model,AssetsEntity asset,RedirectAttributes redirectAttributes)
	{
		String kluch=null;
		
		
		//System.out.println("Inside the controller to update or create. Object is: "+ asset);
		
		service.createOrUpdate(asset);
		
		kluch=asset.getKluch();
		
		//System.out.println("Kluch value sent to portal menu is: "+ kluch);
		
		//model.addAttribute("kluch",kluch);
		//model.addAttribute("assets",assets);
		
		redirectAttributes.addAttribute("kluch",kluch);
					
		return "redirect:/portal/assets/menu";
		

	}
	
	@RequestMapping(path={"/form1"})
	public String portalForm1(Model model)
	{
		String kluch=null;
		String todayDate=null;
		String kluchDate=null;
		
		LocalDateTime now=LocalDateTime.now(); 
		
		//Generating a random value for record identification
		Random r=new Random();
		int seed=r.nextInt();
		
		//Converting seed in always a positive number
		if(seed<0)
			seed=-seed;
		
		//Generating date for the key
		DateTimeFormatter dtfRecord=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		kluchDate=dtfRecord.format(now);
		
		//Generating today's date
		DateTimeFormatter dtfToday=DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		todayDate=dtfToday.format(now);
		
		//Creating unique identifier for this session
		kluch=kluchDate +"-"+ seed;
		
		//Preparing list of projects according to their udelny bes
		List<ProjectsEntity> projects=serviceProjects.getAllHHSbyUB();
		
		//Preparing list of titles
		List<TitlesEntity> titles=serviceTitles.getAllTitles();
				
		model.addAttribute("projects",projects);
		model.addAttribute("titles",titles);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("kluch",kluch);
		
		return "assetsPortalForm1";
		
		
	}
	
	@RequestMapping(path="/form2", method=RequestMethod.POST)
	public String portalForm2(Model model,RedirectAttributes redirectAttributes,
			String firstName,
			String lastName,
			String email,
			String empStatus,
			String title,
			String project,
			String kluch,
			String todayDate,
			String active)
	{
		String cname=null;
		String repitaya=",";
		String pustoy=" ";
		String program=" ";
		
		//Preparing list of items
		List<ItemsEntity> items=serviceItems.getAllMainItems();
		
		cname=lastName+repitaya+pustoy+firstName;
		
		//System.out.println("Captured name is "+ cname);
		
		//Retrieving program name
		program=serviceProjects.getProjectByNum(project);
				
		model.addAttribute("cname",cname);
		model.addAttribute("email",email);
		model.addAttribute("empStatus",empStatus);
		model.addAttribute("title",title);
		
		model.addAttribute("project",project);
		model.addAttribute("program",program);
		model.addAttribute("kluch",kluch);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("active",active);
		
		model.addAttribute("items",items);
		
		//return "redirect:/portal/assets/form2";
		
		return "assetsPortalForm3A";

	}
	
	@RequestMapping(path="/form4", method=RequestMethod.POST)
	public String portalForm3(Model model,RedirectAttributes redirectAttributes,
			
			String cname,
			String email,
			String empStatus,
			String title,
			String program,
			String project,
			String kluch,
			String todayDate,
			String active,
			String klass)
	{
		
		
		model.addAttribute("cname",cname);
		model.addAttribute("email",email);
		model.addAttribute("empStatus",empStatus);
		model.addAttribute("title",title);
		model.addAttribute("program",program);
		model.addAttribute("project",project);
		
		model.addAttribute("kluch",kluch);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("active",active);
		
				
		//Preparing list of peripheral items
		List<ItemsEntity> items=serviceItems.getAllPeripheralsHHS();
		
		model.addAttribute("items",items);		
									
			
		return "assetsPortalForm4A";

	}
	
	@RequestMapping(path="/form3", method=RequestMethod.POST)
	public String portalForm4(Model model,RedirectAttributes redirectAttributes,
			
			String cname,
			String email,
			String empStatus,
			String title,
			String program,
			String project,
			String kluch,
			String todayDate,
			String active,
			String klass,
			String item,
			String assetNumber)
			
	{
		
		
		model.addAttribute("cname",cname);
		model.addAttribute("email",email);
		model.addAttribute("empStatus",empStatus);
		model.addAttribute("title",title);
		model.addAttribute("program",program);
		model.addAttribute("project",project);
		
		model.addAttribute("kluch",kluch);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("active",active);
		
		model.addAttribute("klass",klass);
		model.addAttribute("item",item);
		model.addAttribute("assetNumber",assetNumber);
		
		return "assetsPortalForm4";
		
	}	
	
	@RequestMapping(path="/form5", method=RequestMethod.POST)
	public String portalForm5(Model model,RedirectAttributes redirectAttributes,
			
			String cname,
			String email,
			String empStatus,
			String title,
			String program,
			String project,
			String kluch,
			String todayDate,
			String active,
			String klass,
			String item,
			String assetNumber,
			String priznak)
	{
		
		
		model.addAttribute("cname",cname);
		model.addAttribute("email",email);
		model.addAttribute("empStatus",empStatus);
		model.addAttribute("title",title);
		model.addAttribute("program",program);
		model.addAttribute("project",project);
		
		model.addAttribute("kluch",kluch);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("active",active);
		
		model.addAttribute("klass",klass);
		model.addAttribute("item",item);
		model.addAttribute("assetNumber",assetNumber);
		
		//Save asset information here
		
		
		//Retrieve the asset ID
		
		
		if(priznak.equals("Yes"))
		{	
			
			//Send the assetId
			
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllPeripheralsHHS();
			
			model.addAttribute("items",items);
			
			return "assetsPortalForm4A";
			
		}
		else
		{
			
			return "assetsPortalForm5";
			
		}
		
	}
	
	
	
	@RequestMapping(path="/form8", method=RequestMethod.POST)
	public String portalForm6()
	{
		
		return "assetsPortalForm5";
		
	}
	
	@RequestMapping(path="/form4B", method=RequestMethod.POST)
	public String portalForm4B(Model model, String assetId, String asset, String priznak)
	{
		
		//Save peripheral information
		
		model.addAttribute("assetId",assetId);
		model.addAttribute("asset",asset);
		
					
		return "assetsPortalForm4B";
			
		
		
				
	}
	
	@RequestMapping(path="/form6", method=RequestMethod.POST)
	public String portalForm7(Model model, String assetId, String asset, String priznak)
	{
		
		if(priznak.equals("Yes"))
		{	
			
			//Send the assetId
			
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllPeripheralsHHS();
			
			model.addAttribute("items",items);
			
			
			return "assetsPortalForm4A";
			
		}
		else
		{
			
			return "assetsPortalForm4C";
			
		}
		
		
		
		
				
	}
	
	
	@RequestMapping(path="/form3B", method=RequestMethod.POST)
	public String portalForm3B(Model model,RedirectAttributes redirectAttributes,
			
			String cname,
			String email,
			String empStatus,
			String title,
			String program,
			String project,
			String kluch,
			String todayDate,
			String active,
			String klass,
			String assetNumber)
	{
		
		
		model.addAttribute("cname",cname);
		model.addAttribute("email",email);
		model.addAttribute("empStatus",empStatus);
		model.addAttribute("title",title);
		model.addAttribute("program",program);
		model.addAttribute("project",project);
		
		model.addAttribute("kluch",kluch);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("active",active);
		
		model.addAttribute("klass",klass);
		model.addAttribute("assetNumber",assetNumber);
		
		
		//Save asset
		
		return "assetsPortalForm5";

	}
	
	@RequestMapping(path="/form9", method=RequestMethod.POST)
	public String portalForm6a()
	{
		
		return "assetsPortalForm6";
		
	}
	
	@RequestMapping(path="/form7", method=RequestMethod.POST)
	public String portalForm7()
	{
		
		return "assetsPortalForm3B";
		
	}
	
}
