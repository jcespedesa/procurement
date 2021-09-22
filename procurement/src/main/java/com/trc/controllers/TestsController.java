package com.trc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ReceiptsEntity;
import com.trc.services.EmailService;
import com.trc.services.ProjectsService;
import com.trc.services.ReceiptsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.TestsService;
import com.trc.services.TitlesService;


@Controller
@RequestMapping("/procurement/tests")
public class TestsController 
{
	@Autowired
	TestsService service;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	TitlesService serviceTitles;
	
	@Autowired
	ReceiptsService serviceReceipts;
	
	@GetMapping("/emailSel")
	public String testEmailSel()
	{
			
		return "testEmailSel";
			
	}
	
	@GetMapping("/sendAssetInfoSel")
	public String testEmailObjectSel()
	{
			
		return "testObjectEmailSel";
			
	}
	
	@RequestMapping(path="/testEmailSending", method=RequestMethod.POST)
	public String testEmail(Model model, String toEmail, String body, String subject)
	{
				
		//Sending the test email
		emailService.sendMail(toEmail,subject,body);
		
		model.addAttribute("toEmail",toEmail);
		
		return "testEmailRedirect";
		
	}
	
	@RequestMapping(path="/sendingAssetInfo", method=RequestMethod.POST)
	public String sendingAsset(Model model, String toEmail, long assetId) throws RecordNotFoundException, JsonProcessingException
	{
		
		String description="Receipt confirmation for IT Asset Inventory Input";
		
		String assetIdString=null;
		String program=null;
		String titleName=null;
		
		//Retrieving asset information	
		AssetsEntity asset=service.getAssetById(assetId );
		
		//Converting assetId from long to string
		assetIdString=Long.toString(asset.getAssetid());
		
		//Retrieving program name
		program=serviceProjects.getProjectByNum(asset.getProject());
				
		//Retrieving title name
		titleName=serviceTitles.getTitleByNumber(asset.getTitle());
				
		//Creating new receipt object to be sent
		ReceiptsEntity receipt=new ReceiptsEntity();
				
		//Adapting asset information for the receipt 
		receipt.setDescription(description);
		receipt.setAssetId(assetIdString);
		receipt.setDateReceipt(asset.getDateCreation());
		receipt.setCname(asset.getUsername());
		receipt.setEmail(asset.getEmail());
		receipt.setTitle(titleName);
		receipt.setEmpStatus(asset.getEmpStatus());
		receipt.setProgram(program);
		receipt.setProjectNum(asset.getProject());
		receipt.setSignedBy(asset.getAuthor());
		receipt.setSemail(asset.getAuthorEmail());
		receipt.setNotes(asset.getNotes());
		
		//Saving the receipt
		ReceiptsEntity newReceipt=serviceReceipts.create(receipt);
						
		//Sending the test email  
		emailService.sendMailObject(toEmail,description,newReceipt);
		
		model.addAttribute("toEmail",toEmail);
		
		return "testEmailRedirect";	
	}
	
	
	
}
