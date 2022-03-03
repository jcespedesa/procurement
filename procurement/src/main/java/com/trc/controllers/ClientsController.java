package com.trc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.AssetsEntity;
import com.trc.entities.ClientsEntity;
import com.trc.entities.DivisionsEntity;
import com.trc.entities.HhsDivisionsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.TitlesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.ClientsService;
import com.trc.services.DivisionsService;
import com.trc.services.HhsDivisionsService;
import com.trc.services.LogsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.TitlesService;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement/clients")
public class ClientsController 
{
	@Autowired
	ClientsService service;
	
	@Autowired
	HhsDivisionsService serviceHhsDivisions;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	LogsService serviceLogs;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	SitesService serviceSites;
	
	@Autowired
	TitlesService serviceTitles;
	
	@Autowired
	DivisionsService serviceDivisions;
	
	//CRUD operations for Clients
	
	@RequestMapping(path="/list",method=RequestMethod.POST)
	public String getAllClients(Model model,Long quserId)
	{
		List<ClientsEntity> list=service.getAllClientsAlphab();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("clients",list);
		
		return "clientsList";
		
		
	}
	
	@RequestMapping(path={"/new"},method=RequestMethod.POST)
	public String newAsset(Model model, String stringSearch, Long quserId)
	{
				
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
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
			
		model.addAttribute("seed",seed);
		model.addAttribute("kluch",todayDate);
					
		model.addAttribute("sites",sites);
		model.addAttribute("projects",projects);
		model.addAttribute("titles",titles);
		
		model.addAttribute("priznakNew",priznakNew);
		model.addAttribute("stringSearch",stringSearch);
		
		return "clientsAddEdit";
	}

	
	
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editClientsById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		
		//Preparing list of projects
		List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
		
		//Preparing list of divisions
		List<DivisionsEntity> divisions=serviceDivisions.getAllDivisions();
		
		//Preparing list of sites
		List<SitesEntity> sites=serviceSites.getAllHHSsites();
		
		//Preparing list of titles
		List<TitlesEntity> titles=serviceTitles.getAllTitles();
		
		if(id.isPresent())
		{
			ClientsEntity entity=service.getClientById(id.get());
			
						
			model.addAttribute("client",entity);
		}
		else
		{
			model.addAttribute("client",new ClientsEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("projects",projects);
		model.addAttribute("divisions",divisions);
		
		model.addAttribute("sites",sites);
		model.addAttribute("titles",titles);
		
		return "clientsAddEdit";
	}
	
		
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteUserById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving user identity
        ClientsEntity client=service.getClientById(id);
		
		service.deleteClientById(id);
		
		message="Client was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
      //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Deleting an client from the database. Client ID was "+ client.getClientid());
      	log.setObject("Client description was: "+ client.getCname());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "clientsRedirect";
		
	}
	
	@RequestMapping(path="/createClient", method=RequestMethod.POST)
	public String createOrUpdateClient(Model model,ClientsEntity client, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localEmail=null;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        if(client.getClientid()!=null)
        {
        	//Modify the record
		
        	service.createOrUpdate(client);
		
        	message="Client information was successfully updated...";
		        
        	//Processing logs
	      	LogsEntity log=new LogsEntity();
	      	log.setSubject(quser.getEmail());
	      	log.setAction("Modifying client information in the database. User ID was "+ client.getClientid());
	      	log.setObject("Client description is: "+ client.getCname());
	      	serviceLogs.saveLog(log);
        }
        else
        {
        	//Creating a new record
        	
        	//Checking if the email is not already in the system
        	localEmail=client.getEmail();
        	priznakDuplicate=service.findDuplicates(localEmail);
		
        	if(priznakDuplicate==0)
        	{
        
        		service.createOrUpdate(client);
			
        		message="New client was created successfully...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new client in the system. User ID is "+ client.getCname());
        		log.setObject(client.getEmail());
        		serviceLogs.saveLog(log);
		
        	}
        	else
        	{
        		message="Error: duplicate client found, new record was not created at this time. Please review the list of clients again...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new item due to duplicity: "+ client.getCname());
        		log.setObject(client.getEmail());
        		serviceLogs.saveLog(log);
			
        	}	
        
        }	
		
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "clientsRedirect";
		
		
	}
	
	
	@RequestMapping(path="/search", method=RequestMethod.POST)
	public String search(Model model, Long quserId)
	{
		//Preparing list of projects
		List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("projects",projects);
		
		return "searchFormClients";
		
		
	}
	
	
	@RequestMapping(path="/findByName", method=RequestMethod.POST)
	public String findClientByName(Model model,String stringSearch,Long quserId)
	{
		//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
		
		List<ClientsEntity> list=service.searchClientsByName(stringSearch);
		
		//System.out.println(list);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("clients",list);
		model.addAttribute("stringSearch",stringSearch);
		
		return "clientsList";
		
		
	}
	
	@RequestMapping(path="/findByProgram", method=RequestMethod.POST)
	public String findClientsByProjectName(Model model,String stringSearch,Long quserId)
	{
		//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
		
		List<ClientsEntity> list=service.searchClientsByProgram(stringSearch);
		
		//System.out.println(list);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("clients",list);
		model.addAttribute("stringSearch",stringSearch);
		
		return "clientsList";
			
	}
	
	@RequestMapping(path="/views")
	public String views(Model model)
	{
		List<HhsDivisionsEntity> divisions=serviceHhsDivisions.getAllByName();
		
		//System.out.println("Inside the views form");
		
		model.addAttribute("divisions",divisions);
		
		return "viewsForClients";
		
		
	}
	
	@RequestMapping(path="/viewVaxStatus", method=RequestMethod.POST)
	public String viewVaxStatus(Model model,String division,String vacStatus)
	{
		
		String hhsDivision=null;
		
		List<ClientsEntity> list=service.viewVaxStatus(division,vacStatus);
		
		hhsDivision=serviceHhsDivisions.getDivisionByNumber(division);
		
		//System.out.println(list);
		
		model.addAttribute("clients",list);
		model.addAttribute("hhsDivision",hhsDivision);
		model.addAttribute("vacStatus",vacStatus);
		
		return "viewVaxStatus";
			
	}

}
