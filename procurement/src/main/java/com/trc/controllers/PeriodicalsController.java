package com.trc.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trc.entities.PeriodicalsEntity;
import com.trc.entities.ProvidersEntity;
import com.trc.entities.SitesEntity;
import com.trc.repositories.SettingsRepository;
import com.trc.services.FileService;
import com.trc.services.PeriodicalsService;
import com.trc.services.ProvidersService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;


@Controller
@RequestMapping("/procurement/Periodicals")
public class PeriodicalsController 
{

	@Autowired
	PeriodicalsService service;
	
	@Autowired
	SitesService siteService;
	
	@Autowired
	ProvidersService providerService;
	
	@Autowired
	SettingsRepository repositorySettings;
	
	@Autowired
	FileService fileService;
	
	
	//CRUD operations for periodicals
	
	@GetMapping("/list")
	public String getAllPeriodicals(Model model)
	{
		//List<PeriodicalsEntity> list=service.getAllPeriodicals();
		
		List<PeriodicalsEntity> list=service.getAllByDescription();
		
		model.addAttribute("periodicals",list);
		
		return "periodicalsList";
		
		
	}
	
	@RequestMapping(path={"/edit","/edit/{id}"})
	public String editPeriodicalsById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
	{
		//Preparing list of sites
		List<SitesEntity> sitesList=siteService.getAllSites();
		
		//Preparing list of providers
		List<ProvidersEntity> providersList=providerService.getAllProviders();
		
		model.addAttribute("sites",sitesList);
		model.addAttribute("providers",providersList);
		
		
		if(id.isPresent())
		{
			PeriodicalsEntity entity=service.getPeriodicalById(id.get());
			model.addAttribute("periodical",entity);
		}
		else
		{
			//Generating a random value for record identification
			Random r=new Random();
			int seed=r.nextInt();
			
			model.addAttribute("seed",seed);
						
			model.addAttribute("periodical",new PeriodicalsEntity());
			
		}
		return "periodicalsAddEdit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deletePeriodicalById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		
		service.deletePeriodicalById(id);
		
		return "redirect:/procurement/Periodicals/list";
		
	}
	
	@RequestMapping(path="/createPeriodical", method=RequestMethod.POST)
	public String createOrUpdatePeriodical(PeriodicalsEntity periodical)
	{
		//System.out.println("Inside the controller to update or create. Object is: "+ site);
		
		service.createOrUpdate(periodical);
		
		return "redirect:/procurement/Periodicals/list";

	}
	
	//Scan docs section
	
	@RequestMapping(path={"/upload/{id}"})
    public String uploadById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException 
    {
										
		String dirPath="";
		String uploadDirAux="";
		String dirTrail="periodicals/";
		String periodicalId=null;
		
		periodicalId=Long.toString(id);
										
    	//Retrieving quote entity		    	    	
        PeriodicalsEntity entity=service.getPeriodicalById(id);
        
        //Preparing to scan files in location
      //Getting the application main path
		uploadDirAux=repositorySettings.getAppPath("appHome");
		
		//System.out.println("The general path for the resources folder is "+ uploadDirAux);
		
		//Preparing the address for scan docs folder
		dirPath=uploadDirAux+dirTrail+periodicalId;
		
		//System.out.println("The partial path for the resources folder is "+ dirPath);
		
		//System.out.println("El path to the working folder is: "+ dirPath);
        
        //Retrieving the list of file in the directory
		Set<String> listFiles=service.getAllFiles(dirPath);
        
		//System.out.println("Captured list of files is: "+ listFiles);
		
		//converting collection in array
		ArrayList<String> newListFiles=new ArrayList<>(listFiles);
								        
        model.addAttribute("periodical",entity);
        model.addAttribute("listFiles",newListFiles);
        		        
        return "periodicalsUpload";
    }
	
	@PostMapping("/uploadFile")
    public String uploadFile(Model model,@RequestParam("file") MultipartFile file,@RequestParam("id") Long id, RedirectAttributes redirectAttributes) throws RecordNotFoundException 
    {
		String trail="periodicals";
		

    	if(file.getOriginalFilename().equals(""))
    	{	
    		
    		PeriodicalsEntity entity=service.getPeriodicalById(id);
	    	
	    	model.addAttribute("periodical",entity);
	    	model.addAttribute("id",id);
	    	model.addAttribute("message","You need to select a file to updload. This selection cannot be empty...");
    		
	    	return "periodicalsRedirect";
    		
    	}	
    	    	
        fileService.uploadFile(file,id,trail);
        redirectAttributes.addFlashAttribute("message","You successfully uploaded "+ file.getOriginalFilename() +"!");

        PeriodicalsEntity entity=service.getPeriodicalById(id);
    	
    	model.addAttribute("periodical",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The file was uploaded successfully...");
        	        
    	return "periodicalsRedirect";
    }

	@PostMapping("/deleteFile")
    public String deleteFile(Model model,@RequestParam Long id,@RequestParam String fileName) throws RecordNotFoundException 
    {
    	
		String trail="periodicals";
		
    	//Deleting the picture from folder and the picture name from the table
    	fileService.deleteFile(fileName,id, trail);
    	
    	PeriodicalsEntity entity=service.getPeriodicalById(id);
    	
    	model.addAttribute("periodical",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The file was deleted successfully...");
    	
        return "periodicalsRedirect";
    }
	
	@RequestMapping(path={"/filePreview/{fileName}"})
    public String uploadById(Model model, @PathVariable("fileName") String fileName) throws RecordNotFoundException 
    {
		//System.out.println("Inside the controller to preview file: "+ fileName);
		
						
		model.addAttribute("fileName",fileName);
		
		return "periodicalsPreview";
		
		
	}
	
	@PostMapping("/open")
    public void open(Model model,@RequestParam Long id,@RequestParam String fileName, HttpServletResponse response) throws RecordNotFoundException 
	{
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        
        String dirPath="";
		String uploadDirAux="";
		String dirTrail="periodicals/";
		String periodicalId=null;
		String podcherk="/";
		
		
		periodicalId=Long.toString(id);
										
    	//Retrieving quote entity		    	    	
        PeriodicalsEntity entity=service.getPeriodicalById(id);
        
        //Preparing to scan files in location
      //Getting the application main path
		uploadDirAux=repositorySettings.getAppPath("appHome");
		
		//Preparing the address for scan docs folder
		dirPath=uploadDirAux+dirTrail+periodicalId+podcherk+fileName;
        
		model.addAttribute("periodical",entity);
		
        //Trying to preview the file
        try 
        {
            response.sendRedirect(dirPath);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        		 
    }
	
	
	
	
}
