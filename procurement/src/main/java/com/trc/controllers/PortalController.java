package com.trc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ItemsEntity;
import com.trc.entities.PeripheralsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.services.AssetsService;
import com.trc.services.EmailService;
import com.trc.services.ItemsService;
import com.trc.services.PeripheralsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.TempUsersService;
import com.trc.services.TitlesService;

@Controller
@RequestMapping("/portal")
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
	
	@Autowired
	PeripheralsService servicePeripherals;
	
	@Autowired
	EmailService serviceEmails;
	
	@Autowired
	TempUsersService serviceTempUsers;
	

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
	
	@RequestMapping(path={"/init"})
	public String portalForm1(Model model,String assetId) throws RecordNotFoundException
	{
		String kluch=null;
		
		//System.out.println(assetId);
		
		//Checking if this is the first input in the session
		if(assetId==null)
		{	
		
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
			
		
			//Creating unique identifier for this session
			kluch=kluchDate +"-"+ seed;
			
		}
		else
		{
			//As it is not the first input, then we can retrieve the kluch for this session
			Long assetIdLong=Long.parseLong(assetId);
			
			AssetsEntity firstAsset=service.getAssetById(assetIdLong);
			
			kluch=firstAsset.getKluch();
						
		}
		
		
		//Preparing list of projects according to their udelny bes
		List<ProjectsEntity> projects=serviceProjects.getAllHHSbyUB();
		
		//Preparing list of titles
		List<TitlesEntity> titles=serviceTitles.getAllByName();
		
		//Creating the object
		model.addAttribute("asset",new AssetsEntity());
		
		model.addAttribute("projects",projects);
		model.addAttribute("titles",titles);
		model.addAttribute("kluch",kluch);
		
		return "assetsPortalIni";
		
		
	}
	
	
	
	
	
	@RequestMapping(path="/itForm", method=RequestMethod.POST)
	public String portalForm4(Model model,AssetsEntity asset,String firstName,String lastName)
	{
		String titleName=null;
		String projectName=null;
		
		String username=null;
		String repitaya=", ";
		
		username=lastName+repitaya+firstName;
		
		asset.setUsername(username);
		
		//Preparing list of items
		List<ItemsEntity> items=serviceItems.getAllMainItems();
				
		//Retrieving title name
		titleName=serviceTitles.getTitleByNumber(asset.getTitle());
		
		//Retrieving program name
		projectName=serviceProjects.getProjectByNum(asset.getProject());
		
		model.addAttribute("asset",asset);
		
		model.addAttribute("titleName",titleName);
		model.addAttribute("projectName",projectName);
				
		model.addAttribute("items",items);
		
		return "assetsPortalItForm";
		
	}	
	
	
	@RequestMapping(path="/periphOrNot", method=RequestMethod.POST)
	public String portalForm3(Model model,AssetsEntity asset)
	{
		//System.out.println(asset);
				
		String assetId=null;
		String todayDate=null;
		
				
		String division="300";
		String active="Yes";
		String klass="IT";
		
			
		LocalDateTime now=LocalDateTime.now(); 
		
				
		//Generating today's date
		DateTimeFormatter dtfToday=DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		todayDate=dtfToday.format(now);
								
		//Adding asset information
		asset.setDivision(division);
		asset.setActive(active);
		asset.setKlass(klass);
		asset.setDateCreation(todayDate);
		
		//Creating the object
		AssetsEntity newAsset=new AssetsEntity();
		
		newAsset.setItem(asset.getItem());
		newAsset.setAssetNumber(asset.getAssetNumber());
		
		newAsset.setUsername(asset.getUsername());
		newAsset.setEmail(asset.getEmail());
		newAsset.setProject(asset.getProject());
		newAsset.setTitle(asset.getTitle());
		newAsset.setEmpStatus(asset.getEmpStatus());
		
		newAsset.setNotes(asset.getNotes());
		newAsset.setKluch(asset.getKluch());
		newAsset.setDateCreation(asset.getDateCreation());
		newAsset.setStatus(asset.getStatus());
		
		newAsset.setDivision(division);
		newAsset.setActive(active);
		newAsset.setKlass(klass);
				
		
		//Saving the asset
		service.createAsset(newAsset);
				
		//Retrieving asset ID
		Long assetIdLong=newAsset.getAssetid();
		
		assetId=String.valueOf(assetIdLong);
				
		model.addAttribute("assetId",assetId);
		
		//System.out.println("Asset ID : "+ assetId);
			
		return "assetsPortalPeriphOrNot";

	}
	
	
	
	@RequestMapping(path="/periphForm", method=RequestMethod.POST)
	public String portalForm10(Model model, String assetId) throws RecordNotFoundException
	{
		//Preparing list of already input peripherals
		List<PeripheralsEntity> periphs=servicePeripherals.getByAssetId(assetId);
		
		List<ItemsEntity> list=serviceItems.getAllPeripheralsHHS();
		
		String description=null;
		String peripheralNum=null;
		
		//Retrieving the list of descriptions
		for(PeripheralsEntity periph : periphs)
		{
			peripheralNum=periph.getPeripheralNum();
			description=serviceItems.getItemByNumber(peripheralNum);
			periph.setKluch(description);
		}
		
		//Creating the object
		model.addAttribute("peripheral",new PeripheralsEntity());
			
		model.addAttribute("items",list);
		model.addAttribute("periphs",periphs);
		model.addAttribute("assetId",assetId);
		
		return "assetsPortalPeriphForm";
		
	}
	
	
	
	@RequestMapping(path="/morePeriph", method=RequestMethod.POST)
	public String portalForm4B(Model model, PeripheralsEntity peripheral, String assetId) throws RecordNotFoundException
	{
				
		String description=null;
		String peripheralNum=null;
		String active="Yes";
		
		//Retrieving peripheral description
		peripheralNum=peripheral.getPeripheralNum();
		description=serviceItems.getItemByNumber(peripheralNum);
						
		//Setting the connection with the original asset, active 
		peripheral.setAssetId(assetId);
		peripheral.setActive(active);
						
		//Save peripheral information
		servicePeripherals.createOrUpdate(peripheral);
		
		
		//Generating the list of already input peripherals
		List<PeripheralsEntity> list=servicePeripherals.getByAssetId(assetId);
		
		//Retrieving the list of descriptions
		for(PeripheralsEntity periph : list)
		{
			peripheralNum=periph.getPeripheralNum();
			description=serviceItems.getItemByNumber(peripheralNum);
			periph.setDescription(description);
		}
		
		model.addAttribute("peripherals",list);
		
		model.addAttribute("assetId",assetId);
		model.addAttribute("description",description);
							
		return "assetsPortalMorePeriph";
				
	}
	
	
	@RequestMapping(path="/moreAssets", method=RequestMethod.POST)
	public String portalForm7b(Model model, String assetId) throws RecordNotFoundException
	{
		
		
		model.addAttribute("assetId",assetId);
			
			
		return "assetsPortalMoreAssets";
			
	
	}	
	
	
	@RequestMapping(path="/byeForm", method=RequestMethod.POST)
	public String portalForm7(Model model,String assetId)
	{		
			
		model.addAttribute("assetId",assetId);
		
		return "assetsPortalByeForm";
		
	}
	
		
	
	@RequestMapping(path="/endForm", method=RequestMethod.POST)
	public String portalForm6a(Model model, String assetId, String author, String authorEmail) throws JsonProcessingException, RecordNotFoundException
	{
		String toEmail=null;
		String kluch=null;
		
		Long assetIdLong=Long.parseLong(assetId);
		
		AssetsEntity asset=service.getAssetById(assetIdLong);
		
		//Obtaining the kluch of this session
		kluch=asset.getKluch();
		
		//Updating fields in table assets
		service.updatePortalAuthorInfo(kluch,author,authorEmail);
		
		toEmail=authorEmail;
		
		//Obtaining recipient email
		//toEmail=asset.getAuthorEmail();
		
		//Sending the receipt email
		serviceEmails.sendInventoryReceipt(toEmail,asset,author,authorEmail);
		
		return "assetsPortalEndForm";
		
	}
	
	@GetMapping("/passSendForm")
	public String sendPassForm()
	{
		
		return "usersSendPass";
		
		
	}
	
	
	@RequestMapping(path="/passSendCon", method=RequestMethod.POST)
	public String sendPass(Model model,String toEmail)
	{
		Boolean priznakNewUser=false;
		
		int passwordInt=0;
		
		String password=null;
		String domain="@cc-dc.org";
		String message="An email was sent to your address. Please review your inbox and use that code as your password...";
		String subject="Sending pass code for the HHS App";
		String body="Your passcode is : ";
		String pustoy="";
		String trail=null;
		String role="user";
		
		//Email address concatenation
		toEmail=toEmail+domain;
		
		//Generating a random password of 4 digits
		passwordInt=serviceEmails.generateRandomIntIntRange(1000,9999);
		
		//Generating a random symbol as trailer
		trail=serviceEmails.generateRandomSymbol();
		
		//Converting the password int to password string
		password=Integer.toString(passwordInt);
		
		//Finalizing the password formation
		password=password+trail;
		
		//Body message concatenation
		body=body+pustoy+password;
		
		//System.out.println("The password is "+ password);
		//System.out.println("The subject is "+ subject);
		//System.out.println("The message is "+ body);
		
		//Checking if this is a new user
		priznakNewUser=serviceTempUsers.checkNewUser(toEmail);
		
		if(priznakNewUser)
		{	
			//Updating password for this user
			serviceTempUsers.updatePass(toEmail,password);
			
		}
		else
		{
			//Registering the new user in the temp table
			serviceTempUsers.saveNewTempUser(toEmail,password,role);
		}
			
			
		//Sending the email
		serviceEmails.sendMail(toEmail, subject, body);
		
		model.addAttribute("message",message);
		
		return "login";
		
		
	}
			
	@RequestMapping(path="/userLogin", method=RequestMethod.POST)
	public String clientLogin(Model model,String email,String password)
	{
		Boolean priznakSuccess=false;
		String message="Invalid username/password...";
				
		//Retrieving stored password
		priznakSuccess=serviceTempUsers.checkPass(email,password);
		
		if(priznakSuccess)
		{	
			//System.out.println("User has the right password...");
			return "redirect:/procurement/index";
					
		}
		else
		{
			//System.out.println("User has not the right password...");
			model.addAttribute("message",message);
			return "login";
			
		}	
		
	}
	
}
