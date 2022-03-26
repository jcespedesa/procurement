package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.PeriodicalsEntity;
import com.trc.entities.ZahlungenEntity;
import com.trc.repositories.PeriodicalsRepository;
import com.trc.repositories.ZahlungenRepository;


@Service
public class ZahlungenService 
{

	@Autowired
	ZahlungenRepository repository;
	
	@Autowired
	PeriodicalsRepository repositoryPeriodicals;
	
	public List<ZahlungenEntity> getAllZahlungen()
	{
		List<ZahlungenEntity> result=(List<ZahlungenEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ZahlungenEntity>();
		
	}
	
	public List<ZahlungenEntity> getByMonthYear(String month, String year)
	{
		
		int monthInt=0;
		int yearInt=0;
		
		//Converting strings to int
		
		 monthInt=Integer.parseInt(month);
		 yearInt=Integer.parseInt(year);
		
		List<ZahlungenEntity> result=repository.findByMonthYear(monthInt,yearInt);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ZahlungenEntity>();
		
	}
	
		
	public ZahlungenEntity getZahlungenById(Long id) throws RecordNotFoundException
	{
		Optional<ZahlungenEntity> zahlungen=repository.findById(id);
		
		if(zahlungen.isPresent())
			return zahlungen.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public ZahlungenEntity createOrUpdate(ZahlungenEntity entity)
	{
		
				
		if(entity.getZahlungenid()==null)
		{
			//Preparing to retrieve quote ID
			
			entity=repository.save(entity);
			
						
			return entity;
		}
		else
		{
			Optional<ZahlungenEntity> zahlungen=repository.findById(entity.getZahlungenid());
			
			if(zahlungen.isPresent())
			{
				
								
				ZahlungenEntity newEntity=zahlungen.get();
				
				newEntity.setMonth(entity.getMonth());
				newEntity.setYear(entity.getYear());
				newEntity.setAmount(entity.getAmount());
				
				newEntity.setProviderId(entity.getProviderId());
				newEntity.setSiteNumber(entity.getSiteNumber());
				
				newEntity.setType(entity.getType());
				
				newEntity.setDateSent(entity.getDateSent());
				newEntity.setInvoiceNum(entity.getInvoiceNum());
				
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setDescription(entity.getDescription());
				newEntity.setAccount(entity.getAccount());
				newEntity.setPoNum(entity.getPoNum());
				
				newEntity.setInvoiceDownloaded(entity.getInvoiceDownloaded());
				newEntity.setVoucher(entity.getVoucher());
				newEntity.setCheckCut(entity.getCheckCut());
								
				newEntity=repository.save(newEntity);
				
								
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deleteById(Long id) throws RecordNotFoundException
	{
		Optional<ZahlungenEntity> zahlungen=repository.findById(id);
		
		if(zahlungen.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No zahlungen record exist for given id");
		
		
	}
	
	public void createNewPeriod(String month,String year)
	{
		int i=0;
		int monthInt=0;
		int yearInt=0;
		
		String providerId=null;
		String amount=null;
		String description=null;
		String siteNumber=null;
		String poNum=null;
		String account=null;
		String frequency=null;
		String type=null;
		
		monthInt=Integer.parseInt(month);
		yearInt=Integer.parseInt(year);
		
		//trying to get the list of periodicals
		List<PeriodicalsEntity> listPeriodicals=repositoryPeriodicals.getAllActives();
		
		for(i=0;i<listPeriodicals.size();i++)
		{
			//Creating new records in table zahlungen
			providerId=listPeriodicals.get(i).getProvider();
			amount=listPeriodicals.get(i).getAmount();
			siteNumber=listPeriodicals.get(i).getLocationNumber();
			poNum=listPeriodicals.get(i).getPonum();
			account=listPeriodicals.get(i).getAccount();
			description=listPeriodicals.get(i).getDescription();
			frequency=listPeriodicals.get(i).getFrequency();
			type=listPeriodicals.get(i).getType();
			
			ZahlungenEntity zahlungen= new ZahlungenEntity();
			
			zahlungen.setMonth(monthInt);
			zahlungen.setYear(yearInt);
			
			zahlungen.setProviderId(providerId);
			zahlungen.setAmount(amount);
			zahlungen.setDescription(description);
			zahlungen.setSiteNumber(siteNumber);
			zahlungen.setPoNum(poNum);
			zahlungen.setAccount(account);
			zahlungen.setFrequency(frequency);
			zahlungen.setType(type);

			repository.save(zahlungen);

		}
		
		
	}
	
	//Month translator
	
	public String monthTranslator(String selectedMonth)
	{
		String monthLiteral=null;
		
		switch(selectedMonth) 
		{
		 	case "1":
		 		monthLiteral="Jan";
		    break;

		 	case "2":
		 		monthLiteral="Feb";
		    break;
		    
		 	case "3":
		 		monthLiteral="Mar";
		    break;
		    
		 	case "4":
		 		monthLiteral="Apr";
		    break;
		    
		 	case "5":
		 		monthLiteral="May";
		    break;
		    
		 	case "6":
		 		monthLiteral="Jun";
		    break;
		    
		 	case "7":
		 		monthLiteral="Jul";
		    break;
		    
		 	case "8":
		 		monthLiteral="Aug";
		    break;
		    
		 	case "9":
		 		monthLiteral="Sep";
		    break;
		    
		 	case "10":
		 		monthLiteral="Oct";
		    break;
		    
		 	case "11":
		 		monthLiteral="Nov";
		    break;
		    
		 	case "12":
		 		monthLiteral="Dec";
		    break;
		    
		  default:
		    // code block
		}
		
		
		return monthLiteral;
		
				
	}
	
	public void updateZahlungen(String id,String invoiceNum,String reqNumber,String dateSent,String notes,String invoiceDownloaded,String voucher,String checkCut) 
	{
		Long idLong=null;		
		
		//Converting string id to Long
		idLong=Long.parseLong(id);
		
		//Trying to update field in table
		repository.updateZahlungen(idLong,invoiceNum,reqNumber,dateSent,notes,invoiceDownloaded,voucher,checkCut);
		
	}
	

}
