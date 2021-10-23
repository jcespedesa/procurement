package com.trc.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry
	        .addResourceHandler("/quotes/**","/periodicals/**","/scanPics/**") 
	        .addResourceLocations("file:C:\\Public\\procurementDocs\\scanDocs\\quotes\\",
	        		"file:C:\\Public\\procurementDocs\\scanDocs\\periodicals\\",
	        		"file:C:\\Public\\procurementDocs\\scanPics\\"
	        		)
	        	        	        
	        .setCachePeriod(0);
		
	}
	
	//@Bean
    //public PasswordEncoder passwordEncoder() 
	//{
        //return new BCryptPasswordEncoder();
    //}
	
}
