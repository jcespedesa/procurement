package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.ItemsEntity;

@Repository
public interface ItemsRepository extends CrudRepository<ItemsEntity,Long> 
{
	@Query("Select u from ItemsEntity u Order by item")
	List<ItemsEntity> getAllDesc();
	
	@Query("Select u from ItemsEntity u Where u.klass='Main'")
	List<ItemsEntity> getAllMain();
	
	@Query("Select u from ItemsEntity u Where u.klass='Peripheral'")
	List<ItemsEntity> getAllPeripherals();
	
	@Query("Select u from ItemsEntity u Where (u.klass='Peripheral' and u.hhsForm='Yes')")
	List<ItemsEntity> getAllPeripheralsHHS();
	
	@Query("Select u.item from ItemsEntity u Where u.itemid=?1")
	String getItemDescById(Long itemId);
	
	@Query("Select u from ItemsEntity u Where u.item=?1")
	ItemsEntity getByDescription(String description);
}
