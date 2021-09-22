package com.trc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.entities.ReceiptsEntity;
import com.trc.services.ReceiptsService;

@Controller
@RequestMapping("/procurement/receipts")
public class ReceiptsController 
{
	@Autowired
	ReceiptsService service;
	
	
	@GetMapping("/list")
	public String getAllReceipts(Model model)
	{
								
		List<ReceiptsEntity> list=service.getAllReceipts();
					
		model.addAttribute("receipts",list);
					
		return "receiptsList";
		
		
	}
}
