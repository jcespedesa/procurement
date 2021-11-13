package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.BedListsEntityHHS;

@Repository
public interface BedListsRepositoryHHS extends CrudRepository<BedListsEntityHHS,Long>
{

}
