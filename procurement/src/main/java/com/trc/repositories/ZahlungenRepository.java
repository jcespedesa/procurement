package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.ZahlungenEntity;

@Repository
public interface ZahlungenRepository  extends CrudRepository<ZahlungenEntity,Long>  
{

	@Query("Select u from ZahlungenEntity u Order by month,year Desc")
	List<ZahlungenEntity>  getAllByDescription();
	
	@Query("Select u from ZahlungenEntity u Where (month=?1 and year=?2) Order by month,year Desc")
	List<ZahlungenEntity>  findByMonthYear(int month, int year);
	
	@Modifying
	@Transactional
	@Query("Update ZahlungenEntity u set u.invoiceNum=?2,u.reqNumber=?3,u.dateSent=?4,u.notes=?5,u.invoiceDownloaded=?6,u.voucher=?7,u.checkCut=?8 where u.id=?1")
	void updateZahlungen(Long id,String invoiceNum,String reqNumber,String dateSent,String notes,String invoiceDownloaded,String voucher,String checkcut);
		
}
