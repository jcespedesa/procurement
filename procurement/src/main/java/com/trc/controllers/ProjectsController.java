package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DivisionsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;
import com.trc.services.UsersService;



@Controller
@RequestMapping("procurement/projects")
public class ProjectsController 
{
	@Autowired
	ProjectsService service;
	
	@Autowired
	SitesService serviceSites;
	
	@Autowired
	DivisionsService serviceDivisions;
	
	@Autowired
	UsersService serviceUsers;
	
	//CRUD operations for projects
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllProjects(Model model, Long quserId)
	{
				
		List<ProjectsEntity> list=service.getAllByProject();
		
		String localDivision=null;
		String localSite=null;
		
		
		//Trying to get divisions and sites name
		
		for(ProjectsEntity dname : list)
        {
        	localDivision=serviceDivisions.getDivisionByNumber(dname.getDepartment());
        	
        	dname.setBuffer1(localDivision);
        }
		
		for(ProjectsEntity dsite : list)
        {
        	localSite=serviceSites.getSiteByNumber(dsite.getSiteNumber1());
        	
        	dsite.setBuffer2(localSite);
        }
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("projects",list);
			
		return "projectsList";
			
			
	}
		
	@RequestMapping(path="/edit", method=RequestMethod.POST)
		public String editProjectsById(Model model,Optional<Long> id,  Long quserId) throws RecordNotFoundException 
		{
			
			String department="300";
			
			List<SitesEntity> list=serviceSites.getAllByName();
			List<DivisionsEntity> listDivisions=serviceDivisions.getAllByName();
			List<Integer> listUB=service.findNonRepeatedUB(department);
			
			String site1="";
			String site2="";
			String division="";
			
			if(id.isPresent())
			{
				ProjectsEntity entity=service.getProjectById(id.get());
				model.addAttribute("project",entity);
				
				site1=service.getSiteBySiteNumber1(entity.getSiteNumber1());
				site2=service.getSiteBySiteNumber2(entity.getSiteNumber2());
				
				division=service.getDivisionByDivisionNumber(entity.getSiteNumber1());
				
				model.addAttribute("site1",site1);
				model.addAttribute("site2",site2);
				model.addAttribute("division",division);
				
			}
			else
			{
				model.addAttribute("project",new ProjectsEntity());
				
			}
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("sites",list);
			model.addAttribute("divisions",listDivisions);
			model.addAttribute("udelnyBeses",listUB);
			
			return "projectsAddEdit";
		}
		
	
		@RequestMapping(path="/delete", method=RequestMethod.POST)
		public String deleteProjectById(Model model, Long id,  Long quserId) throws RecordNotFoundException
		{
			String message=null;
			
			service.deleteProjectById(id);
			
			message="Item was removed...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "projectsRedirect";
			
		}
		
		@RequestMapping(path="/createProject", method=RequestMethod.POST)
		public String createOrUpdateProject(Model model, ProjectsEntity project,  Long quserId)
		{
			String message=null;
			
			service.createOrUpdate(project);
			
			message="List was updated successfully...";
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("message",message);
			
			return "projectsRedirect";
			
			
		}
		
		@RequestMapping(path="/search", method=RequestMethod.POST)
		public String search(Model model,  Long quserId)
		{
			//System.out.println("Inside the search form");
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			return "searchFormProjects";
			
			
		}
		
		@RequestMapping(path="/findProjectNum", method=RequestMethod.POST)
		public String findProjectNum(Model model,String stringSearch,  Long quserId)
		{
			//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
			
			List<ProjectsEntity> list=service.searchProjectsByNum(stringSearch);
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("projects",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "projectsSearchList";
			
			
		}
		
		@RequestMapping(path="/findProjectName", method=RequestMethod.POST)
		public String findProjectName(Model model, String stringSearch,  Long quserId)
		{
			//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
			
			List<ProjectsEntity> list=service.searchProjectsByName(stringSearch);
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("projects",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "projectsSearchList";
			
			
		}
		
		
		@RequestMapping(path="/hhsView", method=RequestMethod.POST)
		public String hhsViewForm(Model model,  Long quserId)
		{
					
			List<ProjectsEntity> list=service.getHhsFormView();
			
			String localDivision=null;
			String localSite=null;
			
			
			//Trying to get divisions and sites name
			
			for(ProjectsEntity dname : list)
	        {
	        	localDivision=serviceDivisions.getDivisionByNumber(dname.getDepartment());
	        	
	        	dname.setBuffer1(localDivision);
	        }
			
			for(ProjectsEntity dsite : list)
	        {
	        	localSite=serviceSites.getSiteByNumber(dsite.getSiteNumber1());
	        	
	        	dsite.setBuffer2(localSite);
	        }
			
			//Retrieving user identity
	        UsersEntity quser=serviceUsers.getUserById(quserId);
	        
	        model.addAttribute("quserId",quserId);
			model.addAttribute("quser",quser);
			
			model.addAttribute("projects",list);
				
			return "projectsList";
				
				
		}
	
}
