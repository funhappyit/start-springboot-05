package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import lombok.extern.java.Log;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config...........");
		
		/*
		  authorizeRequests()는 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미
		  antMatches()에서는 특정한 경로를 지정합니다.
		  permitAll()은 모든 사용자가 접근할 수 있다는 것을 의미하고 
		  hasRole()은 시스템상에서 특정 권한을 가진 사람만이 접근할 수 있다는 것을 의미합니다.
		 */
		 
		http.authorizeRequests().antMatchers("/guest/**").permitAll();
		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		http.formLogin().loginPage("/login");
		
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		//세션 무효화
		/*
		 스프링 시큐리티가 웹을 처리하는 방식의 기본은 HttpSession이므로 브라우저가 완전히 종료되면, 
		 로그인한 정보를 잃게 됩니다. 문제는 브라우저를 종료하지 않을 경우입니다. 이 경우 사용자 로그아웃을 
		 행해서 자신이 로그인했던 모든 정보를 삭제해야 합니다.
		 */
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
		
		
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.inMemoryAuthentication()
		.withUser("manager")
		.password("{noop}1111")
		.roles("MANAGER");
	}
	
	

}
