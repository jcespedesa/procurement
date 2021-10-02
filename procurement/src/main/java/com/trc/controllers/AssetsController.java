package com.trc.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
	
	//CRUD operations for assets
	
		@GetMapping("/list")
		public String getAllAssets(Model model) throws RecordNotFoundException
		{
			List<AssetsEntity> list=service.getAllAssets();
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String assetId=null;
			
			Long assetIdLong=null;
			
			for(AssetsEntity asset : list)
			{
				priznakPeripherals=0;
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
							
			}
				
															
			model.addAttribute("assets",list);
						
			return "assetsList";
			
			
		}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editAssetsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
											
						
			if(id.isPresent())
			{
				AssetsEntity entity=service.getAssetById(id.get());
				
				String assetId=null;
				
				Long assetIdLong=id.get();
				
				assetId=String.valueOf(assetIdLong);
				
				//Retrieving related peripherals for this asset				
				List<PeripheralsEntity> peripherals=servicePeripherals.getByAssetId(assetId);
				
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
			}
			else
			{
				String todayDate=null;
				
				LocalDateTime now=LocalDateTime.now(); 
				
				priznakNew="Yes";
				
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
			
			model.addAttribute("priznakNew",priznakNew);
			
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
		public String findByAssetNum(Model model,String stringSearch) throws RecordNotFoundException
		{
			
						
			List<AssetsEntity> list=service.getByAssetNum(stringSearch);
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String assetId=null;
			
			Long assetIdLong=null;
						
			
			for(AssetsEntity asset : list)
			{
				priznakPeripherals=0;
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
						
			return "assetsList";
			
		}
		
		@RequestMapping(path="/findAssetsByItem", method=RequestMethod.POST)
		public String findByKlass(Model model,String stringSearch) throws RecordNotFoundException
		{
			
						
			List<AssetsEntity> list=service.getByItem(stringSearch);
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String assetId=null;
			
			Long assetIdLong=null;
						
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
						
			return "assetsList";
			
		}
		
		@RequestMapping(path="/findAssetsByAuthor", method=RequestMethod.POST)
		public String findByAuthor(Model model,String stringSearch) throws RecordNotFoundException
		{
									
			List<AssetsEntity> list=service.getByAuthor(stringSearch);
			
			int priznakPeripherals=0;
						
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String assetId=null;
			
			Long assetIdLong=null;					
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
						
			return "assetsList";
			
		}
		
		
		@RequestMapping(path="/findAssetsByProgram", method=RequestMethod.POST)
		public String findByProgram(Model model,String stringSearch) throws RecordNotFoundException
		{
									
			List<AssetsEntity> list=service.getByProgram(stringSearch);
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			String itemName=null;
			String itemNumber=null;
			String assetId=null;
			
			Long assetIdLong=null;					
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
							
			}
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
						
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
			
			String ageString=null;
															
			Long assetIdLong=null;
			
			//Testing the age generator
			//String datePurchased="2010-04-23";
			//age=service.priznakOldItem(datePurchased);
			
			//System.out.println("The asset is "+ age +" years old ");
			
			for(AssetsEntity asset : list)
			{
				age=0;
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
								
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
				
				peripheral.setNotes(username);
				
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
				
				peripheral.setNotes(username);
				
			}
			
	 
	        ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	        	        
	        String[] csvHeader={"Item","Asset Number","Maker", "Model", "Date Purchased","User","Title","Emp. Status","Project","Program","Date Inventory","Registered by","Email","Item Class"};
	        String[] nameMapping={"site","assetNumber","maker","model","datePurchased","username","title","empStatus","project","program","dateCreation","author","authorEmail","klass"};
	        
	        String[] csvHeader2={"Description","Asset Number","Item Related","User"};
	        String[] nameMapping2={"description","assetNumber","itemId","notes"};
	        
	        	         
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
			
}
