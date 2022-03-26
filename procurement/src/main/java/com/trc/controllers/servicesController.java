package com.trc.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trc.entities.BedListsEntityHHS;
import com.trc.entities.BedListsEntityHMIS;
import com.trc.entities.UsersEntity;
import com.trc.entities.ViSpdatsEntity;
import com.trc.services.ServicesService;
import com.trc.services.UsersService;


@Controller
@RequestMapping("/procurement/services")
public class servicesController 
{
	
	@Autowired
	ServicesService service;
	
	@Autowired
	UsersService serviceUsers;
	
	//Bed list comparison 
	
	@GetMapping("/bedListHHSsel")
	public String bedListCompHHSsel(Model model,Long quserId)
	{
		//Retrieving user identity
		UsersEntity quser=serviceUsers.getUserById(quserId);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "servicesBedListHHSsel";
			
	}
	
	@RequestMapping(value="/bedListHHSimport", method=RequestMethod.POST)
    public String importExcelFileHHS(Model model, @RequestParam("file") MultipartFile files, Long quserId) throws IOException 
	{
        //HttpStatus status=HttpStatus.OK;
        
        int index=0;
        
        String dateRecord=null;
        String kluch=null;
                
        String lastName=null;
        String firstName=null;
        String middleName=null;
        String repitaya=",";
        String pustoy=" ";
        String cname="";
        
        String message="I could not open the file...";
        
        
        LocalDateTime now=LocalDateTime.now(); 
		
		//Generating today's date
		DateTimeFormatter dtfRecord=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		dateRecord=dtfRecord.format(now);
		
		//Generating a random value for record identification
		Random r=new Random();
		int seed=r.nextInt();
		
		//Converting seed in always a positive number
		if(seed<0)
			seed=-seed;
		
		//Obtaining the unique identifier for this file
		kluch=dateRecord +"-"+ seed;
        
		
		//Opening and importing the selected file

        XSSFWorkbook workbook=new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet=workbook.getSheetAt(0);
        
        
        for(index=7; index < worksheet.getPhysicalNumberOfRows(); index++)
        {
           
        	BedListsEntityHHS bedList=new BedListsEntityHHS();

        	XSSFRow row=worksheet.getRow(index);
        	
        	if(row != null) 
        	{
                
	        	bedList.setFloor(row.getCell(0).getStringCellValue());
	        	bedList.setRoom(row.getCell(1).getStringCellValue());
	            bedList.setBed(row.getCell(2).getStringCellValue());
	                
	            lastName=row.getCell(3).getStringCellValue();
	            firstName=row.getCell(4).getStringCellValue();
	            middleName=row.getCell(5).getStringCellValue();
	                
	            cname=lastName+repitaya+firstName+pustoy+middleName;
	                
	            bedList.setCname(cname);
	            bedList.setKluch(kluch);
	                                            
	            service.create(bedList);
	            
	            //System.out.println("index is "+ index);
	            message="HHS Bed List File was imported successfully. Total of rows is "+ index;
        	}                          
        }
        
        workbook.close();
        
        service.bedCorrectionHHS(kluch);
        
      //Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
      		
                
        model.addAttribute("message",message);
        model.addAttribute("kluch",kluch);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);

