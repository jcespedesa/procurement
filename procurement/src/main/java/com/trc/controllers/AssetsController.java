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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.trc.entities.AssetAssigEntity;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ClientsEntity;
import com.trc.entities.DivisionsEntity;
import com.trc.entities.ItemsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.PeripheralsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.AssetsAssigService;
import com.trc.services.AssetsService;
import com.trc.services.ClientsService;
import com.trc.services.DivisionsService;
import com.trc.services.ItemsService;
import com.trc.services.LogsService;
import com.trc.services.PeripheralsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.TitlesService;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement/assets")
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
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
	@Autowired
	ClientsService serviceClients;
	
	@Autowired
	DivisionsService serviceDivisions;
	
	//CRUD operations for assets
	
		@RequestMapping(path="/menu")
		public String menuAssets(Model model, Long quserId)
		{
			//Preparing list of users
			List<ClientsEntity> clients=serviceClients.getAllClientsAlphab();
			
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
						
			//Preparing list of authors email
			List<String> emails=service.getAuthorEmails();
			
			//Preparing list of assignees
			List<String> assigEmails=service.getAssigneeEmails();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
						
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			model.addAttribute("assigEmails",assigEmails);
			
			model.addAttribute("clients",clients);
			
			return "assetsMenu";
			
			
		}
		
		
	
		
		@RequestMapping(path="/list",method=RequestMethod.POST)
		public String getAllAssets(Model model,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
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
			
			String cname=null;
			String clientId=null;
			String authorId=null;
			String authorName=null;
			
			Long assetIdLong=null;
			Long clientIdLong=null;
			Long authorIdLong=null;						
			
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
				
				//finding employee name
				clientId=asset.getClientId();
				clientIdLong=Long.parseLong(clientId);
				cname=serviceClients.getCnameById(clientIdLong);
				
				//finding author name
				authorId=asset.getAuthorId();
				authorIdLong=Long.parseLong(authorId);
				authorName=serviceClients.getCnameById(authorIdLong);
								
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setUsername(cname);
				asset.setAuthor(authorName);
							
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
																		
			model.addAttribute("assets",list);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsList";
			
			
		}
		
		
		@RequestMapping(path={"/new"},method=RequestMethod.POST)
		public String newAsset(Model model, String stringSearch, Long quserId)
		{
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
								
			//Preparing list of clients
			List<ClientsEntity> clients=serviceClients.getAllActives();
			
			//Preparing list of sites
			List<SitesEntity> sites=serviceSites.getAllHHSsites();
			
			//Preparing list of Divisions
			List<DivisionsEntity> divisions=serviceDivisions.getAllByName();
			
			//Preparing list of active projects by udelny bes
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
			
						
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
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("seed",seed);
			model.addAttribute("kluch",todayDate);
						
			model.addAttribute("items",items);
			model.addAttribute("clients",clients);
			model.addAttribute("sites",sites);
			model.addAttribute("projects",projects);
			model.addAttribute("divisions",divisions);
			
			model.addAttribute("priznakNew",priznakNew);
			model.addAttribute("stringSearch",stringSearch);
			
			return "assetsAddEdit";
		}

		
		
		@RequestMapping(path={"/edit"},method=RequestMethod.POST)
		public String editAssetsById(Model model,Long id,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException 
		{
			ClientsEntity client=new ClientsEntity();
			ClientsEntity oldUser=new ClientsEntity();
			
			new ClientsEntity();
			
			Long clientIdLong=null;
			Long authorIdLong=null;
			
			String assetId=null;
			String username=null;
			String authorName=null;
			
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of clients
			List<ClientsEntity> clients=serviceClients.getAllActives();
						
			//Preparing list of sites
			List<SitesEntity> sites=serviceSites.getAllHHSsites();
			
			//Preparing list of Divisions
			List<DivisionsEntity> divisions=serviceDivisions.getAllByName();
			
			//Preparing list of active projects by udelny bes
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
			
									
			String site=null;
			String project=null;
						
			String itemName=null;
			String itemNumber=null;
								
			String priznakNew=null;
			
			Long oldUserId=null;
											
			AssetsEntity entity=service.getAssetById(id);
												
			assetId=String.valueOf(id);
							
			//Retrieving related peripherals for this asset				
			List<PeripheralsEntity> peripherals=servicePeripherals.getByAssetId(assetId);
				
			//Preparing history of re-assignations 
			List<AssetAssigEntity> assigs=serviceReassig.getAssigById(assetId);
			
			for(AssetAssigEntity assig : assigs)
			{
				
				oldUserId=Long.parseLong(assig.getAssigId());
								
				oldUser=serviceClients.getClientById(oldUserId);
								
				assig.setAssigName(oldUser.getCname());
									
			}
			
			
											
			itemNumber=entity.getItem();
															
			priznakNew="No";
								
			//Finding names or descriptions
			site=serviceSites.getSiteByNumber(entity.getSite());
			project=serviceProjects.getSiteBySiteNumber1(entity.getProject());
			itemName=serviceItems.getItemByNumber(itemNumber);
						
			//Finding cname(assignee)
			clientIdLong=Long.parseLong(entity.getClientId());
			
			client=serviceClients.getClientById(clientIdLong);
			username=client.getCname();
			
			//Finding authorName(person reporting the asset)
			authorIdLong=Long.parseLong(entity.getAuthorId());
			
			serviceClients.getClientById(authorIdLong);
			authorName=client.getCname();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("asset",entity);
				
			model.addAttribute("siteName",site);
			model.addAttribute("projectName",project);
			model.addAttribute("itemName",itemName);
						
			model.addAttribute("peripherals",peripherals);
			model.addAttribute("assigs",assigs);
						
			model.addAttribute("items",items);
			model.addAttribute("sites",sites);
			model.addAttribute("projects",projects);
			model.addAttribute("divisions",divisions);
			
			model.addAttribute("clients",clients);
			
			model.addAttribute("priznakNew",priznakNew);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			model.addAttribute("username",username);
			model.addAttribute("authorName",authorName);
						
			return "assetsAddEdit";
		}
		
		@RequestMapping(path="/delete", method=RequestMethod.POST)
		public String deleteAssetById(Model model,Long id,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
			String message="Asset was deleted...";
			
			AssetsEntity asset=service.getAssetById(id);
			
			//Deleting the record
			service.deleteAssetById(id);
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        //Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Deleting asset from the database. Item ID is "+ asset.getAssetNumber());
			log.setObject(asset.getItem());
			serviceLogs.saveLog(log);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsRedirect";
			
		}
		
		@RequestMapping(path="/createAsset", method=RequestMethod.POST)
		public String createOrUpdateAsset(Model model,AssetsEntity asset,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
						
			String message="Asset was updated successfully...";
									
			service.createOrUpdate(asset);
						
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	               
	        	        	        
	        //Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Creating/modifying asset. Item ID is "+ asset.getAssetNumber());
			log.setObject(asset.getItem());
			serviceLogs.saveLog(log);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("priznak",priznak);
			
			return "assetsRedirect";

		}
		
		
		
		@RequestMapping(path="/search", method=RequestMethod.POST)
		public String search(Model model, Long quserId)
		{
						
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
						
			//Preparing list of authors 
			List<String> emails=service.getAuthorEmails();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			
			return "searchFormAssets";
			
			
		}
		
		@RequestMapping(path="/findAssetByNum", method=RequestMethod.POST)
		public String findByAssetNum(Model model,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
			String cname=null;
			String clientId=null;
			String authorId=null;
			String authorName=null;
						
			List<AssetsEntity> list=service.getByAssetNum(stringSearch);
			new ClientsEntity();
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String assetId=null;
			
			String titleName=null;
			String titleNumber=null;
			
			Long assetIdLong=null;
			Long clientIdLong=null;	
			Long authorIdLong=null;
			
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
				
				//finding employee name
				clientId=asset.getClientId();
				clientIdLong=Long.parseLong(clientId);
				cname=serviceClients.getCnameById(clientIdLong);
				
				//finding author name
				authorId=asset.getAuthorId();
				authorIdLong=Long.parseLong(authorId);
				authorName=serviceClients.getCnameById(authorIdLong);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setUsername(cname);
				asset.setAuthor(authorName);			
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
									
			return "assetsList";
			
		}
		
		@RequestMapping(path="/findAssetsByItem", method=RequestMethod.POST)
		public String findByKlass(Model model,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
			String cname=null;
			String clientId=null;
			String authorId=null;
			String authorName=null;
						
			List<AssetsEntity> list=service.getByItem(stringSearch);
			new ClientsEntity();
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;
			Long clientIdLong=null;	
			Long authorIdLong=null;
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//finding employee name
				clientId=asset.getClientId();
				clientIdLong=Long.parseLong(clientId);
				cname=serviceClients.getCnameById(clientIdLong);
				
				//finding author name
				authorId=asset.getAuthorId();
				authorIdLong=Long.parseLong(authorId);
				authorName=serviceClients.getCnameById(authorIdLong);
				
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setUsername(cname);
				asset.setAuthor(authorName);
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		@RequestMapping(path="/findAssetsByAuthor", method=RequestMethod.POST)
		public String findByAuthor(Model model,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
			String cname=null;
			String clientId=null;
			String authorId=null;
			String authorName=null;
			
			List<AssetsEntity> list=service.getByAuthor(stringSearch);
			new ClientsEntity();
			
			int priznakPeripherals=0;
						
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;
			Long clientIdLong=null;
			Long authorIdLong=null;
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//finding employee name
				clientId=asset.getClientId();
				clientIdLong=Long.parseLong(clientId);
				cname=serviceClients.getCnameById(clientIdLong);
				
				//finding author name
				authorId=asset.getAuthorId();
				authorIdLong=Long.parseLong(authorId);
				authorName=serviceClients.getCnameById(authorIdLong);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setUsername(cname);
				asset.setAuthor(authorName);
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		
		@RequestMapping(path="/findAssetsByProgram", method=RequestMethod.POST)
		public String findByProgram(Model model,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
			String cname=null;
			String clientId=null;
			String authorId=null;
			String authorName=null;
			
			List<AssetsEntity> list=service.getByProgram(stringSearch);
			new ClientsEntity();
			
			int priznakPeripherals=0;
			
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;	
			Long clientIdLong=null;
			Long authorIdLong=null;
			
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//finding employee name
				clientId=asset.getClientId();
				clientIdLong=Long.parseLong(clientId);
				cname=serviceClients.getCnameById(clientIdLong);
				
				//finding author name
				authorId=asset.getAuthorId();
				authorIdLong=Long.parseLong(authorId);
				authorName=serviceClients.getCnameById(authorIdLong);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setUsername(cname);	
				asset.setAuthor(authorName);
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		
		@RequestMapping(path="/findAssetsByAssignee", method=RequestMethod.POST)
		public String findByAssignee(Model model,String stringSearch,String priznak,Long quserId) throws RecordNotFoundException
		{
			String cname=null;
			String clientId=null;
			String authorId=null;
			String authorName=null;
			
			List<AssetsEntity> list=service.getByClientId(stringSearch);
			new ClientsEntity();
			
			int priznakPeripherals=0;
						
			String projectNumber=null;
			String projectName=null;
			
			String itemName=null;
			String itemNumber=null;
			
			String titleName=null;
			String titleNumber=null;
			
			String assetId=null;
			
			Long assetIdLong=null;	
			Long clientIdLong=null;
			Long authorIdLong=null;
						
			for(AssetsEntity asset : list)
			{
			
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//finding title description
				titleNumber=asset.getTitle();
				titleName=serviceTitles.getTitleByNumber(titleNumber);
				
				//finding employee name
				clientId=asset.getClientId();
				clientIdLong=Long.parseLong(clientId);
				cname=serviceClients.getCnameById(clientIdLong);
				
				//finding author name
				authorId=asset.getAuthorId();
				authorIdLong=Long.parseLong(authorId);
				authorName=serviceClients.getCnameById(authorIdLong);
				
				//Finding if this current asset is having peripherals
				assetIdLong=asset.getAssetid();
				assetId=Long.toString(assetIdLong);
				priznakPeripherals=service.findHowManyPeripherals(assetId);
				
				if(priznakPeripherals > 0)
					asset.setStrobe("Yes");
				
				asset.setProgram(projectName);
				asset.setSite(itemName);
				asset.setTitle(titleName);
				asset.setUsername(cname);
				asset.setAuthor(authorName);
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
				
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("assets",list);
			model.addAttribute("priznak",priznak);
						
			return "assetsList";
			
		}
		
		
		
		@RequestMapping(path="/views", method=RequestMethod.POST)
		public String views(Model model, Long quserId)
		{
						
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
						
			//Preparing list of authors email
			List<String> emails=service.getAuthorEmails();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			
			return "viewsMenuAssets";
			
			
		}
		
		@RequestMapping(path="/viewAssetsByProgram", method=RequestMethod.POST)
		public String viewByProgram(Model model,String stringSearch,Long quserId) throws RecordNotFoundException
		{
			//Retrieving assets list						
			List<AssetsEntity> list=service.getByProgram(stringSearch);
			
			//Retrieving peripherals list 
			List<PeripheralsEntity> listPeripherals=servicePeripherals.getByProject(stringSearch);
			
			int age=0;
			
			Long clientIdLong=null;
			Long authorIdLong=null;
			
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
			String author=null;
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
				
				//Finding username
				clientIdLong=Long.parseLong(asset.getClientId());
				username=serviceClients.getCnameById(clientIdLong);
				
				//Finding name of the person reporting the asset
				authorIdLong=Long.parseLong(asset.getAuthorId());
				author=serviceClients.getCnameById(authorIdLong);
			
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
				asset.setUsername(username);
				asset.setAuthor(author);
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
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("stringSearch",stringSearch);
			model.addAttribute("viewTitle",viewTitle);	
			
			model.addAttribute("assets",list);
			model.addAttribute("peripherals",listPeripherals);
						
			return "assetsView";
			
		}
		
		
		@RequestMapping(path="/exportCSV", method=RequestMethod.POST)
	    public void exportToCSV(Model model,HttpServletResponse response,String stringSearch,Long quserId) throws IOException, RecordNotFoundException 
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
			
			Long clientIdLong=null;
			Long authorIdLong=null;
			
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
			String author=null;
			
			String periphNumber=null;
			String periphName=null;
			
			Long assetIdLong=null;
						
			for(AssetsEntity asset : list)
			{
			
				//Finding username
				clientIdLong=Long.parseLong(asset.getClientId());
				username=serviceClients.getCnameById(clientIdLong);
				
				//Finding name of person reporting the asset
				authorIdLong=Long.parseLong(asset.getAuthorId());
				author=serviceClients.getCnameById(authorIdLong);
				
				//Finding project name
				projectNumber=asset.getProject();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				//Finding item name
				itemNumber=asset.getItem();
				itemName=serviceItems.getItemByNumber(itemNumber);
				
				//Finding title name (unused)
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
				asset.setUsername(username);
				asset.setAuthor(author);
				
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
	        	        
	        String[] csvHeader={"Item","Asset Number","Maker", "Model", "Date Purchased","User","Project","Program","Registered by"};
	        String[] nameMapping={"site","assetNumber","maker","model","datePurchased","username","project","program","author"};
	        
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
	        
	        //Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        //Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Exporting Assets Information to CSV file");
			log.setObject("Kind of exported asset is "+ stringSearch);
			serviceLogs.saveLog(log);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
	         
	    }
		
				
		@RequestMapping(path="/reassign", method=RequestMethod.POST)
		public String reassign(Model model,Long assetId, String stringSearch, String priznak, Long quserId) throws RecordNotFoundException
		{
			//Preparing list of clients
			List<ClientsEntity> clients=serviceClients.getAllActives();
			
			ClientsEntity client=new ClientsEntity();
			
			String itemName=null;
			String itemNumber=null;
			String titleName=null;
			String titleNumber=null;
			String projectName=null;
			String projectNumber=null;
			String clientId=null;
			
			Long clientIdLong=null;
			
			//Retrieving asset entity
			AssetsEntity entity=service.getAssetById(assetId);
			
			itemNumber=entity.getItem();
			titleNumber=entity.getTitle();
			projectNumber=entity.getProject();
			clientId=entity.getClientId();
			
			clientIdLong=Long.parseLong(clientId);
			
			//Retrieving item description
			itemName=serviceItems.getItemByNumber(itemNumber);
			
			//Retrieving title description
			titleName=serviceTitles.getTitleByNumber(titleNumber);
			
			//Retrieving project description
			projectName=serviceProjects.getProjectByNum(projectNumber);
			
			//Retrieving client profile
			client=serviceClients.getClientById(clientIdLong);
			
			//Preparing list of titles
			List<TitlesEntity> titles=serviceTitles.getAllTitles();
						
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
			
			//System.out.println("title number is "+ titleNumber);
			//System.out.println("title is "+ titleName);
			//System.out.println("notes are "+ entity.getNotes());
			
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("assetReassig",new AssetAssigEntity());
						
			model.addAttribute("asset",entity);
			model.addAttribute("client",client);
			
			model.addAttribute("projects",projects);
			model.addAttribute("titles",titles);
			model.addAttribute("clients",clients);
			
			model.addAttribute("itemName",itemName);
			model.addAttribute("titleName",titleName);
			model.addAttribute("projectName",projectName);
			
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("priznak",priznak);
			
			return "assetsReassigForm";
			
		}
		
		@RequestMapping(path="/reassignAsset", method=RequestMethod.POST)
		public String reassignAsset(Model model,String assetId,String newAssigId,String oldAssigId,String reassignedBy,String notes,String stringSearch,String priznak, Long quserId) throws RecordNotFoundException
		{
			
			AssetsEntity asset=new AssetsEntity();									
			ClientsEntity newAssignee=new ClientsEntity();
			ClientsEntity oldAssignee=new ClientsEntity();
			
			AssetAssigEntity assignation=new AssetAssigEntity();
			
			Long assetIdLong=null;
			Long newAssigIdLong=null;
			Long oldAssigIdLong=null;
						
			//Converting strings to long
			assetIdLong=Long.parseLong(assetId);
			newAssigIdLong=Long.parseLong(newAssigId);
			oldAssigIdLong=Long.parseLong(oldAssigId);
						
			//Retrieving identities of the old and new assignee, and asset
			newAssignee=serviceClients.getClientById(newAssigIdLong);
			oldAssignee=serviceClients.getClientById(oldAssigIdLong);
			asset=service.getAssetById(assetIdLong);
			
			//Creating the object
			assignation.setAssetId(assetId);
			assignation.setAssigId(oldAssigId);
			assignation.setNewAssigId(newAssigId);
			assignation.setReassignedBy(reassignedBy);
			assignation.setNotes(notes);
			
						
			//Saving re-assignation information
			serviceReassig.createReassig(assignation);
									
			//Saving changes to the asset object
			service.assetReassignation(assetIdLong,newAssigId);
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
			
			//Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Reassigning asset number "+ assetId);
			log.setObject("New recipient of this asset is client ID: "+ newAssigId);
			serviceLogs.saveLog(log);
				        
						
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
									
			model.addAttribute("stringSearch",stringSearch);												
			model.addAttribute("priznak",priznak);
			
			model.addAttribute("asset",asset);
			model.addAttribute("newAssignee",newAssignee);
			model.addAttribute("oldAssignee",oldAssignee);
			model.addAttribute("reassignedBy",reassignedBy);
			model.addAttribute("notes",notes);
						
			return "assetsReassigRedirect";

		}
		
		@RequestMapping(path="/reports", method=RequestMethod.POST)
		public String reports(Model model, Long quserId)
		{
						
			//Preparing list of items
			List<ItemsEntity> items=serviceItems.getAllMainItems();
			
			//Preparing list of projects
			List<ProjectsEntity> projects=serviceProjects.getAllHHSactiveProjects();
						
			//Preparing list of authors email
			List<String> emails=service.getAuthorEmails();
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("items",items);
			model.addAttribute("emails",emails);
			model.addAttribute("projects",projects);
			
			return "reportsMenu";
			
			
		}
		
		@RequestMapping(path="/multipleAssigs",method=RequestMethod.POST)
		public String multipleAssignations(Model model,Long quserId) throws RecordNotFoundException
		{
			List<ClientsEntity> clients=service.getClientsMultipleAssigs();
			
			String message="List of Users Having Assigned IT Items";
						
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("clients",clients);
			model.addAttribute("message",message);
					
			return "multipleAssigsList";
			
			
		}
		
		
		@RequestMapping(path="/viewRepeated", method=RequestMethod.POST)
		public String repeatedItemsView(Model model, Long quserId, String clientId, String cname) throws RecordNotFoundException
		{
						
			//Preparing list of assets assigned to this user
			List<AssetsEntity> assets=service.getByClientId(clientId);
						
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        String itemName=null;
			String itemNumber=null;
	        
	      //finding item description
	        for(AssetsEntity asset : assets)
			{
	        	itemNumber=asset.getItem();
	        	itemName=serviceItems.getItemByNumber(itemNumber);
	        	
	        	asset.setSite(itemName);
	        
			}	
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("assets",assets);
			model.addAttribute("clientId",clientId);
			model.addAttribute("cname",cname);
			
			return "multipleAssigsItems";
			
			
		}
		
		
		//Exporting assigned assets to CSV "exportItemsCSV"
		@RequestMapping(path="/exportItemsCSV", method=RequestMethod.POST)
		public void repeatedItemsExport(Model model,HttpServletResponse response, Long quserId, String clientId, String cname) throws RecordNotFoundException, IOException
		{
			response.setContentType("text/csv");
	        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String currentDateTime=dateFormatter.format(new Date());
	         
	        String headerKey="Content-Disposition";
	       	        	        
	        String headerValue="attachment; filename="+ clientId +"-"+ currentDateTime +".csv";
	        response.setHeader(headerKey,headerValue);
	        
			//Preparing list of assets assigned to this user
			List<AssetsEntity> assets=service.getByClientId(clientId);
						
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        String itemName=null;
			String itemNumber=null;
	        
	      //finding item description
	        for(AssetsEntity asset : assets)
			{
	        	itemNumber=asset.getItem();
	        	itemName=serviceItems.getItemByNumber(itemNumber);
	        	
	        	asset.setSite(itemName);
	        
			}	
	        
	        ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	        
	        String[] csvTitle={"Assets Assigned to: "+ cname};
	        String[] csvSpace={" "};
	        
	        String[] csvHeader={"Asset Number","Description","Model", "Date Purchased","Status","Active","Notes"};
	        String[] nameMapping={"assetNumber","site","model","datePurchased","status","active","notes"};
	        
	        csvWriter.writeHeader(csvTitle);
	        csvWriter.writeHeader(csvSpace);
	        csvWriter.writeHeader(csvHeader);
	         
	        for(AssetsEntity asset : assets) 
	        {
	            csvWriter.write(asset, nameMapping);
	        }
			
	        csvWriter.close();
	        
	        	        
	        //Processing logs
			LogsEntity log=new LogsEntity();
			log.setSubject(quser.getEmail());
			log.setAction("Exporting Assets Information to CSV file");
			log.setObject("Assets registered to "+ cname);
			serviceLogs.saveLog(log);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
		}
			
}
