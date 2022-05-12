package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.SectionsEntity;


@Repository
public interface SectionsRepository extends CrudRepository<SectionsEntity,Long> 
{
	@Query("Select u.sectionName from SectionsEntity u where u.sectionNumber=?1")
	String getSectionName(String sectionNumber);
	
	@Query("Select u from SectionsEntity u Order by sectionName")
	List<SectionsEntity>  getAllByName();
	
	@Query("Select u from SectionsEntity u Where u.active='Yes' Order by sectionName")
	List<SectionsEntity>  getAllActives();
	
	@Query("Select count(u) from SectionsEntity u where u.sectionNumber=?1")
	int findSectionDuplicity(String sectionNumber);
}
