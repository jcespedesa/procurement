package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.QuotesEntity;

@Repository
public interface QuotesRepository extends CrudRepository<QuotesEntity,Long>
{
		
	@Modifying
	@Transactional
	@Query("Update QuotesEntity u set u.realDateQuote=?2 where u.id=?1")
	void setQuoteDate(Long id,String dateQuote);
	
	@Query("Select u.quoteid from QuotesEntity u where u.strobe=?1")
	String getQuoteId(@Param("seed") String seed);
		
	@Modifying
	@Transactional
	@Query("Update QuotesEntity u set u.strobe='None' where u.id=?1")
	void resetStrobe(Long id);
	
	@Modifying
	@Transactional
	@Query("Update QuotesEntity u set u.realDateQuote=?2 where u.id=?1")
	void updateRealDateQuote(Long id,String dateQuote);
	
	@Modifying
	@Transactional
	@Query("Update QuotesEntity u set u.project=?2 where u.id=?1")
	void setProjectDescription(Long id,String project);
	
	@Query("Select u from QuotesEntity u Order by realDateQuote Desc")
	List<QuotesEntity>  getAllByDate();
	
	@Modifying
	@Transactional
	@Query("Update QuotesEntity u set u.realDateQuoteSent=?2 where u.id=?1")
	void setQuoteDateSent(Long id,String dateQuoteSent);
	
	@Modifying
	@Transactional
	@Query("Update QuotesEntity u set u.realDateQuoteSent=?2 where u.id=?1")
	void updateRealDateQuoteSent(Long id,String dateQuoteSent);
	
	@Query("Select u from QuotesEntity u WHERE u.number LIKE '%' || ?1 || '%' Order by number")
	List<QuotesEntity>  getQuotesByNum(@Param("stringSearch") String stringSearch);
	
	@Query("Select u from QuotesEntity u WHERE ((u.quoteSentTo LIKE '%' || ?1 || '%') OR (u.requester LIKE '%' || ?1 || '%')) Order by number")
	List<QuotesEntity>  getQuotesByName(@Param("stringSearch") String stringSearch);
	
	@Query("Select u from QuotesEntity u WHERE (u.item=?1) Order by realDateQuote Desc")
	List<QuotesEntity>  getQuotesByItem(@Param("stringSearch") String stringSearch);
	
}
