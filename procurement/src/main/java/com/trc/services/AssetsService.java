package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.AssetsEntity;
import com.trc.repositories.AssetsRepository;
import com.trc.repositories.ProjectsRepository;

@Service
public class AssetsService 
{
	@Autowired
	AssetsRepository repository;
	
	@Autowired
	ProjectsRepository repositoryProjects;
	
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
				
				newEntity.setDivision(entity.getDivision());
				newEntity.setSite(entity.getSite());
				
				newEntity.setActive(entity.getActive());
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setProject(entity.getProject());
				newEntity.setAuthor(entity.getAuthor());
				newEntity.setAuthorEmail(entity.getAuthorEmail());
				
				newEntity.setEmail(entity.getEmail());
				newEntity.setProgram(entity.getProgram());
				
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
		
		//Trying to save new record in table
		repository.saveNewAsset(item,assetNumber,maker,model,datePurchased,username,title,site,active,notes,project,strobe,author,authorEmail,kluch,email,program,klass);
		
		return kluch;
	
	}
	
	public List<AssetsEntity> findThisSessionAssets(String kluch)
	{
		List<AssetsEntity> result=(List<AssetsEntity>) repository.getThisSessionAssets(kluch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
	public void updatePortalAuthorInfo(String assetId, String author, String authorEmail)
	{
		
		Long assetIdLong=null;
		
		assetIdLong=Long.parseLong(assetId);
		
		repository.updateAuthorInfo(assetIdLong,author,authorEmail);
		
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
		List<AssetsEntity> result=(List<AssetsEntity>) repository.getByAssetNumber(assetNumber);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetsEntity>();
		
	}
	
}
