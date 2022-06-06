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
		
		try
		{
		
			//Retrieving stored password for this email
			storedPassword=repository.getPassByEmail(email);
		
		}catch (Exception e)
		{
			System.out.println("Critical Error: Repeated email was found: "+ email);
		}
		
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
	
	
	public UsersEntity getITapprover() 
	{
		
		UsersEntity itApprover=repository.getITapprover();
		
		
		return itApprover;
	   
	}
	
	public UsersEntity getFOapprover() 
	{
		UsersEntity foApprover=repository.getFOapprover();
		
		
		return foApprover;
	   
	}
	
	public UsersEntity getPpc() 
	{
		UsersEntity ppc=repository.getPpc();
		
		
		return ppc;
	   
	}
	
	public String checkUniqueITrole() 
	{
		int priznak=0;
		
		String priznakUnique=null;
		
		//Checking uniqueness
		priznak=repository.getUniqueIT();
		
		
		if(priznak==0)
			priznakUnique="Yes";
		else
			priznakUnique="No";
				
		
		return priznakUnique;
	   
	}
	
	public String checkUniqueFOrole() 
	{
		int priznak=0;
		
		String priznakUnique=null;
		
		//Checking uniqueness
		priznak=repository.getUniqueFO();
		
		
		if(priznak==0)
			priznakUnique="Yes";
		else
			priznakUnique="No";
				
		
		return priznakUnique;
	   
	}
	
	public String checkUniquePPCrole() 
	{
		int priznak=0;
		
		String priznakUnique=null;
		
		//Checking uniqueness
		priznak=repository.getUniquePPC();
		
		
		if(priznak==0)
			priznakUnique="Yes";
		else
			priznakUnique="No";
				
		
		return priznakUnique;
	   
	}


	public String updateRoles(Long userId, String priznakITapprover, String priznakFOapprover, String priznakPpc) 
	{
		int priznak=0;
		int counter=0;
		
		boolean priznakOperationOK=false;
		
		String message=null;
		
		//Checking one more time if the selected roles are not taken
		//And if there is not more than one assignment
		if(priznakITapprover.equals("Yes"))
		{
			priznak=repository.getUniqueIT();
			
			if(priznak==0)
			{	
				priznakOperationOK=true;
				priznak=0;
				counter++;
				
				//System.out.println("IT assignment detected");
			}	
			
		}
		
		if(priznakFOapprover.equals("Yes"))
		{
			priznak=repository.getUniqueFO();
			
			if(priznak==0)
			{	
				priznakOperationOK=true;
				priznak=0;
				counter++;
				
				//System.out.println("FO assignment detected");
			}	
			
		}
		
		if(priznakPpc.equals("Yes"))
		{
			priznak=repository.getUniquePPC();
			
			if(priznak==0)
			{	
				priznakOperationOK=true;
				priznak=0;
				counter++;
				
				//System.out.println("PPC assignment detected");
			}	
			
		}
				
		if((priznakOperationOK)&&(counter==1))
		{
			//Updating profiles in table secure
			repository.updateRoles(userId,priznakITapprover,priznakFOapprover,priznakPpc);
			message="Assignment of new role was successful";
		}
		else
			message="Error: double assignment or occupied role was detected. Please review the roles assignment again...";
		
		
		return message;
		
	}


	public String demoteById(Long userId, String priznakRole) 
	{
		String message=null;
		
		switch(priznakRole) 
        {
            case "priznakITapprover":
                repository.resetPriznakITapprov(userId);
                message="IT Approval Role was demoted";
            break;
            
            case "priznakFOapprover":
                repository.resetPriznakFOapprov(userId);
                message="FO Approval Role was demoted";
            break;
            
            case "priznakPpc":
                repository.resetPriznakPpc(userId);
                message="PPC Role was demoted";
            break;
            
            default:
                message="No priznak found...";
            break;
        }        
		
		return message;
	}

	
}
