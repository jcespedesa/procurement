package com.trc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trc.entities.PhoneAddress;

public interface PhoneAddrRepository  extends JpaRepository<PhoneAddress, Long>
{

}
