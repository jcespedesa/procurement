package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.AssetAssigEntity;
import com.trc.repositories.AssetAssigRepository;



@Service
public class AssetsAssigService 
{
	@Autowired
	AssetAssigRepository repository;

	public List<AssetAssigEntity> getAllAssetAssig()
	{
		List<AssetAssigEntity> result=repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetAssigEntity>();
		
	}
	
	public List<AssetAssigEntity> getAssigById(String assetId)
	{
		List<AssetAssigEntity> result=repository.getAllAssigById(assetId);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<AssetAssigEntity>();
		
	}
	
		
	public AssetAssigEntity getAssetAssigById(Long id) throws RecordNotFoundException
	{
		Optional<AssetAssigEntity> asset=repository.findById(id);
		
		if(asset.isPresent())
			return asset.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	
	public AssetAssigEntity createReassig(AssetAssigEntity entity)
	{
		//System.out.println(entity);
				
			
		entity=repository.save(entity);
				
		
		return entity;
				
		
	}
	
	public void deleteAssetAssigById(Long id) throws RecordNotFoundException
	{
		Optional<AssetAssigEntity> asset=repository.findById(id);
		
		if(asset.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Asset record exist for given id");
		
		
	}
	

	
}
