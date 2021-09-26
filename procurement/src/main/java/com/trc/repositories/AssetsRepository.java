package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.AssetsEntity;


@Repository
public interface AssetsRepository extends CrudRepository<AssetsEntity,Long>
{
				
	@Modifying
	@Transactional
	@Query("Update AssetsEntity u set u.realDatePurchased=?2 where u.assetid=?1")
	void setDatePurchased(Long id,String datePurchased);
		
	@Query("Select u from AssetsEntity u Order by realDatePurchased Desc")
	List<AssetsEntity>  getAllByDate();
	
	@Query("Select u from AssetsEntity u WHERE u.assetNumber=?1")
	AssetsEntity  getAssetByNum(@Param("stringSearch") String stringSearch);
	
	@Query("Select u.assetid from AssetsEntity u where u.strobe=?1")
	String getAssetId(@Param("seed") String seed);
	
	@Modifying
	@Transactional
	@Query("Update AssetsEntity u set u.strobe='None' where u.id=?1")
	void resetStrobe(Long id);
	
	@Modifying
	@Transactional
	@Query("Update AssetsEntity u set u.division=?2 where u.id=?1")
	void setDivision(Long id,String division);
	
	@Modifying
	@Transactional
	@Query(value="insert into assets(item,assetNumber,maker,model,datePurchased,username,title,site,active,notes,project,strobe,author,authorEmail,kluch,email,program) values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18)",nativeQuery=true)
	void saveNewAsset(String item,String assetNumber,String maker,String model,String datePurchased,String username,String title,String site,String active,String notes,String project,String strobe,String author,String authorEmail,String kluch,String email,String program,String klass);
	
	@Query("Select u from AssetsEntity u WHERE u.kluch=?1")
	List<AssetsEntity> getThisSessionAssets(String kluch);
	
	@Modifying
	@Transactional
	@Query("Update AssetsEntity u set u.author=?2, u.authorEmail=?3 where u.id=?1")
	void updateAuthorInfo(Long id,String author, String authorEmail);
	
	@Query("Select u from AssetsEntity u WHERE u.kluch=?1")
	AssetsEntity  getAssetByKluch(String kluch);
	
	@Modifying
	@Transactional
	@Query("Update AssetsEntity u set u.notes=?2 where u.id=?1")
	void updateNotes(Long id,String notes);
	
	@Query("Select u from AssetsEntity u WHERE u.assetNumber=?1")
	List<AssetsEntity> getByAssetNumber(String assetNumber);
}
