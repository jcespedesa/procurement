package com.trc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry
	        .addResourceHandler("/quotes/**","/periodicals/**") 
	        .addResourceLocations("file:C:\\Public\\procurementDocs\\scanDocs\\quotes\\","file:C:\\Public\\procurementDocs\\scanDocs\\periodicals\\")
	        	        	        
	        .setCachePeriod(0);
		
	}
	
}
