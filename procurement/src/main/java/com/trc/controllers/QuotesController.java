package com.trc.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletOutputStream;
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

import com.trc.entities.ItemsEntity;
import com.trc.entities.ProjectsEntity;
import com.trc.entities.ProvidersEntity;
import com.trc.entities.QuotesEntity;
import com.trc.entities.SitesEntity;
import com.trc.repositories.SettingsRepository;
import com.trc.services.FileService;
import com.trc.services.ItemsService;
import com.trc.services.ProjectsService;
import com.trc.services.ProvidersService;
import com.trc.services.QuotesService;
import com.trc.services.RecordNotFoundException;
import com.trc.services.SitesService;


@Controller
@RequestMapping("/procurement/Quotes")
public class QuotesController 
{
	@Autowired
	SettingsRepository repositorySettings;
	
	@Autowired
	QuotesService service;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	ProjectsService projectService;
	
	@Autowired
	SitesService siteService;
	
	@Autowired
	ProvidersService providerService;
	
	@Autowired
	ItemsService itemsService;
	
	//CRUD operations for quotes
	
			@GetMapping("/list")
			public String getAllQuotes(Model model)
			{
				//List<QuotesEntity> list=service.getAllQuotes();
				
				List<QuotesEntity> list=service.getAllByDate();
				
				model.addAttribute("quotes",list);
				
				return "quotesList";
				
				
			}
			
