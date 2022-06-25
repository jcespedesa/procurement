package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.LogsEntity;
import com.trc.entities.ProjectAssigEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.LogsService;
import com.trc.services.ProjectsAssigService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/procurement/projectsAssig")
public class ProjectsAssigController 
{
	@Autowired
	ProjectsAssigService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	LogsService serviceLogs;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAssigsByAssetId(Model model,String userId,Long quserId)
	{
		Long userIdLong=null;
		
		String projectNumber=null;
		String projectName=null;
		
		userIdLong=Long.parseLong(userId);
		
		List<ProjectAssigEntity> list=service.getAssigById(userId);
		
		for(ProjectAssigEntity project : list)
		{
				
			//finding project name
			projectNumber=project.getAssigProjectNumber();
			projectName=serviceProjects.getProjectByNum(projectNumber);
			
			project.setUsername(projectName);
		}	
		
		//Retrieving user data
        UsersEntity user=serviceUsers.getUserById(userIdLong);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("projects",list);
		model.addAttribute("userId",userId);
		model.addAttribute("user",user);
			
		return "projectsAssigView";
		
		
	}
	
	@RequestMapping(path="/add", method=RequestMethod.POST)
	public String assigProjects(Model model,String userId,Long quserId)
	{
		Long userIdLong=null;
					
		userIdLong=Long.parseLong(userId);
		
		List<ProjectsEntity> list=serviceProjects.getAllHHSactiveProjects();
		
		//Retrieving user data
        UsersEntity user=serviceUsers.getUserById(userIdLong);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Creating the object for the form
        model.addAttribute("assigProject",new ProjectAssigEntity());
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("projects",list);
		model.addAttribute("userId",userId);
		model.addAttribute("user",user);
			
		return "projectsAssigAdd";
		
	}

	@RequestMapping(path="/addProject", method=RequestMethod.POST)
	public String addProject(Model model,String userId,Long quserId,ProjectAssigEntity newProject)
	{
		
		int priznakDuplicate=0;
		
		String message=null;
		
			
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Checking if the section is not already in added for this user
    	priznakDuplicate=service.findDuplicates(userId,newProject.getAssigProjectNumber());
    	
    	if(priznakDuplicate==0)
    	{
    		//Creating the new assignation
    		service.createAssig(newProject);
		
    		message="New Project was assignated successfully...";
		
    		//Processing logs
    		LogsEntity log=new LogsEntity();
    		log.setSubject(quser.getEmail());
    		log.setAction("Assigning new project for the user. User ID is "+ userId);
    		log.setObject("And section ID is: "+ newProject.getProjectid());
    		serviceLogs.saveLog(log);
	
    	}
    	else
    		message="Selected Project is already assigned to this user. No changes were made this time...";
                        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("message",message);
		model.addAttribute("userId",userId);
					
		return "projectsAssigRedirect";
		
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String removeSection(Model model,String userId,Long quserId,String projectNumber) throws RecordNotFoundException
	{
		String message="Section was removed successfully...";
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Retrieving project identity
        String project=serviceProjects.getProjectByNum(projectNumber);
        
        //Removing assigned section
        service.deleteProjectAssigByNumber(projectNumber);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Removing assigned section for the user. User ID is "+ userId);
		log.setObject("And section ID is: "+ project);
		serviceLogs.saveLog(log);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("message",message);
		model.addAttribute("userId",userId);
		
		return "projectsAssigRedirect";
		
	}

}
