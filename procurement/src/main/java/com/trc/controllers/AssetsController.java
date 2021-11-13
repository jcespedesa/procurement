package com.trc.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.trc.entities.AssetAssigEntity;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ItemsEntity;
import com.trc.entities.PeripheralsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.services.AssetsAssigService;
import com.trc.services.AssetsService;
import com.trc.services.ItemsService;
import com.trc.services.PeripheralsService;
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
	
	@Autowired
	PeripheralsService servicePeripherals;
	
	@Autowired
	AssetsAssigService serviceReassig;
	
	//CRUD operations for assets
	
		@RequestMapping(path="/menu")
		public String menuAssets(Model model)
		{
						
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
						
			//Preparing list of authors email
			List<String> emails=service.getAuthorEmails();
			
			//Preparing list of assignees
			List<String> assigEmails=service.getAssigneeEmails();
			
			
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			model.addAttribute("assigEmails",assigEmails);
			
			return "assetsMenu";
			
			
		}
		
		
	
		
		@RequestMapping(path="/list",method=RequestMethod.POST)
		public String getAllAssets(Model model,String stringSearch,String priznak) throws RecordNotFoundException
		{
			List<AssetsEntity> list=service.getAllAssets();
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
					
			Long assetIdLong=null;
									
			
			for(AssetsEntity asset : list)
			{
				priznakPeripherals=0;
			
				//finding project name
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				//finding item description
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
							
			}
															
			model.addAttribute("assets",list);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsList";
			
			
		}
		
		@RequestMapping(path={"/new/{stringSearch}"})
		public String newAsset(Model model,@PathVariable("stringSearch") String stringSearch)
		{
			
								
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of sites
			List<SitesEntity> sites=serviceSites.getAllHHSsites();
			
			//Preparing list of active projects by udelny bes
			List<ProjectsEntity> projects=serviceProjects.getAllHHSbyUB();
			
			//Preparing list of titles
			List<TitlesEntity> titles=serviceTitles.getAllTitles();
						
			
			String priznakNew=null;
					
			
			String todayDate=null;
				
			LocalDateTime now=LocalDateTime.now(); 
				
			priznakNew="Yes";
				
			//Generating today's date
			DateTimeFormatter dtfToday=DateTimeFormatter.ofPattern("MM/dd/yyyy");  
			todayDate=dtfToday.format(now);
				
			//Generating the object
			model.addAttribute("asset",new AssetsEntity());
				
			//Generating a random value for record identification
			Random r=new Random();
			int seed=r.nextInt();
				
			model.addAttribute("seed",seed);
			model.addAttribute("kluch",todayDate);
						
			model.addAttribute("items",items);
			model.addAttribute("sites",sites);
			model.addAttribute("projects",projects);
			model.addAttribute("titles",titles);
			
			model.addAttribute("priznakNew",priznakNew);
			model.addAttribute("stringSearch",stringSearch);
			
			return "assetsAddEdit";
		}

		
		
		@RequestMapping(path={"/edit"},method=RequestMethod.POST)
		public String editAssetsById(Model model,Long id,String stringSearch,String priznak) throws RecordNotFoundException 
		{
			
								
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of sites
			List<SitesEntity> sites=serviceSites.getAllHHSsites();
			
			//Preparing list of active projects by udelny bes
			List<ProjectsEntity> projects=serviceProjects.getAllHHSbyUB();
			
			//Preparing list of titles
			List<TitlesEntity> titles=serviceTitles.getAllTitles();
						
			
			String site=null;
			String project=null;
						
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String priznakNew=null;
											
			AssetsEntity entity=service.getAssetById(id);
				
			String assetId=null;
			
						
			assetId=String.valueOf(id);
				
			//Retrieving related peripherals for this asset				
			List<PeripheralsEntity> peripherals=servicePeripherals.getByAssetId(assetId);
				
			//Preparing history of re-assignations 
			List<AssetAssigEntity> assigs=serviceReassig.getAssigById(assetId);
				
								
			itemNumber=entity.getItem();
			titleNumber=entity.getTitle();
												
			priznakNew="No";
								
			//Finding names or descriptions
				
			site=serviceSites.getSiteByNumber(entity.getSite());
			project=serviceProjects.getSiteBySiteNumber1(entity.getProject());
			itemName=serviceItems.getItemByNumber(itemNumber);
			titleName=serviceTitles.getTitleByNumber(titleNumber);
				
			model.addAttribute("asset",entity);
				
			model.addAttribute("siteName",site);
			model.addAttribute("projectName",project);
			model.addAttribute("itemName",itemName);
			model.addAttribute("titleName",titleName);
			
			model.addAttribute("peripherals",peripherals);
			model.addAttribute("assigs",assigs);
			
			
			model.addAttribute("items",items);
			model.addAttribute("sites",sites);
			model.addAttribute("projects",projects);
			model.addAttribute("titles",titles);
			
			model.addAttribute("priznakNew",priznakNew);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsAddEdit";
		}
		
		@RequestMapping(path="/delete", method=RequestMethod.POST)
		public String deleteAssetById(Model model,Long id,String stringSearch,String priznak) throws RecordNotFoundException
		{
			String message="Record was deleted...";
			
			//Deleting the record
			service.deleteAssetById(id);
			
			model.addAttribute("message",message);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsRedirect";
			
		}
		
		@RequestMapping(path="/createAsset", method=RequestMethod.POST)
		public String createOrUpdateAsset(Model model,AssetsEntity asset,String stringSearch,String priznak)
		{
			//System.out.println("Inside the controller, arrived string search was: "+ stringSearch);
			
			String message="Asset was updated successfully...";
			
						
			service.createOrUpdate(asset);
			
			model.addAttribute("message",message);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsRedirect";

		}
		
		
		
		@RequestMapping(path="/search")
		public String search(Model model)
		{
						
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
						
			//Preparing list of authors email
			List<String> emails=service.getAuthorEmails();
			
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			
			return "searchFormAssets";
			
			
		}
		
		@RequestMapping(path="/findAssetByNum", method=RequestMethod.POST)
		public String findByAssetNum(Model model,String stringSearch,String priznak) throws RecordNotFoundException
		{
			
						
			List<AssetsEntity> list=service.getByAssetNum(stringSearch);
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String assetId=null;
			
			String titleName=null;
			String titleNumber=null;
			
			Long assetIdLong=null;
						
			
			for(AssetsEntity asset : list)
			{
				priznakPeripherals=0;
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
									
			return "assetsList";
			
		}
		
		@RequestMapping(path="/findAssetsByItem", method=RequestMethod.POST)
		public String findByKlass(Model model,String stringSearch,String priznak) throws RecordNotFoundException
		{
			
						
			List<AssetsEntity> list=service.getByItem(stringSearch);
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;
						
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		@RequestMapping(path="/findAssetsByAuthor", method=RequestMethod.POST)
		public String findByAuthor(Model model,String stringSearch,String priznak) throws RecordNotFoundException
		{
									
			List<AssetsEntity> list=service.getByAuthor(stringSearch);
			
			int priznakPeripherals=0;
						
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;					
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		
		@RequestMapping(path="/findAssetsByProgram", method=RequestMethod.POST)
		public String findByProgram(Model model,String stringSearch,String priznak) throws RecordNotFoundException
		{
									
			List<AssetsEntity> list=service.getByProgram(stringSearch);
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;					
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		
		@RequestMapping(path="/findAssetsByAssignee", method=RequestMethod.POST)
		public String findByAssignee(Model model,String stringSearch,String priznak) throws RecordNotFoundException
		{
									
			List<AssetsEntity> list=service.getByAssignee(stringSearch);
			
			int priznakPeripherals=0;
						
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;					
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		
		
		@RequestMapping(path="/views")
		public String views(Model model)
		{
						
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
						
			//Preparing list of authors email
			List<String> emails=service.getAuthorEmails();
			
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			
			return "viewsMenuAssets";
			
			
		}
		
		@RequestMapping(path="/viewAssetsByProgram", method=RequestMethod.POST)
		public String viewByProgram(Model model,String stringSearch) throws RecordNotFoundException
		{
			//Retrieving assets list						
			List<AssetsEntity> list=service.getByProgram(stringSearch);
			
			//Retrieving peripherals list 
			List<PeripheralsEntity> listPeripherals=servicePeripherals.getByProject(stringSearch);
			
			int age=0;
			
			String assetId=null;
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String titleNumber=null;
			String titleName=null;
			String viewTitle=null;
			String header="Assets View by Program : ";
			String username=null;
			String datePurchased=null;
			
			String periphNumber=null;
			String periphName=null;
			
			String ageString=null;
															
			Long assetIdLong=null;
			
			//Testing the age generator
			//String datePurchased="2010-04-23";
			//age=service.priznakOldItem(datePurchased);
			
			//System.out.println("The asset is "+ age +" years old ");
			
			for(AssetsEntity asset : list)
			{
				age=0;
			
				//Finding project name
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				//Finding item name
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding title name
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
								
				//Checking age of the asset
				datePurchased=asset.getRealDatePurchased();
				
				if((datePurchased==null)||(datePurchased.equals("1900-01-01 00:00:00")))
					age=0;
				else
					age=service.priznakOldItem(datePurchased);
							
								
				if((age>5)&&(age<100))
					ageString="Old";
				else
					ageString="New";
							
				//System.out.println("Age is "+ age +" and ageString is "+ ageString);
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setAge(ageString);
			}
			
			for(PeripheralsEntity peripheral : listPeripherals)
			{
				assetId=peripheral.getAssetId();
				assetIdLong=Long.parseLong(assetId);
				username=service.getUsername(assetIdLong);
				
				//Finding item name
				periphNumber=peripheral.getPeripheralNum();
				periphName=serviceItems.getItemByNumber(periphNumber);
												
				//System.out.println("Periph was found with number "+ periphNumber +" and was identify as "+ periphName);
				
				//Checking age of the peripheral
				datePurchased=peripheral.getRealDatePurchased();
				
				if((datePurchased==null)||(datePurchased.equals("1900-01-01 00:00:00")))
					age=0;
				else
					age=service.priznakOldItem(datePurchased);
							
								
				if((age>5)&&(age<100))
					ageString="Old";
				else
					ageString="New";
				
				peripheral.setAge(ageString);
				
				peripheral.setNotes(username);
				peripheral.setDescription(periphName);
				
			}
			
			viewTitle=header + stringSearch;
			
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("viewTitle",viewTitle);	
			
			model.addAttribute("assets",list);
			model.addAttribute("peripherals",listPeripherals);
						
			return "assetsView";
			
		}
		
		
		@GetMapping("/exportCSV/{stringSearch}")
	    public void exportToCSV(HttpServletResponse response,@PathVariable("stringSearch") String stringSearch) throws IOException, RecordNotFoundException 
		{
	        response.setContentType("text/csv");
	        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String currentDateTime=dateFormatter.format(new Date());
	         
	        String headerKey="Content-Disposition";
	        //String headerValue="attachment; filename=assets_" + currentDateTime + ".csv";
	        
	        String headerValue="attachment; filename="+ stringSearch +"-"+ currentDateTime +".csv";
	        response.setHeader(headerKey,headerValue);
	       	            
	       //Retrieving assets list						
			List<AssetsEntity> list=service.getByProgram(stringSearch);
			
			//Retrieving peripherals list 
			List<PeripheralsEntity> listPeripherals=servicePeripherals.getByProject(stringSearch);
			
			String assetId=null;
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String titleNumber=null;
			String titleName=null;
			String dateConverted=null;
			String dateRaw=null;
			String year=null;
			String month=null;
			String day=null;
			String podcherk="/";
			String username=null;
			
			String periphNumber=null;
			String periphName=null;
			
			Long assetIdLong=null;
						
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				
				
				dateRaw=asset.getDateCreation();
				
				//Converting raw date into string mm/dd/yy
				year=dateRaw.substring(0,dateRaw.indexOf("-"));
				month=dateRaw.substring(5,7);
				day=dateRaw.substring(8,10);
				
				dateConverted=month+podcherk+day+podcherk+year;
				
				//System.out.println("Year is "+ year);
				//System.out.println("Month is "+ month);
				//System.out.println("Day is "+ day);
								
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setDateCreation(dateConverted);	
				
			}
			
			for(PeripheralsEntity peripheral : listPeripherals)
			{
				assetId=peripheral.getAssetId();
				assetIdLong=Long.parseLong(assetId);
				username=service.getUsername(assetIdLong);
				
				//Finding item name
				periphNumber=peripheral.getPeripheralNum();
				periphName=serviceItems.getItemByNumber(periphNumber);
				
				peripheral.setNotes(username);
				peripheral.setDescription(periphName);
				
			}
			
	 
	        ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	        	        
	        String[] csvHeader={"Item","Asset Number","Maker", "Model", "Date Purchased","User","Title","Emp. Status","Project","Program","Date Inventory","Registered by","Email","Item Class"};
	        String[] nameMapping={"site","assetNumber","maker","model","datePurchased","username","title","empStatus","project","program","dateCreation","author","authorEmail","klass"};
	        
	        String[] csvHeader2={"Description","Item Related","User"};
	        String[] nameMapping2={"description","itemId","notes"};
	        
	        	         
	        csvWriter.writeHeader(csvHeader);
	         
	        for (AssetsEntity asset : list) 
	        {
	            csvWriter.write(asset, nameMapping);
	        }
	        
	        	        	        
	        
	        csvWriter.writeHeader(csvHeader2);
	        
	        for (PeripheralsEntity peripheral : listPeripherals) 
	        {
	            csvWriter.write(peripheral, nameMapping2);
	        }
	         
	        csvWriter.close();
	         
	    }
		
				
		@RequestMapping(path="/reassign", method=RequestMethod.POST)
		public String reassign(Model model,Long assetId, String stringSearch, String priznak) throws RecordNotFoundException
		{
			String itemName=null;
			String itemNumber=null;
			String titleName=null;
			String titleNumber=null;
			String projectName=null;
			String projectNumber=null;
			
			//Retrieving asset entity
			AssetsEntity entity=service.getAssetById(assetId);
			
			itemNumber=entity.getItem();
			titleNumber=entity.getTitle();
			projectNumber=entity.getProject();
			
			//Retrieving item description
			itemName=serviceItems.getItemByNumber(itemNumber);
			
			//Retrieving title description
			titleName=serviceTitles.getTitleByNumber(titleNumber);
			
			//Retrieving project description
			projectName=serviceProjects.getProjectByNum(projectNumber);
			
			//Preparing list of titles
			List<TitlesEntity> titles=serviceTitles.getAllTitles();
						
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
			
			//System.out.println("title number is "+ titleNumber);
			//System.out.println("title is "+ titleName);
			//System.out.println("notes are "+ entity.getNotes());
			
			model.addAttribute("assetReassig",new AssetAssigEntity());
						
			model.addAttribute("asset",entity);
			model.addAttribute("projects",projects);
			model.addAttribute("titles",titles);
			
			model.addAttribute("itemName",itemName);
			model.addAttribute("titleName",titleName);
			model.addAttribute("projectName",projectName);
			
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("priznak",priznak);
			
			return "assetsReassigForm";
			
		}
		
		@RequestMapping(path="/reassignAsset", method=RequestMethod.POST)
		public String reassignAsset(Model model,Long id, AssetAssigEntity assetReassig, String stringSearch, String priznak,
				
				String username,
				String email,
				String empStatus,
				String title,
				String project,
				
				String assetNumber,
				String kluch
				
		) throws RecordNotFoundException
		{
									
			String assetIdString=null;
			String todayDate=null;
			
			String newUsername=null;
			String newTitle=null;
			String newEmpStatus=null;
			String newProject=null;
			String newEmail=null;
			String reassignedBy=null;
			String emailReassigner=null;
			
			newUsername=assetReassig.getNewAssigName();
			newTitle=assetReassig.getNewAssigTitle();
			newEmpStatus=assetReassig.getNewAssigEmpStatus();
			newProject=assetReassig.getNewAssigProject();
			newEmail=assetReassig.getNewAssigEmail();
			reassignedBy=assetReassig.getReassignedBy();
			emailReassigner=assetReassig.getEmailReassigner();
						
			//Converting Long to String
			assetIdString=String.valueOf(id);
			
			LocalDateTime now=LocalDateTime.now(); 
			
			//Generating today's date
			DateTimeFormatter dtfRecord=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
						
			todayDate=dtfRecord.format(now);
			
			//Complementing the Re-Assignation object
			assetReassig.setAssetId(assetIdString);
			assetReassig.setAssetNumber(assetNumber);
			assetReassig.setAssigName(username);
			assetReassig.setAssigEmail(email);
			assetReassig.setAssigEmpStatus(empStatus);
			assetReassig.setAssigProject(project);
			assetReassig.setAssigTitle(title);
					
			assetReassig.setDateReassignation(todayDate);
			assetReassig.setKluch(kluch);
			
			//System.out.println(assetReassig);
			
			//Saving re-assignation information
			serviceReassig.createReassig(assetReassig);
			
			//Saving changes to the asset object
			service.assetReassignation(id,newUsername,newTitle,newEmpStatus,newProject,newEmail,reassignedBy,emailReassigner);
			
			model.addAttribute("assetReassig",assetReassig);
			
			model.addAttribute("username",username);
			model.addAttribute("email",email);
			model.addAttribute("empStatus",empStatus);
			model.addAttribute("title",title);
			model.addAttribute("project",project);
			
			model.addAttribute("assetNumber",assetNumber);
			
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("priznak",priznak);
						
			return "assetsReassigRedirect";

		}
			
}
