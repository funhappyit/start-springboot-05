package org.zerock.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	ZerockUsersService zerockUsersService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		log.info("security config..............");

		http.authorizeRequests().antMatchers("/guest/**").permitAll();

		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

		// http.formLogin();
		http.formLogin().loginPage("/login");

		http.exceptionHandling().accessDeniedPage("/accessDenied");

		// http.logout().invalidateHttpSession(true);
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);

		http.userDetailsService(zerockUsersService);

	

	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		/*
		 스프링 시큐리티가 데이터베이스를 연동하는 방법은 크게 
		 1) 직접 SQL 등을 지정해서 처리하는 방법 
		 2) 기존에 작성된 Repository나 서비스 객체들을 이용해서 별도로 시큐리티 관련 서비스를 개발하는 방법  
		 */


		String query1 = "SELECT uid username, upw password, true enabled FROM tbl_members WHERE uid= ?";
		
		String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";
		
		/*
		 사용자에 대한 계정 정보와  
		  
		  
		  
		 */
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(query1)
		.rolePrefix("ROLE_")
		.authoritiesByUsernameQuery(query2);
	}
	

	

}
