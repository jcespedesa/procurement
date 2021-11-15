package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.BedListsEntityHMIS;

@Repository
public interface BedListsRepositoryHMIS extends CrudRepository<BedListsEntityHMIS,Long>
{
	@Query("Select u from BedListsEntityHMIS u Where kluch=?1 Order by room,bed")
	List<BedListsEntityHMIS>  getBedListByKluch(String kluch);
}
