package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.ClientsEntity;


@Repository
public interface ClientsRepository extends CrudRepository<ClientsEntity,Long>
{
	@Query("Select u from ClientsEntity u WHERE u.division=?1 Order by cname")
	List<ClientsEntity> getClientsByDivision(String division);
	
	@Query("Select u from ClientsEntity u Order by cname")
	List<ClientsEntity> getAllAlphab();
	
	@Query("Select u from ClientsEntity u WHERE u.cname LIKE '%' || ?1 || '%' Order by cname")
	List<ClientsEntity>  getClientsByName(String stringSearch);
	
	@Query("Select u from ClientsEntity u WHERE u.program LIKE '%' || ?1 || '%' Order by cname")
	List<ClientsEntity>  getClientsByProgram(String stringSearch);
	
	@Query("Select u from ClientsEntity u WHERE (u.division='300' and u.hhsDivision=?1 and vacStatus=?2 and active='Yes') Order by cname")
	List<ClientsEntity>  getClieByVacStatus(String division, String vacStatus);
	
}
