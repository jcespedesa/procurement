package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.PurchasesEntity;

@Repository
public interface PurchasesRepository extends CrudRepository<PurchasesEntity,Long> 
{
	@Query("Select u from PurchasesEntity u where u.reqid=?1")
	PurchasesEntity getProjectById(Long reqId);
	
	@Query("Select u from PurchasesEntity u Order by u.realReqDate Desc")
	List<PurchasesEntity>  getAllByDate();
	
	@Modifying
	@Transactional
	@Query("Update PurchasesEntity u set u.itApproverId='',u.itDateApproval='',u.realItDateApproval='',u.foApproverId='',u.foDateApproval='',u.realFoDateApproval='',u.status='modified',u.observation=''  where u.reqid=?1")
	void retrieveById(Long id);
	
	@Query("Select u.reqid from PurchasesEntity u where u.strobe=?1")
	String getRequestId(String seed);
	
	@Modifying
	@Transactional
	@Query("Update PurchasesEntity u set u.realReqDate=?2,u.status='new',requestorId=?3 where u.id=?1")
	void updateRealDateReq(Long id,String dateRequest,String requestorId);
}
