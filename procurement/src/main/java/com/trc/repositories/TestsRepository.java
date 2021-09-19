package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.ConfirmationsEntity;



@Repository
public interface TestsRepository extends CrudRepository<ConfirmationsEntity,Long>
{

}
