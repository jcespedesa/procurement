package com.trc.services;

import java.util.List;

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
	
	@Autowired
	AssetsService serviceAssets;
	
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
    
    public void sendInventoryReceipt(String toEmail, AssetsEntity asset, String author, String authorEmail) throws JsonProcessingException, RecordNotFoundException 
    {
    		
    	
    	String description="Receipt confirmation for IT Asset Inventory Input";
    	String notes="Input items in this session: ";
		String repitaya=", ";
		String kluch=null;
		String listAssetNumbers="";
								
		//Creating new receipt object to be sent
		ReceiptsEntity receipt=new ReceiptsEntity();
						
		//Adapting asset information for the receipt 
		receipt.setDescription(description);
		receipt.setDateReceipt(asset.getDateCreation());
		receipt.setSignedBy(author);
		receipt.setSemail(authorEmail);
		receipt.setKluch(asset.getKluch());
		
						
		//Saving the receipt
		ReceiptsEntity newReceipt=serviceReceipts.create(receipt);
				
		//Getting the session kluch
		kluch=receipt.getKluch();
		
		//Retrieving list of assets within this session
		List<AssetsEntity> assets=serviceAssets.findThisSessionAssets(kluch);
		
		for(AssetsEntity object : assets)
		{
			
			listAssetNumbers=listAssetNumbers+repitaya+object.getAssetNumber();
			
		}
		//Assembling notes strings
		notes=notes+listAssetNumbers;
		
		//Registering notes in the object
		receipt.setNotes(notes);
				   	
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
