package com.trc.controllers;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trc.entities.ZahlungenEntity;
import com.trc.services.RecordNotFoundException;
import com.trc.services.ZahlungenService;

@Controller
@RequestMapping("/procurement/zahlungen")
public class ZahlungenController 
{

	@Autowired
	ZahlungenService service;
	
	
	@GetMapping("/select")
	public String selectZahlungen(Model model)
	{
						
		
		return "zahlungenSelect";
			
			
	}
	
	
	
	//CRUD operations for zahlungen
	
	@GetMapping("/list")
	public String getAllZahlungen(Model model)
	{
						
		List<ZahlungenEntity> list=service.getAllZahlungen();
			
		model.addAttribute("zahlungen",list);
			
		return "zahlungenList";
			
			
	}
	
			
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editZahlungenById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
	{
		String monthLiteral=null;
		int month=0;
		
		ZahlungenEntity entity=service.getZahlungenById(id.get());
		
		month=entity.getMonth();
		monthLiteral= Integer.toString(month); 
		
		
		//converting int month to string month
        monthLiteral=service.monthTranslator(monthLiteral);
		
		model.addAttribute("zahlungen",entity);
		model.addAttribute("monthLiteral",monthLiteral);
			
		return "zahlungenEdit";
	}
		
	@RequestMapping(path="/delete/{id}")
	public String deleteZahlungenById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
			
		service.deleteById(id);
			
		return "redirect:/procurement/Zahlungen/list";
			
	}
		
	@RequestMapping(path="/createZahlungen", method=RequestMethod.POST)
	public String createOrUpdateZahlungen(ZahlungenEntity zahlungen)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ zahlungen);
			
		service.createOrUpdate(zahlungen);
			
		return "redirect:/procurement/Zahlungen/list";

	}
	
	@PostMapping("/view")
	public String selectYearMonth(Model model,@RequestParam("month") String month,@RequestParam("year") String year)
	{
		List<ZahlungenEntity> list=service.getByMonthYear(month,year);
		
		String monthLiteral=null;
		
		// check if the list is empty or not 
        // in that case we need to create a new list of periodicals
        boolean priznakNoPeriod=list.isEmpty(); 
        
        if(priznakNoPeriod==true)
        {
        	
        	//Selected period does not exists so we are creating one
        	service.createNewPeriod(month,year);
        	
        	
        	list=service.getByMonthYear(month,year);
        	
        }
        		
		//converting int month to string month
        monthLiteral=service.monthTranslator(month);
        
        model.addAttribute("monthLiteral",monthLiteral);
		model.addAttribute("month",month);
		model.addAttribute("year",year);
		model.addAttribute("periods",list);
					
		return "zahlungenList";

	}
	
	
	@PostMapping("/update")
	public String updateZahlungen(Model model,@RequestParam("invoiceNum") String invoiceNum,@RequestParam("reqNumber") String reqNumber,
		@RequestParam("dateSent") String dateSent,@RequestParam("notes") String notes,@RequestParam("month") String month,@RequestParam("year") String year,
		@RequestParam("id") String id,@RequestParam("invoiceDownloaded") String invoiceDownloaded,@RequestParam("voucher") String voucher,@RequestParam("checkCut") String checkCut)
	{
		
		//System.out.println("Inside the controller to update. Item ID is: "+ id);
		//System.out.println("Month is: "+ month);
		//System.out.println("Year is: "+ year);
				
		
		String monthLiteral=null;
		
				
		//Updating fields in record
		service.updateZahlungen(id,invoiceNum,reqNumber,dateSent,notes,invoiceDownloaded,voucher,checkCut);
		
		//converting int month to string month
        monthLiteral=service.monthTranslator(month);
        
        //Retrieving record identity
        List<ZahlungenEntity> list=service.getByMonthYear(month,year);
		
		model.addAttribute("month",month);
		model.addAttribute("year",year);
		model.addAttribute("monthLiteral",monthLiteral);
		model.addAttribute("periods",list);
		
		return "zahlungenList";
		
	}
	
	
	@GetMapping("/back")
	public String backToView(Model model,@RequestParam("month") String month,@RequestParam("year") String year)
	{
		List<ZahlungenEntity> list=service.getByMonthYear(month,year);
		
		String monthLiteral=null;
		
		       		
		//converting int month to string month
        monthLiteral=service.monthTranslator(month);
        
        model.addAttribute("monthLiteral",monthLiteral);
		model.addAttribute("month",month);
		model.addAttribute("year",year);
		model.addAttribute("periods",list);
					
		return "zahlungenList";

	}
	
	
	
}
