package com.trc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.TempUsersEntity;


@Repository
public interface TempUsersRepository extends CrudRepository<TempUsersEntity,Long> 
{
	@Query("Select u.password from TempUsersEntity u Where u.email=?1")
	String  getPassByEmail(String email);
	
	@Query("SELECT CASE WHEN COUNT(email) > 0 THEN true ELSE false END FROM TempUsersEntity p WHERE p.email=?1")
    boolean existsByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("Update TempUsersEntity u set u.password=?2 where u.email=?1")
	void updatePassword(String email,String password);
}
