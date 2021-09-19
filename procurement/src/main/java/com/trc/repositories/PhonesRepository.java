package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.PhoneAddress;


@Repository
public interface PhonesRepository extends CrudRepository<PhoneAddress,Long> 
{
	@Query("Select u from PhoneAddress u Order by phoneNumber")
	List<PhoneAddress>  getAllByPhoneName();
	
	@Query("Select u from PhoneAddress u Order by phoneNumber")
	PhoneAddress getByPhoneNumber(String phoneNumber);
	
	@Query("Select u from PhoneAddress u WHERE u.phoneNumber LIKE '%' || ?1 || '%' Order by siteNumber,phoneNumber")
	List<PhoneAddress>  getByPhoneNum(String stringSearch);
	
	@Query("Select u from PhoneAddress u WHERE u.siteNumber=?1 Order by phoneNumber")
	List<PhoneAddress>  getBySiteNum(String stringSearch);
	
	@Query("Select Distinct(u.status) from PhoneAddress u Order by u.status")
	List<String>  findDistStatuses();
	
	@Query("Select Distinct(u.klass) from PhoneAddress u Order by u.klass")
	List<String>  findDistKlasses();
	
	@Query("Select u from PhoneAddress u WHERE u.status=?1 Order by siteNumber,phoneNumber")
	List<PhoneAddress>  getByStatus(String stringSearch);
	
	@Query("Select u from PhoneAddress u WHERE u.klass=?1 Order by siteNumber,phoneNumber")
	List<PhoneAddress>  getByKlass(String stringSearch);
	
	@Query("Select u from PhoneAddress u WHERE (u.siteNumber=?1 and u.klass=?2) Order by phoneNumber")
	List<PhoneAddress>  getSiteNumKlass(String siteNum,String klass);
	
	@Query("Select u from PhoneAddress u WHERE (u.siteNumber=?1 and u.status=?2) Order by phoneNumber")
	List<PhoneAddress>  getSiteNumStatus(String siteNum,String status);
	
	@Query("Select u from PhoneAddress u WHERE u.extension=?1 Order by siteNumber,phoneNumber")
	List<PhoneAddress>  getByExtension(String stringSearch);
	
}
