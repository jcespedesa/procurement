package com.trc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.repositories.TestsRepository;


@Service
public class TestsService 
{
	@Autowired
	TestsRepository repository;
	
}
