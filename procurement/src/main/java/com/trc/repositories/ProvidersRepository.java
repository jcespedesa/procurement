package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.ProvidersEntity;


@Repository
public interface ProvidersRepository  extends CrudRepository<ProvidersEntity,Long>
{
	@Query("Select u.address from SitesEntity u where u.sname=?1")
	String getAppPath(@Param("sname") String sname);
	
	@Query("Select u from ProvidersEntity u Order by code")
	List<ProvidersEntity> getAllListByCode();
	
	@Modifying
	@Transactional
	@Query("Update ProvidersEntity u set u.site=?2 where u.id=?1")
	void updateLocationName(Long id, String site);
	
	@Query("Select u from ProvidersEntity u Order by pname")
	List<ProvidersEntity> getAllListByName();
	
}
