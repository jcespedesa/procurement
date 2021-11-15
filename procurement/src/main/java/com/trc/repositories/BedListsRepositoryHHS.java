package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.BedListsEntityHHS;

@Repository
public interface BedListsRepositoryHHS extends CrudRepository<BedListsEntityHHS,Long>
{
	@Query("Select u from BedListsEntityHHS u Where kluch=?1")
	List<BedListsEntityHHS>  getSessionList(String kluch);
	
	@Query("Select u from BedListsEntityHHS u Where kluch=?1 Order by room,bedCorrection")
	List<BedListsEntityHHS>  getBedListByKluch(String kluch);
}
