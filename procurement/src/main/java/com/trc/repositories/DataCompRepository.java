package com.trc.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.DataCompEntity;

@Repository
public interface DataCompRepository extends CrudRepository<DataCompEntity,Long> 
{
	@Query("Select count(u) from DataCompEntity u where u.date=?1")
	int findDataCompDuplicity(String date);

	@Query("Select u from DataCompEntity u Order by realDate")
	List<DataCompEntity> getByDate();

	@Query("select u from DataCompEntity u Order by realDate Desc")
	DataCompEntity getLastRecord(PageRequest pageRequest);
	
	@Query("Select u from DataCompEntity u Where projectNumber=?1 Order by project")
	List<DataCompEntity> findAllByProjectNum(String projectNumber);
	
	@Query("Select u from DataCompEntity u Where (projectNumber=?1 and realDate<=?3 and realDate>=?2)")
	List<DataCompEntity> findAllByDate(String projectNumber,String initialDate,String finalDate);
}
