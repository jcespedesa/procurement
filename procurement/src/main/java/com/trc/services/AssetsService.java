package com.trc.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.AssetsEntity;
import com.trc.entities.ClientsEntity;
import com.trc.repositories.AssetAssigRepository;
import com.trc.repositories.AssetsRepository;
import com.trc.repositories.ClientsRepository;
import com.trc.repositories.PeripheralsRepository;
import com.trc.repositories.ProjectsRepository;

@Service
public class AssetsService 
{
	@Autowired
	AssetsRepository repository;
	
	@Autowired
	ProjectsRepository repositoryProjects;
	
	@Autowired
	PeripheralsRepository repositoryPeripherals;
	
	@Autowired
	AssetAssigRepository repositoryReassig;
	
	@Autowired
	ClientsRepository repositoryClients;
	
	public List<AssetsEntity> getAllAssets()
	{
		List<AssetsEntity> result=(List<AssetsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
		
	public AssetsEntity getAssetById(Long id) throws RecordNotFoundException
	{
		Optional<AssetsEntity> asset=repository.findById(id);
		
		if(asset.isPresent())
			return asset.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	
	public AssetsEntity createOrUpdate(AssetsEntity entity)
	{
		if(entity.getAssetid()==null)
		{
			//Preparing to retrieve asset ID
			
			String datePurchased=null;
			String assetId=null;
			String seed=null;
			String division=null;
			
			Long id;
			
			//System.out.println("Inside the service to create a new record. Object is: "+ entity);
			
			//Finding the division number
			division=repositoryProjects.getDivisionByProject(entity.getProject());
			
			//Saving the new record
			entity=repository.save(entity);
			
			//Retrieving the strobe value
			seed=entity.getStrobe();
			
			//Finding the id of the new record
			assetId=repository.getAssetId(seed);
			
			id=Long.parseLong(assetId);
			
			//resetting strobe field
			repository.resetStrobe(id);
						
			//Updating special fields
			datePurchased=entity.getDatePurchased();
			repository.setDatePurchased(id,datePurchased);
			repository.setDivision(id,division);
			
			return entity;
		}
		else
		{
			Optional<AssetsEntity> asset=repository.findById(entity.getAssetid());
			
			if(asset.isPresent())
			{
										
				//Setting variables to update real date quote
				Long localId=entity.getAssetid();
				String localDatePurchased=entity.getDatePurchased();
				
				
				AssetsEntity newEntity=asset.get();
				
				newEntity.setItem(entity.getItem());
				newEntity.setAssetNumber(entity.getAssetNumber());
				newEntity.setMaker(entity.getMaker());
				newEntity.setModel(entity.getModel());
				
				newEntity.setDatePurchased(entity.getDatePurchased());
				newEntity.setUsername(entity.getUsername());
				newEntity.setTitle(entity.getTitle());
				newEntity.setClientId(entity.getClientId());
				
				newEntity.setDivision(entity.getDivision());
				newEntity.setSite(entity.getSite());
				
				newEntity.setActive(entity.getActive());
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setProject(entity.getProject());
				
				newEntity.setAuthorId(entity.getAuthorId());
				newEntity.setAuthor(entity.getAuthor());
				newEntity.setAuthorEmail(entity.getAuthorEmail());
				
				newEntity.setEmail(entity.getEmail());
				newEntity.setProgram(entity.getProgram());
				
				newEntity.setAge(entity.getAge());
				newEntity.setStatus(entity.getStatus());
				
				newEntity=repository.save(newEntity);
				
				//Updating local date 
				repository.setDatePurchased(localId,localDatePurchased);
				
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deleteAssetById(Long id) throws RecordNotFoundException
	{
		Optional<AssetsEntity> asset=repository.findById(id);
		
		if(asset.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Asset record exist for given id");
		
		
	}
	
	public AssetsEntity getAssetByNumber(String assetNumber) throws RecordNotFoundException
	{
		AssetsEntity asset=repository.getAssetByNum(assetNumber);
		
		if(asset==null)
			throw new RecordNotFoundException("No record exist for given asset number");
		else
			return asset;
		
	}
	
	public String saveNewAsset(AssetsEntity asset) 
	{
		String item=null;
		String assetNumber=null;
		String maker=null;
		String model=null;
		String datePurchased=null;
		String username=null;
		String title=null;
		String site=null;
		String active=null;
		String notes=null;
		String project=null;
		String strobe=null;
		String author=null;
		String kluch=null;
		String email=null;
		String authorEmail=null;
		String program=null;
		String klass=null;
		String status=null;
		
		item=asset.getItem();
		assetNumber=asset.getAssetNumber();
		maker=asset.getMaker();
		model=asset.getModel();
		datePurchased=asset.getDatePurchased();
		username=asset.getUsername();
		title=asset.getTitle();
		site=asset.getSite();
		active=asset.getActive();
		notes=asset.getNotes();
		project=asset.getProject();
		strobe=asset.getStrobe();
		author=asset.getAuthor();
		authorEmail=asset.getAuthorEmail();
		kluch=asset.getKluch();
		email=asset.getEmail();
		program=asset.getProgram();
		klass=asset.getKlass();
		status=asset.getStatus();
		
		//Trying to save new record in table
		repository.saveNewAsset(item,assetNumber,maker,model,datePurchased,username,title,site,active,notes,project,strobe,author,authorEmail,kluch,email,program,klass,status);
		
		return kluch;
	
	}
	
	public List<AssetsEntity> findThisSessionAssets(String kluch)
	{
		List<AssetsEntity> result=repository.getThisSessionAssets(kluch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
	public void updatePortalAuthorInfo(String kluch, String author, String authorEmail)
	{
		
				
		repository.updateAuthorInfo(kluch,author,authorEmail);
		
	}
	
	public AssetsEntity getAssetByKluch(String kluch) throws RecordNotFoundException
	{
		AssetsEntity asset=repository.getAssetByKluch(kluch);
		
		return asset;
		
	}
	
	public void updateNotesInfo(String assetId, String notes)
	{
		
		Long assetIdLong=null;
		
		assetIdLong=Long.parseLong(assetId);
		
		repository.updateNotes(assetIdLong,notes);
		
	}
	
	public List<AssetsEntity> getByAssetNum(String assetNumber)
	{
		List<AssetsEntity> result=repository.getByAssetNumber(assetNumber);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
	public List<AssetsEntity> getByItem(String item)
	{
				
		List<AssetsEntity> result=repository.getByItem(item);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
	public List<String> getAuthorEmails()
	{
				
		List<String> result=repository.getDistAuthorEmails();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<String>();
		
	}
	
	public List<String> getAssigneeEmails()
	{
				
		List<String> result=repository.getDistAssigneeEmails();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<String>();
		
	}
	
	public List<AssetsEntity> getByAuthor(String authorClientId)
	{
				
		List<AssetsEntity> result=repository.getByAuthor(authorClientId);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
	public List<AssetsEntity> getByProgram(String projectNumber)
	{
				
		List<AssetsEntity> result=repository.getByProgram(projectNumber);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
	public String getUsername(Long assetId)
	{
				
		String username=repository.getUsername(assetId);
		
		return username;
		
	}
	
	public Integer findHowManyPeripherals(String assetId)
	{
				
		int numberPeripherals=repositoryPeripherals.getNumberPeripherals(assetId);
		
		return numberPeripherals;
		
	}
	
	public Integer priznakOldItem(String datePurchased)
	{
				
		int yearsOld=0;
		
		//Finding local date for today's date
		LocalDate todayDateLocal=new java.sql.Date(new java.util.Date().getTime()).toLocalDate();
		
		//Converting received Date Purchased in separate Strings
		
		int year=0;
		int month=0;
		int day=0;
		
		String yearString=null;
		String monthString=null;
		String dayString=null;
		
		yearString=datePurchased.substring(0,datePurchased.indexOf("-"));
		monthString=datePurchased.substring(datePurchased.indexOf("-")+1,7);
		dayString=datePurchased.substring(8,10);
		
		year=Integer.parseInt(yearString);
		month=Integer.parseInt(monthString);
		day=Integer.parseInt(dayString);
		
		LocalDate datePurchasedLocal=LocalDate.of(year,month,day);
		
		//finding the age
		yearsOld=Period.between(datePurchasedLocal,todayDateLocal).getYears();
				
		return yearsOld;
		
	}
	
	public void assetReassignation(Long assetId,String newAssigId)
	{
					
		//Making changes and saving information
		repository.putReassignation(assetId,newAssigId);
		
		
	}
	
	public String getAssetIdByKluch(String kluch)
	{
		String assetId=null;
		
		Long assetIdLong=repository.getIdByKluch(kluch);
		
		assetId=String.valueOf(assetIdLong);
		
		return assetId;
		
	}
	
	public AssetsEntity createAsset(AssetsEntity entity)
	{
		entity=repository.save(entity);
		
		return entity;
	}
	
	public List<AssetsEntity> getByAssignee(String email)
	{
				
		List<AssetsEntity> result=repository.getByAssignee(email);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}


	public List<AssetsEntity> getByClientId(String clientId) 
	{
		List<AssetsEntity> result=repository.getByAssignee(clientId);
					
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
	}
	
	
	public List<ClientsEntity> getClientsMultipleAssigs() 
	{
		//Retrieving list of assets
		List<AssetsEntity> ListAssets=repository.getAllActives();
		
		//Retrieving list of users
		List<ClientsEntity> listClients=repositoryClients.getAllActives();
		
		int counterRep=0;
		
		String clientIdString=null;
		
		for(ClientsEntity client : listClients)
		{
			for(AssetsEntity asset : ListAssets)
			{
				clientIdString=String.valueOf(client.getClientid());
				
				if(asset.getClientId().equals(clientIdString))
					counterRep++;
			}
			client.setCounter(counterRep);
			counterRep=0;
			
		}
		
		
		if(listClients.size() > 0)
			return listClients;
		else
			return new ArrayList<ClientsEntity>();
			
			
	}
	
	
		
}
