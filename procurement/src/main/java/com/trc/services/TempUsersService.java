package com.trc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.TempUsersEntity;
import com.trc.repositories.TempUsersRepository;

@Service
public class TempUsersService 
{

	@Autowired
	TempUsersRepository repository;
	
	public void saveNewTempUser(String email,String password,String role)
	{
		String active="Yes";
		
		TempUsersEntity user=new TempUsersEntity();
		
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(role);
		user.setActive(active);
		
		repository.save(user);
				
	}

	public Boolean checkPass(String email, String password) 
	{
		
		Boolean priznakSuccess=false;
		String storedPassword=null;
		
		//Retrieving stored password for this email
		storedPassword=repository.getPassByEmail(email);
		
		
		if(storedPassword==null)
		{	
			System.out.println("No email was found: "+ email);
			
		}	
		else
			if(storedPassword.equals(password))
				priznakSuccess=true;
		
				
		return priznakSuccess;
	}
	
	public Boolean checkNewUser(String email) 
	{
		
		Boolean priznakNewUser=false;
							
		//Checking if this email already exists in the table
		priznakNewUser=repository.existsByEmail(email);
		
		if(priznakNewUser)
			System.out.println("This user does exists...");
		else
			System.out.println("This user is new...");
		
		return priznakNewUser;
	}
	
	public void updatePass(String email, String password)
	{
		repository.updatePassword(email,password);
	}
}