			@RequestMapping(path={"/edit","/edit/{id}"})
			public String editQuotesById(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
			{
				//Preparing list of projects
				List<ProjectsEntity> projectsList=projectService.getAllProjects();
				
				//Preparing list of sites
				List<SitesEntity> sitesList=siteService.getAllSites();
				
				//Preparing list of providers
				List<ProvidersEntity> providersList=providerService.getAllProviders();
				
				//Preparing list of items
				List<ItemsEntity> itemsList=itemsService.getAllItemsListDesc();
				
				model.addAttribute("quote",new QuotesEntity());
				model.addAttribute("projects",projectsList);
				model.addAttribute("sites",sitesList);
				model.addAttribute("providers",providersList);
				model.addAttribute("items",itemsList);
				
				if(id.isPresent())
				{
					QuotesEntity entity=service.getQuoteById(id.get());
					model.addAttribute("quote",entity);
				}
				else
				{
					//Generating a random value for record identification
					Random r=new Random();
					int seed=r.nextInt();
					
					model.addAttribute("seed",seed);
					
					//System.out.println("The value of seed is: "+ seed);
					
					
				}
				
								
				return "quotesAddEdit";
			}
			
			@RequestMapping(path="/delete/{id}")
			public String deleteQuoteById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
			{
				
				service.deleteQuoteById(id);
				
				return "redirect:/procurement/Quotes/list";
				
			}
			
			@RequestMapping(path="/createQuote", method=RequestMethod.POST)
			public String createOrUpdateQuote(QuotesEntity quote)
			{
				//System.out.println("Inside the controller to update or create. Object is: "+ quote);
				
								
				service.createOrUpdate(quote);
				
				return "redirect:/procurement/Quotes/list";
				
				
			}
			
			@RequestMapping(path={"/upload/{id}"})
		    public String uploadById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException 
		    {
												
				String dirPath="";
				String uploadDirAux="";
				String dirTrail="quotes/";
				String quoteId=null;
				
				quoteId=Long.toString(id);
												
		    	//Retrieving quote entity		    	    	
		        QuotesEntity entity=service.getQuoteById(id);
		        
		        //Preparing to scan files in location
		      //Getting the application main path
				uploadDirAux=repositorySettings.getAppPath("appHome");
				
				//System.out.println("The general path for the resources folder is "+ uploadDirAux);
				
				//Preparing the address for scan docs folder
				dirPath=uploadDirAux+dirTrail+quoteId;
				
				//System.out.println("The partial path for the resources folder is "+ dirPath);
				
				//System.out.println("El path to the working folder is: "+ dirPath);
		        
		        //Retrieving the list of file in the directory
				Set<String> listFiles=service.getAllFiles(dirPath);
		        
				//System.out.println("Captured list of files is: "+ listFiles);
				
				//converting collection in array
				ArrayList<String> newListFiles=new ArrayList<>(listFiles);
										        
		        model.addAttribute("quote",entity);
		        model.addAttribute("listFiles",newListFiles);
		        		        
		        return "quotesUpload";
		    }
			
			@PostMapping("/uploadFile")
		    public String uploadFile(Model model,@RequestParam("file") MultipartFile file,@RequestParam("id") Long id, RedirectAttributes redirectAttributes) throws RecordNotFoundException 
		    {

				String trail="quotes";
				
		    	if(file.getOriginalFilename().equals(""))
		    	{	
		    		
		    		QuotesEntity entity=service.getQuoteById(id);
			    	
			    	model.addAttribute("quote",entity);
			    	model.addAttribute("id",id);
			    	model.addAttribute("message","You need to select a file to updload. This selection cannot be empty...");
		    		
			    	return "quotesRedirect";
		    		
		    	}	
		    	    	
		        fileService.uploadFile(file,id,trail);
		        redirectAttributes.addFlashAttribute("message","You successfully uploaded "+ file.getOriginalFilename() +"!");

		        QuotesEntity entity=service.getQuoteById(id);
		    	
		    	model.addAttribute("quote",entity);
		    	model.addAttribute("id",id);
		    	model.addAttribute("message","The file was uploaded successfully...");
		        	        
		    	return "quotesRedirect";
		    }

			@PostMapping("/deleteFile")
		    public String deleteFile(Model model,@RequestParam Long id,@RequestParam String fileName) throws RecordNotFoundException 
		    {
		    	
				String trail="quotes";
				
		    	//Deleting the picture from folder and the picture name from the table
		    	fileService.deleteFile(fileName,id, trail);
		    	
		    	QuotesEntity entity=service.getQuoteById(id);
		    	
		    	model.addAttribute("quote",entity);
		    	model.addAttribute("id",id);
		    	model.addAttribute("message","The file was deleted successfully...");
		    	
		        return "quotesRedirect";
		    }
			
			@RequestMapping(path={"/filePreview/{fileName}"})
		    public String uploadById(Model model, @PathVariable("fileName") String fileName) throws RecordNotFoundException 
		    {
				System.out.println("Inside the controller to preview file: "+ fileName);
				
								
				model.addAttribute("fileName",fileName);
				
				return "quotesPreview";
				
				
			}
			
			@PostMapping("/open")
		    public void open(Model model,@RequestParam Long id,@RequestParam String fileName, HttpServletResponse response) throws RecordNotFoundException 
			{
		        response.setHeader("Access-Control-Allow-Origin", "*");
		        
		        
		        String dirPath="";
				String uploadDirAux="";
				String dirTrail="quotes/";
				String quoteId=null;
				String podcherk="/";
				
				
				quoteId=Long.toString(id);
												
		    	//Retrieving quote entity		    	    	
		        QuotesEntity entity=service.getQuoteById(id);
		        
		        //Preparing to scan files in location
		      //Getting the application main path
				uploadDirAux=repositorySettings.getAppPath("appHome");
				
				//Preparing the address for scan docs folder
				dirPath=uploadDirAux+dirTrail+quoteId+podcherk+fileName;
		        
				model.addAttribute("quote",entity);
				
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
			
			@RequestMapping(path="/search")
			public String search(Model model)
			{
				//System.out.println("Inside the search form");
				
				List<ItemsEntity> list=itemsService.getAllItems();
				
				model.addAttribute("items",list);
				
				return "searchFormQuotes";
				
				
			}
			
			@RequestMapping(path="/findQuoteNum", method=RequestMethod.POST)
			public String findQuoteNum(Model model,String stringSearch)
			{
				//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
				
				List<QuotesEntity> list=service.searchQuotesByNum(stringSearch);
				
				//System.out.println(list);
				
				model.addAttribute("quotes",list);
				model.addAttribute("stringSearch",stringSearch);
				
				return "quotesList";
				
				
			}
			
			@RequestMapping(path="/findQuoteName", method=RequestMethod.POST)
			public String findQuoteName(Model model,String stringSearch)
			{
				//System.out.println("Inside the controller to search by string. Object is: "+ stringSearch);
				
				List<QuotesEntity> list=service.searchQuotesByName(stringSearch);
				
				//System.out.println(list);
				
				model.addAttribute("quotes",list);
				model.addAttribute("stringSearch",stringSearch);
				
				return "quotesList";
				
				
			}
			
			
			@RequestMapping(path="/findQuoteItem", method=RequestMethod.POST)
			public String findQuoteItem(Model model,String stringSearch)
			{
				//System.out.println("Inside the controller to search by item. Object is: "+ stringSearch);
				
				List<QuotesEntity> list=service.searchQuotesByItem(stringSearch);
				
				//System.out.println(list);
				
				model.addAttribute("quotes",list);
				model.addAttribute("stringSearch",stringSearch);
				
				return "quotesList";
				
				
			}
			
			@RequestMapping(path={"/download/{id}/{fileName}"})
		    public void downloadById(Model model, @PathVariable("id") Long id, @PathVariable("fileName") String fileName,HttpServletResponse response) throws IOException 
		    {

				String dirPath="";
				String uploadDirAux="";
				String dirTrail="quotes/";
				String quoteId=null;
				String podcherk="/";
				
				quoteId=Long.toString(id);
				
		    	//Retrieving quote entity		    	    	
		        //QuotesEntity entity=service.getQuoteById(id);
		        
		        //Preparing to scan files in location
		      //Getting the application main path
				uploadDirAux=repositorySettings.getAppPath("appHome");
				
				//Preparing the complete path
				dirPath=uploadDirAux+dirTrail+quoteId+podcherk+fileName;
				
				File file=new File(dirPath);

				response.setContentType("application/octet-stream");
				String headerKey="Content-Disposition";
				String headerValue="attachment; filename="+ file.getName();
				
				
				response.setHeader(headerKey, headerValue);
				ServletOutputStream outputStream=response.getOutputStream();
				BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(file));

				byte[] buffer=new byte[8192]; //8KB buffer
				int bytesRead=-1;

				while((bytesRead=inputStream.read(buffer)) != -1)
				{
					outputStream.write(buffer,0,bytesRead);
				}
				
				inputStream.close();
				outputStream.close();
				
		    }
}
