package com.trc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.BedListsEntityHHS;
import com.trc.entities.BedListsEntityHMIS;
import com.trc.repositories.BedListsRepositoryHHS;
import com.trc.repositories.BedListsRepositoryHMIS;

@Service
public class ServicesService 
{

	@Autowired
	BedListsRepositoryHHS repositoryHHS;
	
	@Autowired
	BedListsRepositoryHMIS repositoryHMIS;
	
	public BedListsEntityHHS create(BedListsEntityHHS entity)
	{
		
		entity=repositoryHHS.save(entity);
				
		return entity;
				
		
			
	}
	
	public BedListsEntityHMIS createHMIS(BedListsEntityHMIS entity)
	{
		
		entity=repositoryHMIS.save(entity);
				
		return entity;
				
		
			
	}
	
	public void bedCorrectionHHS(String kluch)
	{
		
		List<BedListsEntityHHS> bedList=(List<BedListsEntityHHS>) repositoryHHS.getSessionList(kluch);
		
		String bedName=null;
		
		for(BedListsEntityHHS bed : bedList)
		{
			bedName=bed.getBed();
			
			//System.out.println(bedName);
			
			switch(bedName)
			{
							
				case "1-Top":
					bed.setBedCorrection("01-T");
				break;	
			
				case "1-Bottom":
					bed.setBedCorrection("01-B");
				break;
			
				case "2-Top":
					bed.setBedCorrection("02-T");
				break;	
				
				case "2-Bottom":
					bed.setBedCorrection("02-B");
				break;
				
				case "3-Top":
					bed.setBedCorrection("03-T");
				break;	
				
				case "3-Bottom":
					bed.setBedCorrection("03-B");
				break;
				
				case "4-Top":
					bed.setBedCorrection("04-T");
				break;	
				
				case "4-Bottom":
					bed.setBedCorrection("04-B");
				break;
				
				case "5-Top":
					bed.setBedCorrection("05-T");
				break;	
				
				case "5-Bottom":
					bed.setBedCorrection("05-B");
				break;
				
				case "6-Top":
					bed.setBedCorrection("06-T");
				break;	
				
				case "6-Bottom":
					bed.setBedCorrection("06-B");
				break;
				
				case "7-Top":
					bed.setBedCorrection("07-T");
				break;	
				
				case "7-Bottom":
					bed.setBedCorrection("07-B");
				break;
				
				case "8-Top":
					bed.setBedCorrection("08-T");
				break;	
				
				case "8-Bottom":
					bed.setBedCorrection("08-B");
				break;
			
				case "9-Top":
					bed.setBedCorrection("09-T");
				break;	
				
				case "9-Bottom":
					bed.setBedCorrection("09-B");
				break;
				
				case "10-Top":
					bed.setBedCorrection("10-T");
				break;	
				
				case "10-Bottom":
					bed.setBedCorrection("10-B");
				break;
				
				case "11-Top":
					bed.setBedCorrection("11-T");
				break;	
				
				case "11-Bottom":
					bed.setBedCorrection("11-B");
				break;
				
				case "12-Top":
					bed.setBedCorrection("12-T");
				break;	
				
				case "12-Bottom":
					bed.setBedCorrection("12-B");
				break;
				
				case "13-Top":
					bed.setBedCorrection("13-T");
				break;	
				
				case "13-Bottom":
					bed.setBedCorrection("13-B");
				break;
				
				case "14-Top":
					bed.setBedCorrection("14-T");
				break;	
				
				case "14-Bottom":
					bed.setBedCorrection("14-B");
				break;
				
				case "15-Top":
					bed.setBedCorrection("15-T");
				break;	
				
				case "15-Bottom":
					bed.setBedCorrection("15-B");
				break;
				
				case "16-Top":
					bed.setBedCorrection("16-T");
				break;	
				
				case "16-Bottom":
					bed.setBedCorrection("16-B");
				break;
				
				default:
		             throw new IllegalArgumentException("Invalid bed definition: "+ bedName);
		             
		           
			}
			
			repositoryHHS.save(bed);  
		}
		
	}
	
	
	public List<BedListsEntityHHS> getCompListHHS(String kluch)
	{
		List<BedListsEntityHHS> result=(List<BedListsEntityHHS>) repositoryHHS.getBedListByKluch(kluch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<BedListsEntityHHS>();
		
	}
	
	public List<BedListsEntityHMIS> getCompListHMIS(String kluch)
	{
		List<BedListsEntityHMIS> result=(List<BedListsEntityHMIS>) repositoryHMIS.getBedListByKluch(kluch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<BedListsEntityHMIS>();
		
	}
}
