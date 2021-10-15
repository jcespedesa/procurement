package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.entities.AssetAssigEntity;
import com.trc.services.AssetsAssigService;


@Controller
@RequestMapping("/procurement/assetsAssig")
public class AssetsAssigController 
{

	@Autowired
	AssetsAssigService service;
	
	@GetMapping("/list/{id}")
	public String getAssigsByAssetId(Model model, @PathVariable("id") String id)
	{
		List<AssetAssigEntity> list=service.getAssigById(id);
					
		model.addAttribute("assigs",list);
		model.addAttribute("assetId",id);
		
		return "assigsView";
		
		
	}
	
}
