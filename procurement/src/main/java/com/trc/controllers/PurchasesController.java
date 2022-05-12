package com.trc.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.ItemsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.PurchasesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.ItemsService;
import com.trc.services.LogsService;
import com.trc.services.ProjectsService;
import com.trc.services.PurchasesService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/purchases")
public class PurchasesController 
{
	@Autowired
	LogsService serviceLogs;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	PurchasesService service;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	ItemsService serviceItems;
	
	@GetMapping("/mainMenu")
	public String getAllQuotes(Model model,Long quserId)
	{
		//Retrieving approvers information
		UsersEntity ITapprover=serviceUsers.getITapprover();
		UsersEntity FOapprover=serviceUsers.getFOapprover();
		UsersEntity Ppc=serviceUsers.getPpc();
				
		//Retrieving user information
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		model.addAttribute("ITapprover",ITapprover);
		model.addAttribute("FOapprover",FOapprover);
		model.addAttribute("Ppc",Ppc);
										
		model.addAttribute("quser",quser);
		model.addAttribute("quserId",quserId);
		
		return "purchMainMenu";
		
	}
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllPurchases(Model model, Long quserId)
	{
				
		List<PurchasesEntity> list=service.getAllByDate();
						
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("purchases",list);
			
		return "purchasesList";
			
			
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String newPurchase(Model model, Long quserId,Optional<Long> id) throws RecordNotFoundException
	{
		
		
		if(id.isPresent())
		{
			PurchasesEntity entity=service.getPurchaseById(id.get());
			model.addAttribute("purchase",entity);
		}
		else
		{
			//Generating a random value for record identification
			Random r=new Random();
			int seed=r.nextInt();
			
			model.addAttribute("seed",seed);
			
			//Creating the object
			model.addAttribute("purchase",new PurchasesEntity());
			
		}
		
		//Preparing list of projects
		List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
		
		//Preparing list of items
		List<ItemsEntity> items=serviceItems.getAllItemsListDesc();
		
		//Preparing quantities 
		List<Integer> quantities=service.getArrayIntegers(10);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //System.out.println(quantities);
				
		model.addAttribute("items",items);		
		model.addAttribute("projects",projects);
		model.addAttribute("quantities",quantities);
		
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "purchAddEdit";
			
			
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteRequestById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving request identity
        PurchasesEntity request=service.getPurchaseById(id);
		
		service.deletePurchaseById(id);
		
		message="Purchase was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Deleting Purchase Request record from the database. Request number was "+ request.getReqid());
      	log.setObject(request.getItemNumber());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "purchRedirect";
		
	}
	
	@RequestMapping(path="/retrieve", method=RequestMethod.POST)
	public String retrieveRequestById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving request identity
        PurchasesEntity request=service.getPurchaseById(id);
		
		service.retrievePurchaseById(id);
		
		message="Purchase was retrieved. All approvals were removed...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Retrieving Purchase Request record from the chain of approvals. Request number was "+ request.getReqid());
      	log.setObject(request.getItemNumber());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "purchRedirect";
		
	}
	
	@RequestMapping(path="/createRequest", method=RequestMethod.POST)
	public String createOrUpdateRequest(Model model, PurchasesEntity request, Long quserId)
	{
				
		String message=null;
		String requesterId=null;
		
		//Converting long id to string
		requesterId=String.valueOf(quserId);
		
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
		
        if(request.getReqid()!=null)
        {
        	//Modify the record
        
        	service.createOrUpdate(request,requesterId);
		
        	message="Record was successful updated...";
		
			//Processing logs
	      	LogsEntity log=new LogsEntity();
	      	log.setSubject(quser.getEmail());
	      	log.setAction("Modifying Purchase Request in the database. Request ID was "+ request.getReqid());
	      	log.setObject(request.getProjectNumber());
	      	serviceLogs.saveLog(log);
	        
        }
        else
        {
        	//Creating a new record
        	
        	service.createOrUpdate(request,requesterId);
				
        	message="New purchase request was created successfully...";
        		
		   	//Processing logs
        	LogsEntity log=new LogsEntity();
        	log.setSubject(quser.getEmail());
        	log.setAction("Creating new purchase request in the database. Section ID is "+ request.getReqid());
        	log.setObject(request.getProjectNumber());
        	serviceLogs.saveLog(log);
        	        	
        }
	      	
	    model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
			
		model.addAttribute("message",message);
		
		return "purchRedirect";
		
	}
	
}
