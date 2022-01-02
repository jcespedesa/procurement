package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.AssetAssigEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.AssetsAssigService;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement/assetsAssig")
public class AssetsAssigController 
{

	@Autowired
	AssetsAssigService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAssigsByAssetId(Model model,String assetId,String stringSearch,String priznak, Long quserId)
	{
		List<AssetAssigEntity> list=service.getAssigById(assetId);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
					
		model.addAttribute("assigs",list);
		model.addAttribute("assetId",assetId);
		
		model.addAttribute("stringSearch",stringSearch);
		model.addAttribute("priznak",priznak);
		
		return "assigsView";
		
		
	}
	
}
