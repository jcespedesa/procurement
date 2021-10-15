package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trc.entities.AssetAssigEntity;


@Repository
public interface AssetAssigRepository extends JpaRepository<AssetAssigEntity,Long>
{
	@Query("Select u from AssetAssigEntity u Order by u.dateReassignation Desc")
	List<AssetAssigEntity> getAllReassigDesc();
	
	@Query("Select u from AssetAssigEntity u where assetId=?1")
	List<AssetAssigEntity> getAllAssigById(String assetId);
	
		
}
