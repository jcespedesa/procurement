package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.DivisionsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.SitesEntity;
import com.trc.services.DivisionsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;



@Controller
@RequestMapping("procurement/Projects")
public class ProjectsController 
{
	@Autowired
	ProjectsService service;
	
	@Autowired
	SitesService serviceSites;
	
	@Autowired
	DivisionsService serviceDivisions;
	
	//CRUD operations for projects
	
	@GetMapping("/list")
	public String getAllProjects(Model model)
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
		
		model.addAttribute("projects",list);
			
		return "projectsList";
			
			
	}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
		public String editProjectsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
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
			
			model.addAttribute("sites",list);
			model.addAttribute("divisions",listDivisions);
			model.addAttribute("udelnyBeses",listUB);
			
			return "projectsAddEdit";
		}
		
		@RequestMapping(path="/delete/{id}")
		public String deleteProjectById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
		{
			
			service.deleteProjectById(id);
			
			return "redirect:/procurement/Projects/list";
			
		}
		
		@RequestMapping(path="/createProject", method=RequestMethod.POST)
		public String createOrUpdateProject(ProjectsEntity project)
		{
			//System.out.println("Inside the controller to update or create. Object is: "+ project);
			
			service.createOrUpdate(project);
			
			return "redirect:/procurement/Projects/list";
			
			
		}
		
		@RequestMapping(path="/search")
		public String search()
		{
			//System.out.println("Inside the search form");
			
			return "searchFormProjects";
			
			
		}
		
		@RequestMapping(path="/findProjectNum", method=RequestMethod.POST)
		public String findProjectNum(Model model,String stringSearch)
		{
			//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
			
			List<ProjectsEntity> list=service.searchProjectsByNum(stringSearch);
			
			//System.out.println(list);
			
			model.addAttribute("projects",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "projectsSearchList";
			
			
		}
		
		@RequestMapping(path="/findProjectName", method=RequestMethod.POST)
		public String findProjectName(Model model, String stringSearch)
		{
			//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
			
			List<ProjectsEntity> list=service.searchProjectsByName(stringSearch);
			
			//System.out.println(list);
			
			model.addAttribute("projects",list);
			model.addAttribute("stringSearch",stringSearch);
			
			return "projectsSearchList";
			
			
		}
		
		@GetMapping("/hhsView")
		public String hhsViewForm(Model model)
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
			
			model.addAttribute("projects",list);
				
			return "projectsList";
				
				
		}
	
}
