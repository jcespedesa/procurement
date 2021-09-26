package com.trc.repositories;

import java.util.List;

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
}
