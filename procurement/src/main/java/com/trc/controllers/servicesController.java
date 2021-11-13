package com.trc.controllers;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trc.entities.BedListsEntityHHS;
import com.trc.services.ServicesService;


@Controller
@RequestMapping("/procurement/services")
public class servicesController 
{
	
	@Autowired
	ServicesService service;
	
	@GetMapping("/bedListHHSsel")
	public String bedListCompHHSsel()
	{
			
		return "servicesBedListHHSsel";
			
	}
	
	@RequestMapping(value="/bedListHHSimport", method=RequestMethod.POST)
    public String importExcelFileHHS(@RequestParam("file") MultipartFile files) throws IOException 
	{
        //HttpStatus status=HttpStatus.OK;
        
        int index=0;
                
        String lastName=null;
        String firstName=null;
        String middleName=null;
        String repitaya=",";
        String pustoy=" ";
        String cname="";
                              

        XSSFWorkbook workbook=new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet=workbook.getSheetAt(0);
        
        
        for(index=7; index < worksheet.getPhysicalNumberOfRows(); index++)
        {
           
        	BedListsEntityHHS bedList=new BedListsEntityHHS();

        	XSSFRow row=worksheet.getRow(index);
                
        	bedList.setFloor(row.getCell(0).getStringCellValue());
        	bedList.setRoom(row.getCell(1).getStringCellValue());
            bedList.setBed(row.getCell(2).getStringCellValue());
                
            lastName=row.getCell(3).getStringCellValue();
            firstName=row.getCell(4).getStringCellValue();
            middleName=row.getCell(5).getStringCellValue();
                
            cname=lastName+repitaya+firstName+pustoy+middleName;
                
            bedList.setCname(cname);
                                            
            service.create(bedList);
            
            System.out.println("index is "+ index);
                                  
        }
        
        workbook.close();

        return "servicesBedListRedirect";
    }
	
}

