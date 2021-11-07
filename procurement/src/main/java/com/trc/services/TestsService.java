package com.trc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import com.trc.entities.AssetsEntity;
import com.trc.entities.DivisionsEntity;
import com.trc.entities.SettingsEntity;
import com.trc.entities.TestSitesEntity;
import com.trc.repositories.AssetsRepository;
import com.trc.repositories.SettingsRepository;
import com.trc.repositories.TestSitesRepository;
import com.trc.repositories.TestsRepository;


@Service
public class TestsService 
{
	@Autowired
	TestsRepository repository;
	
	@Autowired
	AssetsRepository repositoryAssets;
	
	@Autowired
	SettingsRepository repositorySettings;
	
	@Autowired
	TestSitesRepository repositoryTestSites;
	
	public AssetsEntity getAssetById(Long id) throws RecordNotFoundException
	{
		Optional<AssetsEntity> asset=repositoryAssets.findById(id);
		
		if(asset.isPresent())
			return asset.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public SettingsEntity getSettingBySname(String sname) throws RecordNotFoundException
	{
		SettingsEntity setting=repositorySettings.getBySname(sname);
		
		return setting;
	}
	
	@Bean
	public RestTemplate getRestTemplate() 
	{
	      return new RestTemplate();
	}
	
	
	public TestSitesEntity create(TestSitesEntity entity)
	{
		
		entity=repositoryTestSites.save(entity);
				
		return entity;
				
		
			
	}
	
	
		
}
