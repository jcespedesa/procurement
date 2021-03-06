package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.PeripheralsEntity;

@Repository
public interface PeripheralsRepository extends CrudRepository<PeripheralsEntity,Long>
{
	@Query("Select u.description from PeripheralsEntity u where u.peripheralNum=?1")
	String getPeripheralByNumber(String peripheralNum);
	
	@Query("Select u from PeripheralsEntity u where u.kluch=?1")
	List<PeripheralsEntity> getAllByKluch(String kluch);
	
	@Query("Select u from PeripheralsEntity u where u.assetId=?1")
	List<PeripheralsEntity> getAllByAssetId(String assetId);
	
	@Query(value="Select u FROM PeripheralsEntity u INNER JOIN AssetsEntity a ON u.assetId=a.assetid where a.project=?1")
	List<PeripheralsEntity> getByProject(String project);
	
	@Modifying
	@Transactional
	@Query("Update PeripheralsEntity u set u.realDatePurchased=?2 where u.itemid=?1")
	void setDatePurchased(Long id,String datePurchased);
	
	@Query("Select count(*) from PeripheralsEntity s where s.assetId=?1")
	Integer getNumberPeripherals(String assetId);
}
