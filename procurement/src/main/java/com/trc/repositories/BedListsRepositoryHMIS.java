package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.BedListsEntityHMIS;

@Repository
public interface BedListsRepositoryHMIS extends CrudRepository<BedListsEntityHMIS,Long>
{

}
