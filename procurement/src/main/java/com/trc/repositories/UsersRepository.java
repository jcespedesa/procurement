package com.trc.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.entities.UsersEntity;

@Repository
public interface UsersRepository  extends CrudRepository<UsersEntity,Long>
{

	@Query("Select u from UsersEntity u WHERE (u.userid=?1) Order by u.username")
	UsersEntity  getUserById(Long userid);
	
	@Modifying
	@Transactional
	@Query("update UsersEntity u set u.password=?2 where u.userid=?1")
	void setDefaultPass(Long id,String encodedPass);

}
