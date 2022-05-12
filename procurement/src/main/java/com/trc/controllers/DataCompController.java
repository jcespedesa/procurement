package com.trc.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.trc.entities.DataCompEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.UsersEntity;
import com.trc.services.DataCompService;
import com.trc.services.LogsService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.UsersService;

@Controller
@RequestMapping("/dataComp")
public class DataCompController 
{
	@Autowired
	DataCompService service;
	
	@Autowired
	UsersService serviceUsers;
	
	@Autowired
	LogsService serviceLogs;
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public String getAllSites(Model model, Long quserId)
	{
		List<DataCompEntity> list=service.getAllDataComp();
		
				
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("dataComps",list);
		
		return "dataCompsList";
		
		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String editDataCompById(Model model,Optional<Long> id, Long quserId) throws RecordNotFoundException 
	{
		boolean priznakNew=false;
		
		if(id.isPresent())
		{
			DataCompEntity entity=service.getDataCompById(id.get());
			priznakNew=false;
			
			model.addAttribute("dataComp",entity);
		}
		else
		{
			model.addAttribute("dataComp",new DataCompEntity());
			priznakNew=true;
			
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Retrieving the last record in the table
        DataCompEntity lastRecord=service.getLastRecord();
        
        //Preventing the null error when the table is completely empty
        if(lastRecord==null)
        	priznakNew=false;
        
        //System.out.println("Last record is: "+ lastRecord);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("lastRecord",lastRecord);
		model.addAttribute("priznakNew",priznakNew);
				
		return "dataCompsAddEdit";
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public String deleteDataCompById(Model model, Long id, Long quserId) throws RecordNotFoundException
	{
		String message=null;
		
		//Retrieving DataComp identity
		DataCompEntity dataComp=service.getDataCompById(id);
				
		service.deleteDataCompById(id);
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
      //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Deleting DataComp record from the database. DataComp ID was "+ dataComp.getDataid());
		log.setObject(dataComp.getDate());
		serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		message="DataComp was deleted...";
		
		model.addAttribute("message",message);
		
		return "dataCompsRedirect";
		
	}
	
	@RequestMapping(path="/createDataComp", method=RequestMethod.POST)
	public String createOrUpdateDataComp(Model model, DataCompEntity dataComp, Long quserId)
	{
		int priznakDuplicate=0;
		
		String localDataComp=null;		
		String message=null;
				
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
		        
		if(dataComp.getDataid()!=null)
        {
        	//Modify the record
        	
        	service.createOrUpdate(dataComp);
        	
        	message="DataComp was updated successfully...";
			
    		//Processing logs
    		LogsEntity log=new LogsEntity();
    		log.setSubject(quser.getEmail());
    		log.setAction("Modifying DataComp record. Item ID is "+ dataComp.getDataid());
    		log.setObject(dataComp.getDate());
    		serviceLogs.saveLog(log);
        	
        }
		else
        {
        	//Creating a new record
        	
        	//Checking if this item is not already in the system
        	localDataComp=dataComp.getDate();
        	priznakDuplicate=service.findDuplicates(localDataComp);
        	
        	if(priznakDuplicate==0)
        	{
        		service.createOrUpdate(dataComp);
				
        		message="DataComp was created successfully...";
        		
		   		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Creating new DataComp record in the database. DataComp ID is "+ dataComp.getDataid());
        		log.setObject(dataComp.getDate());
        		serviceLogs.saveLog(log);
        	}
        	else
        	{
        		message="Error: Duplicate DataComp was found, new record was not created at this time. Please review the list of DataComp again...";
				
        		//Processing logs
        		LogsEntity log=new LogsEntity();
        		log.setSubject(quser.getEmail());
        		log.setAction("Failing to create a new DataComp due to duplicity: "+ dataComp.getDataid());
        		log.setObject(dataComp.getDate());
        		serviceLogs.saveLog(log);
        	}
        }	
        		
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("message",message);
		
		return "dataCompsRedirect";
		
		
	}
	
	@RequestMapping(path="/exportCSV", method=RequestMethod.POST)
    public void exportToCSV(Model model,HttpServletResponse response,Long quserId) throws IOException, RecordNotFoundException 
	{
        response.setContentType("text/csv");
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime=dateFormatter.format(new Date());
         
        String headerKey="Content-Disposition";
        //String headerValue="attachment; filename=assets_" + currentDateTime + ".csv";
        
        String headerValue="attachment; filename=tcpData"+ currentDateTime +".csv";
        response.setHeader(headerKey,headerValue);
       	            
        //Retrieving data from the table
		List<DataCompEntity> list=service.getAllByDate();
		
		 
        ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        	        
        String[] csvHeader={"Date","HMIS Data","Bed List Data","Max Hourly Census"};
        String[] nameMapping={"date","hmis","bedList","hmax"};
                      
        	         
        csvWriter.writeHeader(csvHeader);
         
        for (DataCompEntity dataComp : list) 
        {
            csvWriter.write(dataComp,nameMapping);
        }
        
        csvWriter.close();
        
        //Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        //Processing logs
		LogsEntity log=new LogsEntity();
		log.setSubject(quser.getEmail());
		log.setAction("Exporting TCP Information to CSV file");
		log.setObject("Kind of exported list is TCP Data Compare");
		serviceLogs.saveLog(log);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
         
    }
	
}
