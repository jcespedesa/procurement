package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.ProjectAssigEntity;
import com.trc.entities.SectionAssigEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DivisionsService;
import com.trc.services.LogsService;
import com.trc.services.ProjectsAssigService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SectionsAssigService;
import com.trc.services.SectionsService;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/users")
public class UsersController 
{
	@Autowired
	UsersService service;
		
	@Autowired
	DivisionsService serviceDivisions;
	
	@Autowired
	LogsService serviceLogs;
	
	@Autowired
	SectionsAssigService serviceSectionsAssig;
	
	@Autowired
	ProjectsAssigService serviceProjectsAssig;
	
	@Autowired
	SectionsService serviceSections;
	
	@Autowired
	ProjectsService serviceProjects;
	
		
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllUsers(Model model, Long quserId)
	{
		
		List<UsersEntity> list=service.getAllUsers();
		
		
		//Finding assigned roles
		for(UsersEntity user : list)
		{
			if(user.getPriznakFOapprover()!=null)
			{	
				if(user.getPriznakFOapprover().equals("Yes"))
					user.setAssignedRole("FO Approver");
			}
			
			if(user.getPriznakITapprover()!=null)
			{	
				if(user.getPriznakITapprover().equals("Yes"))
					user.setAssignedRole("IT Approver");
			}
			if(user.getPriznakPpc()!=null)
			{	
				if(user.getPriznakPpc().equals("Yes"))
					user.setAssignedRole("Assigned PPC");
			}
			
		}
		
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("users",list);
		
		return "usersList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editUsersById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		String userIdString=null;
		
		String sectionNumber=null;
		String sectionName=null;
		
		String projectNumber=null;
		String projectName=null;
				
		//Preparing list of divisions
		List<DivisionsEntity> divisions=serviceDivisions.getAllDivisions();
		
		if(id.isPresent())
		{
			UsersEntity entity=service.getUserById(id.get());
			
			userIdString=String.valueOf(id.get());
			
			//Retrieving the list of assigned sections
			List<SectionAssigEntity> sections=serviceSectionsAssig.getAssigById(userIdString);
			
						
			for(SectionAssigEntity section : sections)
			{
					
				//finding section name
				sectionNumber=section.getAssigSectionNumber();
				sectionName=serviceSections.getSectionByNumber(sectionNumber);
				
				section.setUsername(sectionName);
			}
			
			//Retrieving the list of assigned projects
			List<ProjectAssigEntity> projects=serviceProjectsAssig.getAssigById(userIdString);
			
						
			for(ProjectAssigEntity project : projects)
			{
					
				//finding project name
				projectNumber=project.getAssigProjectNumber();
				projectName=serviceProjects.getProjectByNum(projectNumber);
				
				project.setUsername(projectName);
			}	
			
			model.addAttribute("projects",projects);						
			model.addAttribute("sections",sections);
			model.addAttribute("user",entity);
		}
		else
		{
			model.addAttribute("user",new UsersEntity());
			
		}
		
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("divisions",divisions);
		
		return "usersAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteUserById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving user identity
        UsersEntity user=service.getUserById(id);
		
		service.deleteUserById(id);
		
		message="User was deleted...";
		
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
      //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Deleting an user from the database. User ID was "+ user.getUserid());
      	log.setObject("User description was: "+ user.getUsername() +" and role was "+ user.getRole());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "usersRedirect";
		
	}
	
