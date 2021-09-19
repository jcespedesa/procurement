package com.trc.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.QuotesEntity;
import com.trc.repositories.ProjectsRepository;
import com.trc.repositories.QuotesRepository;
import com.trc.repositories.SettingsRepository;
import com.trc.repositories.SitesRepository;

@Service
public class QuotesService 
{
	@Autowired
	QuotesRepository repository;
	
	@Autowired
	SettingsRepository repositorySettings;
	
	@Autowired
	ProjectsRepository repositoryProjects;
	
	@Autowired
	SitesRepository repositorySites;
	
	public List<QuotesEntity> getAllQuotes()
	{
		List<QuotesEntity> result=(List<QuotesEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<QuotesEntity>();
		
	}
	
	public List<QuotesEntity> getAllByDate()
	{
		List<QuotesEntity> result=(List<QuotesEntity>) repository.getAllByDate();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<QuotesEntity>();
		
	}
	
	public QuotesEntity getQuoteById(Long id) throws RecordNotFoundException
	{
		Optional<QuotesEntity> quote=repository.findById(id);
		
		if(quote.isPresent())
			return quote.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public QuotesEntity createOrUpdate(QuotesEntity entity)
	{
		
		String dirPath="";
		String uploadDirAux="";
		String dirTrail="quotes/";
		
		String projectNum=null;
		String project=null;
		
				
		//Retrieving project description
		project=repositoryProjects.getProjectName(projectNum);
		
		//Getting the application main path
		uploadDirAux=repositorySettings.getAppPath("appHome");
		
						
		if(entity.getQuoteid()==null)
		{
			
			//Preparing to retrieve quote ID
			String quoteId=null;
			String seed=null;
			String dateQuote=null;
			String dateQuoteSent=null;
			
					
			Long id;
			
			//Saving the new record
			entity=repository.save(entity);
			
			seed=entity.getStrobe();
			
			quoteId=repository.getQuoteId(seed);
			
			id=Long.parseLong(quoteId);
			
			
			
			//Preparing the address for scan docs folder
			dirPath=uploadDirAux+dirTrail+quoteId;
			
						
			//resetting strobe field
			repository.resetStrobe(id);
			
			//Updating special fields
			dateQuote=entity.getDateQuote();
			repository.updateRealDateQuote(id,dateQuote);
			
			dateQuoteSent=entity.getDateQuoteSent();
			repository.updateRealDateQuoteSent(id,dateQuoteSent);
			
			//Setting up project description
			repository.setProjectDescription(id,project);
			
						
			//Creating the folder for scan documents
			File file=new File(dirPath);
			
			if(!file.exists())
			{
				
				if(file.mkdir())
				{
					System.out.println("Directory "+ dirPath +" has been created successfully!");
				}
				else
				{
					System.out.println("Directory "+ dirPath +" failed to be created!");
				}
			}
										
			return entity;
		}
		else
		{
			Optional<QuotesEntity> quote=repository.findById(entity.getQuoteid());
			
			if(quote.isPresent())
			{
				
				String localIdString=null;
				
				//Setting variables to update real date quote
				Long localId=entity.getQuoteid();
				String localDateQuote=entity.getDateQuote();
				
				//Converting long Id to string
				localIdString=Long.toString(localId);
				
				QuotesEntity newEntity=quote.get();
				
				newEntity.setNumber(entity.getNumber());
				newEntity.setDetails(entity.getDetails());
				newEntity.setDateQuote(entity.getDateQuote());
				newEntity.setReqNumber(entity.getReqNumber());
				newEntity.setRequester(entity.getRequester());
				newEntity.setProjectNum(entity.getProjectNum());
				newEntity.setProject(entity.getProject());
				newEntity.setAmount(entity.getAmount());
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setQuoteSentTo(entity.getQuoteSentTo());
				newEntity.setDateQuoteSent(entity.getDateQuoteSent());
				newEntity.setItem(entity.getItem());
				
				newEntity=repository.save(newEntity);
				
				//Updating local date quote
				repository.setQuoteDate(localId,localDateQuote);
				
				
				//Setting up project description
				repository.setProjectDescription(localId,project);
				
								
				//Preparing the address for scan docs folder
				dirPath=uploadDirAux+dirTrail+localIdString;
				
				
				//Creating the folder for scan documents if does not exist already
				File file=new File(dirPath);
				
				if(!file.exists())
				{
					
					if(file.mkdir())
					{
						System.out.println("Directory "+ dirPath +" has been created successfully!");
					}
					else
					{
						System.out.println("Directory "+ dirPath +" failed to be created!");
					}
				}
				
				
								
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deleteQuoteById(Long id) throws RecordNotFoundException
	{
		Optional<QuotesEntity> quote=repository.findById(id);
		
		if(quote.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Quote record exist for given id");
		
		
	}

	public Set<String> getAllFiles(String dir) 
	{
	    return Stream.of(new File(dir).listFiles())
	      .filter(file -> !file.isDirectory())
	      .map(File::getName)
	      .collect(Collectors.toSet());
	}
	
	
	
	public List<QuotesEntity> searchQuotesByNum(String stringSearch)
	{
		List<QuotesEntity> result=(List<QuotesEntity>) repository.getQuotesByNum(stringSearch);
		
		return result;
		
	}
	
	public List<QuotesEntity> searchQuotesByName(String stringSearch)
	{
		List<QuotesEntity> result=(List<QuotesEntity>) repository.getQuotesByName(stringSearch);
		
		return result;
		
	}
	
	public List<QuotesEntity> searchQuotesByItem(String stringSearch)
	{
		List<QuotesEntity> result=(List<QuotesEntity>) repository.getQuotesByItem(stringSearch);
		
		return result;
		
	}
	
}
