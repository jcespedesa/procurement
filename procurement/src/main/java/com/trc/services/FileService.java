package com.trc.services;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.trc.repositories.SettingsRepository;

@Service
public class FileService 
{
	@Autowired
	SettingsRepository repository;
	
	
	public String uploadDirAux="";
	public String uploadDir="";
	public String idString="";
	
	
	public void uploadFile(MultipartFile file, Long id, String trail)
	{
		
		String podcherk="/";
		
		String dirTrail=trail+podcherk;
		
		idString=String.valueOf(id);
		
		//Getting the application path
		uploadDirAux=repository.getAppPath("appHome");
		
		uploadDir=uploadDirAux+dirTrail+idString;
		
		try
		{
			Path copyLocation=Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			Files.copy(file.getInputStream(),copyLocation,StandardCopyOption.REPLACE_EXISTING);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new FileSystemNotFoundException("Could not store file "+ file.getOriginalFilename() +". Please try again!");
			
		}
		
	}
	
	public void deleteFile(String fileName,Long id, String trail)
	{
		String dirPath="";
		String fullPath="";
		String dirTrail=null;
		String podcherk="/";
		
		
		//Getting the complete dir trail
		
		dirTrail=trail+podcherk;
		
		//Converting id long to id string
		idString=String.valueOf(id);
		
		//Getting the application main path
		dirPath=repository.getAppPath("appHome");
		
		fullPath=dirPath+dirTrail+idString+podcherk+fileName;
		
		try
		{
			File file=new File(fullPath);
			
			if(file.delete())
			{	
				System.out.println(file.getName() + " was deleted!");
				
			}
			else
			{
				System.out.println("Delete operation have failed");
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	
	

}
