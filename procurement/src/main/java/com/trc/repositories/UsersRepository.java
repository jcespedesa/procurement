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

	UsersEntity getUserByEmail(String email);
		
	@Query("Select u.password from UsersEntity u Where u.email=?1")
	String  getPassByEmail(String email);
	
	@Query("Select count(u) from UsersEntity u where u.email=?1")
	int findEmailDuplicity(String email);
	
	@Query("Select u from UsersEntity u where priznakITapprover='Yes'")
	UsersEntity  getITapprover();
	
	@Query("Select u from UsersEntity u where priznakFOapprover='Yes'")
	UsersEntity  getFOapprover();

	@Query("Select u from UsersEntity u where priznakPpc='Yes'")
	UsersEntity  getPpc();
	
	@Query("Select count(u) from UsersEntity u where u.priznakITapprover='Yes'")
	int getUniqueIT();
	
	@Query("Select count(u) from UsersEntity u where u.priznakFOapprover='Yes'")
	int getUniqueFO();
	
	@Query("Select count(u) from UsersEntity u where u.priznakPpc='Yes'")
	int getUniquePPC();

	@Modifying
	@Transactional
	@Query("update UsersEntity u set u.priznakITapprover=?2,u.priznakFOapprover=?3,u.priznakPpc=?4 where u.userid=?1")
	void updateRoles(Long userId, String priznakITapprover, String priznakFOapprover, String priznakPpc);
	
	@Modifying
	@Transactional
	@Query("update UsersEntity u set u.priznakITapprover='No' where u.userid=?1")
	void resetPriznakITapprov(Long id);
	
	@Modifying
	@Transactional
	@Query("update UsersEntity u set u.priznakFOapprover='No' where u.userid=?1")
	void resetPriznakFOapprov(Long id);
	
	@Modifying
	@Transactional
	@Query("update UsersEntity u set u.priznakPpc='No' where u.userid=?1")
	void resetPriznakPpc(Long id);
}
