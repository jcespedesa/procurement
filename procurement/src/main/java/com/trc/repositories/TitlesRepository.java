package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.TitlesEntity;

@Repository
public interface TitlesRepository extends CrudRepository<TitlesEntity,Long>
{
	@Query("Select u.titleDesc from TitlesEntity u where u.titleNum=?1")
	String getTitleName(@Param("titleNum") String titleNum);
	
	@Query("Select u from TitlesEntity u Order by titleDesc")
	List<TitlesEntity>  getAllByName();
	
	@Query("Select count(u) from TitlesEntity u where u.titleNum=?1")
	int findTitleDuplicity(String titleNum);
}
