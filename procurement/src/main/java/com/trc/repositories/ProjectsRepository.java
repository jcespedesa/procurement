package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.ProjectsEntity;

@Repository
public interface ProjectsRepository extends CrudRepository<ProjectsEntity,Long>
{
	@Query("Select u.project from ProjectsEntity u where u.projectNumber=?1")
	String getProjectName(String projectNum);
	
	@Query("Select u from ProjectsEntity u Order by project")
	List<ProjectsEntity>  getAllByProject();
	
	@Query("Select u from ProjectsEntity u WHERE u.projectNumber LIKE '%' || ?1 || '%' Order by project")
	List<ProjectsEntity>  getProjectsByNum(@Param("stringSearch") String stringSearch);
	
	@Query("Select u from ProjectsEntity u WHERE u.project LIKE '%' || ?1 || '%' Order by project")
	List<ProjectsEntity>  getProjectsByName(@Param("stringSearch") String stringSearch);
	
	@Query("Select u from ProjectsEntity u where u.department='300' Order by project")
	List<ProjectsEntity>  getAllHHSprojects();
	
	@Query("Select u from ProjectsEntity u where (u.department='300' and u.active='Yes') Order by udelnyBes")
	List<ProjectsEntity>  getAllHHSbyUB();
	
	@Query("Select u.department from ProjectsEntity u where u.projectNumber=?1")
	String getDivisionByProject(String project);
	
	@Query("Select u.udelnyBes from ProjectsEntity u where u.department=?1 Order by udelnyBes")
	List<Integer>  getOriginalListUB(String department);
	
	@Query("Select u from ProjectsEntity u where (u.department=?1 and u.active='Yes') Order by udelnyBes")
	List<ProjectsEntity>  getHhsFormView(String department);
}

