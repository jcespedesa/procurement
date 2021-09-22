package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.ReceiptsEntity;


@Repository
public interface ReceiptsRepository extends CrudRepository<ReceiptsEntity,Long>
{

}
