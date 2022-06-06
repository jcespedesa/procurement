package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.entities.AssetAssigEntity;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ClientsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.AssetsAssigService;
import com.trc.services.AssetsService;
import com.trc.services.ClientsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement/assetsAssig")
public class AssetsAssigController 
{

	@Autowired
	AssetsAssigService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	AssetsService serviceAssets;
	
	@Autowired
	ClientsService serviceClients;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAssigsByAssetId(Model model,String assetId,String stringSearch,String priznak, Long quserId) throws RecordNotFoundException
	{
		List<AssetAssigEntity> list=service.getAssigById(assetId);
		
		AssetsEntity asset=new AssetsEntity();
		ClientsEntity oldUser=new ClientsEntity();
		ClientsEntity newUser=new ClientsEntity();
		
		Long assigId=null;
		Long oldUserId=null;
		Long newUserId=null;
				
		for(AssetAssigEntity entity : list)
		{
			assigId=Long.parseLong(entity.getAssetId());
			oldUserId=Long.parseLong(entity.getAssigId());
			newUserId=Long.parseLong(entity.getNewAssigId());
			
			asset=serviceAssets.getAssetById(assigId);
			oldUser=serviceClients.getClientById(oldUserId);
			newUser=serviceClients.getClientById(newUserId);
			
			entity.setAssetNumber(asset.getAssetNumber());
			entity.setAssigName(oldUser.getCname());
			entity.setNewAssigName(newUser.getCname());
				
		}
		
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