	@RequestMapping(path="/createUser", method=RequestMethod.POST)
	public String createOrUpdateUser(Model model, UsersEntity user, Long quserId)
	{
		int priznakDuplicate=0;
		
		String message=null;
		String localUser=null;
		
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
        if(user.getUserid()!=null)
        {
        	//Modify the record
		
        	service.createOrUpdate(user);
		
        	message="User was successfully updated...";
		        
        	//Processing logs
	      	LogsEntity log=new LogsEntity();
	      	log.setSubject(quser.getEmail());
	      	log.setAction("Modifying user information in the database. User ID was "+ user.getUserid());
	      	log.setObject("User description is: "+ user.getUsername() +" and role is "+ user.getRole());
	      	serviceLogs.saveLog(log);
        }
        else
        {
        	//Creating a new record
        	
        	//Checking if the email is not already in the system
        	localUser=user.getEmail();
        	priznakDuplicate=service.findDuplicates(localUser);
		
        	if(priznakDuplicate==0)
        	{
        
        		user.setPriznakFOapprover("No");
        		user.setPriznakITapprover("No");
        		user.setPriznakPpc("No");
        		
        		service.createOrUpdate(user);
			
        		message="New user was created successfully...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new user in the system. User ID is "+ user.getUsername());
        		log.setObject(user.getEmail());
        		serviceLogs.saveLog(log);
		
        	}
        	else
        	{
        		message="Error: duplicate user found, new record was not created at this time. Please review the list of users again...";
			
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new item due to duplicity: "+ user.getUsername());
        		log.setObject(user.getEmail());
        		serviceLogs.saveLog(log);
			
        	}	
        
        }	
                
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "usersRedirect";
		
		
	}
	
	
	@RequestMapping(path="/resetPass", method=RequestMethod.POST)
    public String resetPassById(Model model, Long id, Long quserId) throws RecordNotFoundException 
    {
    	UsersEntity entity=service.getUserById(id);
    	
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
				
        model.addAttribute("user",entity);
                
        return "usersPassReset";
    }
	
	@RequestMapping(path="/changePass", method=RequestMethod.POST)
    public String changePass(Model model, Long id, Long quserId) throws RecordNotFoundException 
    {
		String message=null;
		
		//Retrieving user identity
        UsersEntity user=service.getUserById(id);
		
    	service.resetPass(id);
    	
    	message="Password was reset...";
    	    	   	
    	//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
        //Processing logs
      	LogsEntity log=new LogsEntity();
      	log.setSubject(quser.getEmail());
      	log.setAction("Reseting password for user in the database. User ID was "+ user.getUserid());
      	log.setObject("User description is: "+ user.getUsername() +" and role is "+ user.getRole());
      	serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
    	
    	model.addAttribute("message",message);
    	
    	return "usersRedirect";
    }
	
	@RequestMapping(path="/setPass", method=RequestMethod.POST)
    public String setPass(Model model,Long id, String newPass, Long quserId) throws RecordNotFoundException 
    {
		String message=null;
		String priznak=null;
		
		LogsEntity log=new LogsEntity();
		
		//Retrieving target user identity
        UsersEntity user=service.getUserById(id);
		
    	//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
		
		//Checking for at least one capital letter and one number
		priznak=service.checkString(newPass);
		    	
    	if(priznak.equals("false"))
    	{
    		message="The password is invalid. No changes were made this time. Please try again...";
    		
    		//Processing logs
          	log.setSubject(quser.getEmail());
          	log.setAction("Failing to set up a new password for user in the database. User ID is "+ user.getUserid());
          	log.setObject("User description is: "+ user.getUsername() +" and role is "+ user.getRole());
    		
    	}
    	else
    	{
    		service.setPass(id,newPass);
    		message="Password was set...";
    		
    		//Processing logs
          	log.setSubject(quser.getEmail());
          	log.setAction("Setting up a new password for user in the database. User ID is "+ user.getUserid());
          	log.setObject("User description is: "+ user.getUsername() +" and role is "+ user.getRole());
          	
    	}
    	serviceLogs.saveLog(log);
    	
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
    	
    	model.addAttribute("message",message);
    	
    	return "usersRedirect";
    }
	
	@RequestMapping(path="/advancedPerms", method=RequestMethod.POST)
    public String advancedPermissions(Model model, Long userId, Long quserId) throws RecordNotFoundException 
    {
		UsersEntity itApprover;
    	UsersEntity foApprover;
    	UsersEntity ppc;
    	
    	String priznakUniqueIT=null;
    	String priznakUniqueFO=null;
    	String priznakUniquePPC=null;
		
		UsersEntity entity=service.getUserById(userId);
    	    	    	
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
                
        //Retrieving approvers entities
        itApprover=service.getITapprover();
        foApprover=service.getFOapprover();
        ppc=service.getPpc();
        
        //Checking if these roles are already taken
        priznakUniqueIT=service.checkUniqueITrole();
        priznakUniqueFO=service.checkUniqueFOrole();
        priznakUniquePPC=service.checkUniquePPCrole();
        
        model.addAttribute("itApprover",itApprover);
        model.addAttribute("foApprover",foApprover);
        model.addAttribute("ppc",ppc);
        
        model.addAttribute("priznakUniqueIT",priznakUniqueIT);
        model.addAttribute("priznakUniqueFO",priznakUniqueFO);
        model.addAttribute("priznakUniquePPC",priznakUniquePPC);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
				
        model.addAttribute("user",entity);
                
        return "usersAdvPerms";
    }
	
	@RequestMapping(path="/advancedPermsSet", method=RequestMethod.POST)
    public String advancedPermsSet(Model model,Long userId,Long quserId,String priznakITapprover,String priznakFOapprover,String priznakPpc)
    {
    	String message=null;
		
		UsersEntity entity=service.getUserById(userId);
		LogsEntity log=new LogsEntity();
    	    	    	
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
      //Retrieving target user identity
        UsersEntity user=service.getUserById(userId);
               
        //Updating records in user's profile
        message=service.updateRoles(userId,priznakITapprover,priznakFOapprover,priznakPpc);
        
      //Processing logs
      	log.setSubject(quser.getEmail());
      	log.setAction("Assigning up a new role for user ID: "+ user.getUserid());
      	log.setObject("User description is: "+ user.getUsername() +" and role is "+ user.getRole());
                
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
				
        model.addAttribute("user",entity);
        model.addAttribute("message",message);
        
        return "usersRedirect";
    }
	
	@RequestMapping(path="/demote", method=RequestMethod.POST)
    public String demoteById(Model model, Long userId, Long quserId, String priznakRole) 
    {
    	
		String message=service.demoteById(userId,priznakRole);
		
		//Retrieving user identity
        UsersEntity quser=service.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
				
        model.addAttribute("message",message);
                
        return "usersRedirect";
    }
	
}
