package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.TestSitesEntity;


@Repository
public interface TestSitesRepository extends CrudRepository<TestSitesEntity,Long> 
{

}
