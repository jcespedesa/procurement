package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trc.entities.UsersEntity;
import com.trc.repositories.SettingsRepository;
import com.trc.repositories.UsersRepository;


@Service
public class UsersService 
{
	@Autowired
	UsersRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	SettingsRepository repositorySettings;
	
	public List<UsersEntity> getAllUsers()
	{
		List<UsersEntity> result=(List<UsersEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<UsersEntity>();
		
	}
	
	
	public UsersEntity createOrUpdate(UsersEntity entity)
	{
		if(entity.getUserid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<UsersEntity> user=repository.findById(entity.getUserid());
			
			if(user.isPresent())
			{
				
				UsersEntity newEntity=user.get();
				
				newEntity.setUsername(entity.getUsername());
				newEntity.setEmail(entity.getEmail());
				newEntity.setRole(entity.getRole());
				newEntity.setDomain(entity.getDomain());
				
				newEntity.setActive(entity.getActive());
								
				newEntity=repository.save(newEntity);
				
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deleteUserById(Long id) throws RecordNotFoundException
	{
		Optional<UsersEntity> user=repository.findById(id);
		
		if(user.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No user record exist for given id");
		
		
	}
	
	public UsersEntity getUserById(Long id)
	{
		UsersEntity user=repository.getUserById(id);
		
		return user;
		
	}
	
	public void resetPass(Long id) 
    {
    	String encodedPass=null;
    	String param1="defaultPass";
    	String newPassword=null;
    	    	
    	//Trying to set client's password to the default one
    	
    	//Retrieving the default password
    	newPassword=repositorySettings.getDefaultPass(param1);
    	
    	//Encoding is disabled for the moment
    	//encodedPass=newPassword;
    	
    	//Encoding the default password
        encodedPass=this.encodePass(newPassword);
                 	
    	repository.setDefaultPass(id,encodedPass);
    	    	    	
    	    	    	
    }
	
	public void setPass(Long id,String newPass) 
    {
    	    	
    	String encodedPass=null;
    	    	
    	//Trying to set user's new password
    	
    	//Encoding the sent password
        encodedPass=passwordEncoder.encode(newPass);
    	
    	repository.setDefaultPass(id,encodedPass);
    	
    	    	
    	
    }




	public UsersEntity getUserByEmail(String email) 
	{
				
		UsersEntity user=repository.getUserByEmail(email);
		
		return user; 
	}


		
	public Boolean checkPass(String email, String password) 
	{
		
		Boolean priznakSuccess=false;
		String storedPassword=null;
		
		//Retrieving stored password for this email
		storedPassword=repository.getPassByEmail(email);
		
		//Coding input password
		//password=this.encodePass(password);
		
		
		if(storedPassword==null)
		{	
			System.out.println("No email was found: "+ email);
			
		}	
		else
		{
				//checking if the input password matches with the stored password
		    	boolean isPasswordMatch=passwordEncoder.matches(password,storedPassword);
		    	
		    	if(isPasswordMatch)
					priznakSuccess=true;
		
		}
				
		return priznakSuccess;
	}
	
	
	
	public String checkString(String str) 
	{
		
	    int i=0;
		
		char ch;
	    
	    boolean capitalFlag=false;
	    boolean numberFlag=false;
	    
	    for(i=0;i<str.length();i++) 
	    {
	        ch=str.charAt(i);
	        
	        if(Character.isDigit(ch)) 
	        {
	            numberFlag=true;
	        }
	        else 
	        	if(Character.isUpperCase(ch)) 
	        	{
	        		capitalFlag=true;
	        		
	        	} 
	        	if(numberFlag && capitalFlag)
	        		return "true";
	      }
	      return "false";
	}
	
	public String encodePass(String password) 
	{
		String encodedPass=null;		
		
		encodedPass=passwordEncoder.encode(password);
		
		return encodedPass; 
	}

	public int findDuplicates(String email)
	{
		int priznakDuplicate=0;
		
		priznakDuplicate=repository.findEmailDuplicity(email);
		
		
		return priznakDuplicate;
	}
	
	
	public String createAccessCode(Long id)
	{
		int min=100000;
		int max=999999;
		int passInt=0;
		
		String passNum=null;
		String passString=null;
		String pass=null;
		
		Random r=new Random();
        passInt=r.nextInt((max-min)+1)+ min;
        
      //Converting the password int to password string
		passNum=Integer.toString(passInt);
		
		//Trying to get a random symbol
		passString=this.generateRandomSymbol();
		
		//Concatenation of the password
		pass=passNum+passString;
		
		return pass;
	}
	
	public String generateRandomSymbol() 
    {
    	String[] arr={"!","@","#","$","%","*","&","?"};
    	
    	int idx=new Random().nextInt(arr.length);
    	String s=(arr[idx]);
    	
    	
        return s;
    }
	
}
