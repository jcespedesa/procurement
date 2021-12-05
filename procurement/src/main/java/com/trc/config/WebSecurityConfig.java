package com.trc.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter
{
	protected void configure(HttpSecurity http) throws Exception 
	{
		
		http.authorizeRequests()
		
			.antMatchers("/portal/passSendForm").permitAll()
			.antMatchers("/**").authenticated()
            
			.anyRequest().authenticated()
			
			//Login Form Details
			.and()
			.formLogin()
			.defaultSuccessUrl("/procurement/index", true)
			
			//Logout Form Details
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/portal/logout"))
					
			//Exception Details		
			.and()	
			.exceptionHandling()
			.accessDeniedPage("/accessDenied")
			;
	}
		
}
