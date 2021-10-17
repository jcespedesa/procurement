package com.trc.controllers;


import java.time.LocalDate;
import java.time.Period;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ReceiptsEntity;
import com.trc.repositories.SettingsRepository;
import com.trc.services.EmailService;
import com.trc.services.ProjectsService;
import com.trc.services.ReceiptsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.TestsService;
import com.trc.services.TitlesService;
import com.trc.entities.SettingsEntity;



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
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	SettingsRepository repositorySettings;
	
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
	
	
	@GetMapping("/findAgeTwoDates")
	public String findAgeTwoDatesSel()
	{
			
		return "testFindAgeTwoDatesSel";
			
	}
	
	@GetMapping("/ezOffAPIquerySel")
	public String ezOfficeAPIquerySel(Model model) throws RecordNotFoundException
	{
		
		//Retrieving setting
		SettingsEntity setting=service.getSettingBySname("APIezOffice");
		
		String apiUrl=null;
		String token=null;
		String prefix=null;
		
			
		//Retrieving the url from parameters
		apiUrl=setting.getPath();
		prefix=setting.getParam1();
		token=setting.getParam2();
		
				
		model.addAttribute("apiUrl",apiUrl);
		model.addAttribute("prefix",prefix);
		model.addAttribute("token",token);
		
		return "ezOfficeAPIquerySel";
			
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
		
				
		//Retrieving asset information	
		AssetsEntity asset=service.getAssetById(assetId );
		
		//Creating new receipt object to be sent
		ReceiptsEntity receipt=new ReceiptsEntity();
				
		//Adapting asset information for the receipt 
		receipt.setDescription(description);
		receipt.setDateReceipt(asset.getDateCreation());
		receipt.setSignedBy(asset.getAuthor());
		receipt.setSemail(asset.getAuthorEmail());
		receipt.setNotes(asset.getNotes());
		receipt.setKluch(asset.getKluch());
		
		//Saving the receipt
		ReceiptsEntity newReceipt=serviceReceipts.create(receipt);
						
		//Sending the test email  
		emailService.sendMailObject(toEmail,description,newReceipt);
		
		model.addAttribute("toEmail",toEmail);
		
		return "testEmailRedirect";	
	}
	
	@RequestMapping(path="/testFindAgeTwoDates", method=RequestMethod.POST)
	public String testFindAgeTwoDates(Model model, String datePurchased, String todayDate)
	{
		int age=0;
		
		int year=0;
		int month=0;
		int day=0;
		
		String yearString=null;
		String monthString=null;
		String dayString=null;
		
		monthString=datePurchased.substring(0,datePurchased.indexOf("/"));
		dayString=datePurchased.substring(datePurchased.indexOf("/")+1,5);
		yearString=datePurchased.substring(6,datePurchased.length());
		
		year=Integer.parseInt(yearString);
		month=Integer.parseInt(monthString);
		day=Integer.parseInt(dayString);
		
		LocalDate datePurchasedLocal=LocalDate.of(year,month,day);
		
		monthString=todayDate.substring(0,datePurchased.indexOf("/"));
		dayString=todayDate.substring(datePurchased.indexOf("/")+1,5);
		yearString=todayDate.substring(6,datePurchased.length());
		
		year=Integer.parseInt(yearString);
		month=Integer.parseInt(monthString);
		day=Integer.parseInt(dayString);
		
		LocalDate todayDateLocal=LocalDate.of(year,month,day);
				
		//finding the age
		age=Period.between(datePurchasedLocal,todayDateLocal).getYears();
		
		model.addAttribute("datePurchased",datePurchased);
		model.addAttribute("todayDate",todayDate);
		model.addAttribute("age",age);
		
		return "testAgeTwoDatesRedirect";
		
	}
	
	@RequestMapping(path="/testAPIqueryToEZoffice", method=RequestMethod.POST)
	public Object testAPIqueryToEZoffice(Model model,String apiUrl,String prefix,String token,String assetNumber)
	{
				
		String url=null;
		String questionMark="?";
		String tokenEqual="token=";
		
		url=apiUrl+assetNumber+prefix+questionMark+tokenEqual+token;
		
		
		System.out.println(url);
		
		Object resultingAsset=restTemplate.getForObject(url,Object.class);
		
		System.out.println(resultingAsset);
		
		model.addAttribute("assetNumber",assetNumber);
		model.addAttribute("asset",resultingAsset);
		
		return "testAPIredirect";
		
	}
	
	
	
	
}
