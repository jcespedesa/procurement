package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trc.entities.SectionAssigEntity;

@Repository
public interface SectionAssigRepository  extends JpaRepository<SectionAssigEntity,Long>
{
	@Query("Select u from SectionAssigEntity u")
	List<SectionAssigEntity> getAllSections();
	
	@Query("Select u from SectionAssigEntity u where userId=?1")
	List<SectionAssigEntity> getAllAssigById(String userId);
	
	@Query("Select count(u) from SectionAssigEntity u where (u.userId=?1 and u.assigSectionNumber=?2)")
	int findAssigDuplicity(String userId,String sectionNumber);

	@Modifying
	@Transactional
	@Query("Delete from SectionAssigEntity u where u.assigSectionNumber=?1")
	void deleteBySectionNumber(String sectionNumber);
	
}
