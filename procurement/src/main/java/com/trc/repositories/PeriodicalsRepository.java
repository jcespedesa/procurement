package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.PeriodicalsEntity;


@Repository
public interface PeriodicalsRepository extends CrudRepository<PeriodicalsEntity,Long> 
{

	@Query("Select u.location from PeriodicalsEntity u where u.locationNumber=?1")
	String getLocation(@Param("locationNumber") String locationNumber);
	
	@Query("Select u.periodicalid from PeriodicalsEntity u where u.strobe=?1")
	String getPeriodicalId(@Param("seed") String seed);
	
	@Modifying
	@Transactional
	@Query("Update PeriodicalsEntity u set u.strobe='None' where u.id=?1")
	void resetStrobe(Long id);
	
	@Modifying
	@Transactional
	@Query("Update PeriodicalsEntity u set u.location=?2 where u.id=?1")
	void setLocationDescription(Long id,String location);
	
	@Query("Select u from PeriodicalsEntity u Order by description")
	List<PeriodicalsEntity>  getAllByDescription();
	
	@Query("Select u from PeriodicalsEntity u Where u.status='Active' Order by description")
	List<PeriodicalsEntity>  getAllActives();
	
}
