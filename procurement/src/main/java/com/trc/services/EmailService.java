package com.trc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trc.entities.AssetsEntity;
import com.trc.entities.ReceiptsEntity;

@Service
public class EmailService 
{
	@Autowired
	ProjectsService serviceProjects;
	
	@Autowired
	TitlesService serviceTitles;
	
	@Autowired
	ReceiptsService serviceReceipts;
	
	@Autowired
	ItemsService serviceItems;
	
	
	private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) 
    {
        this.javaMailSender=javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) 
    {

        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("javaOperator@cc-dc.org");

        javaMailSender.send(mailMessage);
    }
    
    public void sendMailObject(String toEmail, String subject, ReceiptsEntity body) throws JsonProcessingException 
    {
    	
    	ObjectMapper mapper=new ObjectMapper();
    	
    	String jsonString=mapper.writeValueAsString(body);
    	
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(jsonString);

        mailMessage.setFrom("javaOperator@cc-dc.org");

        javaMailSender.send(mailMessage);
    }
    
    public void sendInventoryReceipt(String toEmail, AssetsEntity asset) throws JsonProcessingException, RecordNotFoundException 
    {
    	
    	String description="Receipt confirmation for IT Asset Inventory Input";
		
		String assetIdString=null;
		
		String program=null;
		String titleName=null;
		String itemName=null;
		
		String notes="See notes in peripherals";
		
		Long itemIdLong=null;
		
    	
		//Converting assetId from long to string
		assetIdString=Long.toString(asset.getAssetid());
		
		//Converting itemId from string to long
		itemIdLong=Long.parseLong(asset.getItem());
				
		//Retrieving program name
		program=serviceProjects.getProjectByNum(asset.getProject());
						
		//Retrieving title name
		titleName=serviceTitles.getTitleByNumber(asset.getTitle());
				
		//Retrieving item name
		itemName=serviceItems.getItemDescById(itemIdLong);
		
		
		//Creating new receipt object to be sent
		ReceiptsEntity receipt=new ReceiptsEntity();
						
		//Adapting asset information for the receipt 
		receipt.setDescription(description);
		receipt.setAssetId(assetIdString);
		receipt.setAssetName(itemName);
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
		
		//Correcting notes for a cell phone asset
		if(asset.getItem().equals("27"))
			receipt.setNotes(notes);
		
				
		//Saving the receipt
		ReceiptsEntity newReceipt=serviceReceipts.create(receipt);
				   	
    	//Email sending process
    	
    	ObjectMapper mapper=new ObjectMapper();
    	
    	String jsonString=mapper.writeValueAsString(newReceipt);
    	
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(description);
        mailMessage.setText(jsonString);

        mailMessage.setFrom("javaOperator@cc-dc.org");

        javaMailSender.send(mailMessage);
    }
	
}
