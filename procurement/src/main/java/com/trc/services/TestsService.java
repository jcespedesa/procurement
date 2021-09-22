package com.trc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.AssetsEntity;
import com.trc.repositories.AssetsRepository;
import com.trc.repositories.TestsRepository;


@Service
public class TestsService 
{
	@Autowired
	TestsRepository repository;
	
	@Autowired
	AssetsRepository repositoryAssets;
	
	
	public AssetsEntity getAssetById(Long id) throws RecordNotFoundException
	{
		Optional<AssetsEntity> asset=repositoryAssets.findById(id);
		
		if(asset.isPresent())
			return asset.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	
}
