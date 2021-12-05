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

import com.trc.entities.ClientsEntity;
import com.trc.entities.HhsDivisionsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.services.ClientsService;
import com.trc.services.HhsDivisionsService;
import com.trc.services.ProjectsService;
import com.trc.services.RecordNotFoundException;


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
	
	//CRUD operations for Clients
	
	@GetMapping("/list")
	public String getAllClients(Model model)
	{
		List<ClientsEntity> list=service.getAllClientsAlphab();
		
		String projectNumber=null;
		String projectName=null;
		
		for(ClientsEntity client : list)
		{
			
			//finding project name
			projectNumber=client.getProjectNumber();
			projectName=serviceProjects.getProjectByNum(projectNumber);
			
			client.setProgram(projectName);
			
		}
		
		
				
		model.addAttribute("clients",list);
		
		return "clientsList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editClientsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
	{
		
		//Preparing list of projects
		List<ProjectsEntity> projects=serviceProjects.getAllHHSprojects();
		
		if(id.isPresent())
		{
			ClientsEntity entity=service.getClientById(id.get());
			
			String project=null;
			
			//Retrieving project description
			project=serviceProjects.getSiteBySiteNumber1(entity.getProjectNumber());
			
			entity.setProgram(project);
			
			model.addAttribute("client",entity);
		}
		else
		{
			model.addAttribute("client",new ClientsEntity());
			
		}
		
		model.addAttribute("projects",projects);
		
		return "clientsAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteClientById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteClientById(id);
		
		return "redirect:/procurement/clients/list";
		
	}
	
	@RequestMapping(path="/createClient", method=RequestMethod.POST)
	public String createOrUpdateClient(ClientsEntity client)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ client);
		
		service.createOrUpdate(client);
		
		return "redirect:/procurement/clients/list";
		
		
	}
	
	
	@RequestMapping(path="/search")
	public String search()
	{
		//System.out.println("Inside the search form");
		
		return "searchFormClients";
		
		
	}
	
	
	@RequestMapping(path="/findByName", method=RequestMethod.POST)
	public String findClientByName(Model model,String stringSearch)
	{
		//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
		
		List<ClientsEntity> list=service.searchClientsByName(stringSearch);
		
		//System.out.println(list);
		
		model.addAttribute("clients",list);
		model.addAttribute("stringSearch",stringSearch);
		
		return "clientsList";
		
		
	}
	
	@RequestMapping(path="/findByProgram", method=RequestMethod.POST)
	public String findClientsByProjectName(Model model,String stringSearch)
	{
		//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
		
		List<ClientsEntity> list=service.searchClientsByProgram(stringSearch);
		
		//System.out.println(list);
		
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