        return "servicesBedListRedirect";
    }
	
	
	
	@RequestMapping(value="/bedListHMISsel", method=RequestMethod.POST)
	public String bedListCompHMISsel(Model model,String kluch,Long quserId)
	{
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
				
		model.addAttribute("kluch",kluch);
		
		model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		return "servicesBedListHMISsel";
			
	}
	
	@RequestMapping(value="/bedListHMISimport", method=RequestMethod.POST)
    public String importExcelFileHHS(Model model, @RequestParam("file") MultipartFile files, String kluch, Long quserId) throws IOException 
	{
        //HttpStatus status=HttpStatus.OK;
        
        int index=0;
                               
        String message="I could not open the file...";
               		
		//Opening and importing the selected file

        XSSFWorkbook workbook=new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet=workbook.getSheetAt(0);
        
        
        for(index=7; index < worksheet.getPhysicalNumberOfRows(); index++)
        {
           
        	BedListsEntityHMIS bedList=new BedListsEntityHMIS();

        	XSSFRow row=worksheet.getRow(index);
        	
        	if(row != null) 
        	{
                
	        	bedList.setFloor(row.getCell(0).getStringCellValue());
	        	bedList.setRoom(row.getCell(1).getStringCellValue());
	            bedList.setBed(row.getCell(2).getStringCellValue());
	            bedList.setCname(row.getCell(3).getStringCellValue());
	                
	            bedList.setKluch(kluch);
	                                            
	            service.createHMIS(bedList);
	            
	            //System.out.println("index is "+ index);
	            message="HMIS Bed List File was imported successfully. Total of rows is "+ index;
        	}                          
        }
        
        workbook.close();
        
        //Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
        
        model.addAttribute("message",message);
        model.addAttribute("kluch",kluch);

        return "servicesBedListComp";
    }

	@RequestMapping(value="/bedListComp", method=RequestMethod.POST)
	public String bedListComparation(Model model,String kluch, Long quserId)
	{
				
		List<BedListsEntityHHS> bedListHHS=service.getCompListHHS(kluch);
		List<BedListsEntityHMIS> bedListHMIS=service.getCompListHMIS(kluch);
		
		String room1=null;
		String bed1=null;
		String room2=null;
		String bed2=null;
		String name=null;
		
		//Capturing the array for room,bed order from HMIS
		
		for(BedListsEntityHMIS bedList1 : bedListHMIS)
		{
			room1=bedList1.getRoom();
			bed1=bedList1.getBed();
			name="";
			
			for(BedListsEntityHHS bedList2 : bedListHHS)
			{
			
				room2=bedList2.getRoom();
				bed2=bedList2.getBedCorrection();
				
								
				if((room1.equals(room2))&&(bed1.equals(bed2)))
				{
					name=bedList2.getCname();
					
				}
				
			}
			
			bedList1.setHhsCname(name);
			
		}
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("bedListHHS",bedListHHS);
		model.addAttribute("bedListHMIS",bedListHMIS);
		
		return "servicesBedListView";
	}
	
	
	
	//Billables report process
	
	@GetMapping("/billableFileSel")
	public String billableUploadSel(Model model, Long quserId)
	{
		String dateRecord=null;
        String kluch=null;
        
        //Creating the unique record identifier
        LocalDateTime now=LocalDateTime.now(); 
		
		//Generating today's date
		DateTimeFormatter dtfRecord=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		dateRecord=dtfRecord.format(now);
		
		//Generating a random value for record identification
		Random r=new Random();
		int seed=r.nextInt();
		
		//Converting seed in always a positive number
		if(seed<0)
			seed=-seed;
		
		//Obtaining the unique identifier for this file
		kluch=dateRecord +"-"+ seed;
		
		//Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
		
		model.addAttribute("kluch",kluch);
		
		return "servicesBillRepSel";
			
	}
	
	@RequestMapping(value="/billableFileUpload", method=RequestMethod.POST)
    public String billableFileUpload(Model model, @RequestParam("file") MultipartFile files, String kluch, Long quserId) throws IOException 
	{
        //HttpStatus status=HttpStatus.OK;
        
        int index=0;
                               
        String message="I could not open the file...";
        
              		
		//Opening and importing the selected file, viSpdat tab 0

        XSSFWorkbook workbook=new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet=workbook.getSheetAt(0);
        
        
        for(index=2; index < worksheet.getPhysicalNumberOfRows(); index++)
        {
           
        	ViSpdatsEntity viSpdat=new ViSpdatsEntity();

        	XSSFRow row=worksheet.getRow(index);
        	
        	if(row != null) 
        	{
              /*  
        		viSpdat.setFloor(row.getCell(0).getStringCellValue());
        		viSpdat.setRoom(row.getCell(1).getStringCellValue());
        		viSpdat.setBed(row.getCell(2).getStringCellValue());
        		viSpdat.setCname(row.getCell(3).getStringCellValue());
	                
	            bedList.setKluch(kluch);
	                                            
	            service.createHMIS(bedList);
	          */  
	            //System.out.println("index is "+ index);
	            message="HMIS Bed List File was imported successfully. Total of rows is "+ index;
        	}                          
        }
        
        workbook.close();
        
      //Retrieving user identity
        UsersEntity quser=serviceUsers.getUserById(quserId);
        
        model.addAttribute("quserId",quserId);
		model.addAttribute("quser",quser);
        
        model.addAttribute("message",message);
        model.addAttribute("kluch",kluch);

        return "servicesBillRepRedirect";
    }

	
}

