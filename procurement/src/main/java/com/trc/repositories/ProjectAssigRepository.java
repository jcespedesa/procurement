package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trc.entities.ProjectAssigEntity;


@Repository
public interface ProjectAssigRepository  extends JpaRepository<ProjectAssigEntity,Long>
{
	@Query("Select u from ProjectAssigEntity u")
	List<ProjectAssigEntity> getAllSections();
	
	@Query("Select u from ProjectAssigEntity u where userId=?1")
	List<ProjectAssigEntity> getAllAssigById(String userId);
	
	@Query("Select count(u) from ProjectAssigEntity u where (u.userId=?1 and u.assigProjectNumber=?2)")
	int findAssigDuplicity(String userId,String projectNumber);

	@Modifying
	@Transactional
	@Query("Delete from ProjectAssigEntity u where u.assigProjectNumber=?1")
	void deleteByProjectNumber(String projectNumber);
}
