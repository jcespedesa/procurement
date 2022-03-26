package com.trc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter
{
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
		web.ignoring().antMatchers("/portal/apiEmailSwitch");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		
	    http.sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        .invalidSessionUrl("/procurement/login");
	    
	    	http.cors().and().csrf().disable();
	    
	}
	
	
	
	@Bean
    public PasswordEncoder passwordEncoder() 
	{
        return new BCryptPasswordEncoder();
    }
		
}
