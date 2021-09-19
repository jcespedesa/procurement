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

import com.trc.entities.TitlesEntity;
import com.trc.services.RecordNotFoundException;
import com.trc.services.TitlesService;

@Controller
@RequestMapping("/procurement/Titles")
public class TitlesController 
{
	@Autowired
	TitlesService service;
	
	//CRUD operations for titles
	
	@GetMapping("/list")
	public String getAllTitles(Model model)
	{
				
		List<TitlesEntity> list=service.getAllByName();
		
		model.addAttribute("titles",list);
		
		return "titlesList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editTitlesById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
	{
		
		if(id.isPresent())
		{
			TitlesEntity entity=service.getTitleById(id.get());
			model.addAttribute("title",entity);
		}
		else
		{
			model.addAttribute("title",new TitlesEntity());
			
		}
		return "titlesAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteTitleById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deleteTitleById(id);
		
		return "redirect:/procurement/Titles/list";
		
	}
	
	@RequestMapping(path="/createTitle", method=RequestMethod.POST)
	public String createOrUpdateTitle(TitlesEntity title)
	{
				
		service.createOrUpdate(title);
		
		return "redirect:/procurement/Titles/list";
		
		
	}
}
