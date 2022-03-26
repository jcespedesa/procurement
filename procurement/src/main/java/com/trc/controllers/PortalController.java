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
import com.trc.entities.LogsEntity;
import com.trc.entities.PeripheralsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SettingsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.AssetsService;
import com.trc.services.ClientsService;
import com.trc.services.EmailService;
import com.trc.services.ItemsService;
import com.trc.services.LogsService;
import com.trc.services.PeripheralsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SettingsService;
import com.trc.services.SitesService;
import com.trc.services.TempUsersService;
import com.trc.services.TitlesService;
import com.trc.services.UsersService;

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
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	SettingsService serviceSettings;
	
	@Autowired
	LogsService serviceLogs;
	
	@Autowired
	ClientsService serviceClients;
	

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
	
	@RequestMapping(path="/init")
	public String portalForm1(Model model,String assetId,Long quserId) throws RecordNotFoundException
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
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
				
		model.addAttribute("projects",projects);
		model.addAttribute("titles",titles);
		model.addAttribute("kluch",kluch);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "assetsPortalIni";
		
		
	}
	
	
	
	
	
	@RequestMapping(path="/itForm", method=RequestMethod.POST)
	public String portalForm4(Model model,AssetsEntity asset,String firstName,String lastName,Long quserId)
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
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		model.addAttribute("asset",asset);
		
		model.addAttribute("titleName",titleName);
		model.addAttribute("projectName",projectName);
				
		model.addAttribute("items",items);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "assetsPortalItForm";
		
	}	
	
	
	@RequestMapping(path="/periphOrNot", method=RequestMethod.POST)
	public String portalForm3(Model model,AssetsEntity asset, Long quserId)
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
		
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
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
		
		//Inputing author fields in case the operator is not getting to the last screen
		newAsset.setAuthor(quser.getUsername());
		newAsset.setAuthorEmail(quser.getEmail());
				
		//Saving the asset
		service.createAsset(newAsset);
		
		//Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Creating new IT asset with number: "+ newAsset.getAssetNumber());
		log.setObject("New asset has a description as: "+ newAsset.getItem());
		serviceLogs.saveLog(log);
		
				
		//Retrieving asset ID
		Long assetIdLong=newAsset.getAssetid();
		
				
		assetId=String.valueOf(assetIdLong);
				
		model.addAttribute("assetId",assetId);
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
				
		//System.out.println("Asset ID : "+ assetId);
			
		return "assetsPortalPeriphOrNot";

	}
	
	
	
	@RequestMapping(path="/periphForm", method=RequestMethod.POST)
	public String portalForm10(Model model, String assetId, Long quserId) throws RecordNotFoundException
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
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
			
		model.addAttribute("items",list);
		model.addAttribute("periphs",periphs);
		model.addAttribute("assetId",assetId);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "assetsPortalPeriphForm";
		
	}
	
	
	
	@RequestMapping(path="/morePeriph", method=RequestMethod.POST)
	public String portalForm4B(Model model, PeripheralsEntity peripheral, String assetId, Long quserId) throws RecordNotFoundException
	{
				
		String description=null;
		String peripheralNum=null;
		String active="Yes";
		String assetIdString=null;
		
		//Retrieving peripheral description
		peripheralNum=peripheral.getPeripheralNum();
		description=serviceItems.getItemByNumber(peripheralNum);
		
		//Converting id from long to string
		assetIdString=String.valueOf(assetId);  
						
		//Setting the connection with the original asset, active 
		peripheral.setAssetId(assetIdString);
		peripheral.setActive(active);
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
						
		//Save peripheral information
		servicePeripherals.createOrUpdate(peripheral);
		
		//Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Creating a new peripheral for IT Item. Item ID is "+ assetId);
		log.setObject("Description of the peripheral is "+ peripheral.getDescription());
		serviceLogs.saveLog(log);
		
		
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
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
							
		return "assetsPortalMorePeriph";
				
	}
	
	
	@RequestMapping(path="/moreAssets", method=RequestMethod.POST)
	public String portalForm7b(Model model, String assetId, Long quserId) throws RecordNotFoundException
	{
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		model.addAttribute("assetId",assetId);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);	
			
		return "assetsPortalMoreAssets";
			
	
	}	
	
	
	@RequestMapping(path="/byeForm", method=RequestMethod.POST)
	public String portalForm7(Model model,String assetId,Long quserId)
	{		
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		model.addAttribute("assetId",assetId);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "assetsPortalByeForm";
		
	}
	
		
	
	@RequestMapping(path="/endForm", method=RequestMethod.POST)
	public String portalForm6a(Model model, String assetId, String author, String authorEmail,Long quserId) throws JsonProcessingException, RecordNotFoundException
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
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		//Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Sending Receipt confirmation for session of new Assets creation: "+ asset.getAssetNumber());
		log.setObject("Description of the asset(s) is ;"+ asset.getItem());
		serviceLogs.saveLog(log);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "assetsPortalEndForm";
		
	}
	
	@GetMapping("/passSendForm")
	public String sendPassForm()
	{
		
		return "usersSendPass";
		
		
	}
	
	
	@RequestMapping(path="/passSendCon", method=RequestMethod.POST)
	public String sendPass(Model model,String email) throws RecordNotFoundException
	{
		SettingsEntity setting=serviceSettings.getSettingByDescription("emailSettings");
		
		int existentEmail=0;
		
		String pass=null;
		String localMessage="An email was sent to your inbox, please review...";
		String message="This is your code to access the HHS IT Inventory System: ";	
		String subject="Your code for the HHS IT Inventory System";
		String pustoy=" ";
		String arrobaSymbol="@";
		
		//Concatening full email string
		email=email+arrobaSymbol+setting.getParam2();
		
				
		//Verifying if this is an existent email in the system
		existentEmail=serviceUsers.findDuplicates(email);
		
		//System.out.println("Sent email: "+ email);
		//System.out.println("The value of existentEmail is: "+ existentEmail);
		
		LogsEntity log=new LogsEntity();
		
		if(existentEmail==0)
		{
			localMessage="Invalid or no registered email. Please contact your System Administrator ";
			
			log.setSubject(email);
			log.setAction("Failed to receive an access code due to misspelled or unregistered email");
			log.setObject("Main login page");
				
		}
		else
		{
			//Retrieving user information
			UsersEntity user=serviceUsers.getUserByEmail(email);
			
			//Creating the access code
			pass=serviceUsers.createAccessCode(user.getUserid());
		
			//Assembling the message
			message=message+pustoy+pass;
			
									
			//Sending the registration email
			serviceEmails.sendMail(user.getEmail(), subject, message, setting.getParam1());
			
			//Encoding password
	    	//encodedPass=serviceUsers.encodePass(pass);
									
			//Saving the new password for this user
			serviceUsers.setPass(user.getUserid(),pass);
			
			log.setSubject(email);
			log.setAction("Receiving a new automatic generated code to access the system");
			log.setObject("Main login page");
						
		}
		
		serviceLogs.saveLog(log);
		
		model.addAttribute("message",localMessage);
		
		return "login";
		
		
	}
			
	@RequestMapping(path="/userLogin", method=RequestMethod.POST)
	public String clientLogin(Model model,String email,String password)
	{
		Boolean priznakSuccess=false;
		String message="Invalid username/password...";
				
		//Retrieving stored password
		priznakSuccess=serviceUsers.checkPass(email,password);
		
		if(priznakSuccess)
		{	
			//System.out.println("User has the right password...");
						
			//Retrieving user credentials
			UsersEntity quser=serviceUsers.getUserByEmail(email);
			
			//System.out.println("Selected user was: "+ user);
			
			model.addAttribute("quser",quser);
			model.addAttribute("quserId",quser.getUserid());
			
			return "mainMenu";
					
		}
		else
		{
			//System.out.println("User has not the right password...");
			model.addAttribute("message",message);
			return "login";
			
		}	
		
	}
	
	
	@RequestMapping(path="/mainMenu", method=RequestMethod.POST)
	public String backToMainMenu(Model model,Long quserId)
	{
		
		//Retrieving user credentials
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		//System.out.println("Selected user was: "+ quser);
		
		model.addAttribute("quser",quser);
		
		model.addAttribute("quserId",quserId);
		
		return "mainMenu";
			
	}
	
	@RequestMapping(path="/insertValues")
	public String insertValuesForm(Model model,Long quserId)
	{		
		
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
				
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "insertValuesForm";
		
	}
	
	
	@RequestMapping(path="/apiEmailSwitch", method=RequestMethod.POST)
	public void sendEmail(Model model,String email,String subject,String message, String project) throws RecordNotFoundException
	{
		SettingsEntity setting=serviceSettings.getSettingByDescription("emailSettings");
		
		LogsEntity log=new LogsEntity();
		
		log.setSubject(email);
		log.setAction("Sending email using the Email Switch Feature in behalf of the "+ project);
		log.setObject("API Email Switch");
		
		//Sending the registration email
		serviceEmails.sendMail(email,subject,message, setting.getParam1());
		
		
						
	}

}
