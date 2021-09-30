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
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		log.info("security config..............");
//
//	//	http.authorizeRequests().antMatchers("/guest/**").permitAll();
//
//		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
////
//		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
//		
//		// http.formLogin();
//		http.formLogin().loginPage("/login");
//
//		http.exceptionHandling().accessDeniedPage("/accessDenied");
//
//		// http.logout().invalidateHttpSession(true);
//		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
//
//		//http.userDetailsService(zerockUsersService);
//
//	
//
//	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	                .antMatchers("/manager/**").hasRole("MANAGER")
	                .antMatchers("/admin/**").hasRole("ADMIN");

	        http.formLogin();
	        http.exceptionHandling().accessDeniedPage("/accessDenied");
	        http.logout().logoutUrl("/logout").invalidateHttpSession(true);

	    }
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	 
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        log.info("build Auth global.......");

	        auth.inMemoryAuthentication()
	                .withUser("manager")
	                .password("{noop}1111")
	                .roles("MANAGER");
	    }
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(zerockUsersService);
//		/*
//		 스프링 시큐리티가 데이터베이스를 연동하는 방법은 크게 
//		 1) 직접 SQL 등을 지정해서 처리하는 방법 
//		 2) 기존에 작성된 Repository나 서비스 객체들을 이용해서 별도로 시큐리티 관련 서비스를 개발하는 방법  
//		 */
//
//
//		String query1 = "SELECT uid username, upw password, true enabled FROM tbl_members WHERE uid= ?";
//		
//		String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";
//		
//		/*
//		 사용자에 대한 계정 정보와 권한을 체크하는 부분에서는 DataSource를 이용하고
//		 SQL을 지정하도록 작성합니다. 
//		 이 때의 SQL문은 
//		 1) 사용자의 계정 정보를 이용해서 필요한 정보를 가져오는 SQL
//		 2) 해당 사용자의 권한을 확인하는 SQL 
//
//		 auth.jdbcAuthentication() 메소드는 JdbcUserDetailsManagerConfigurer객체를 반환 
//		 이 객체를 이용해서 DataSource를 주입
//		 필요한 SQL문을 파라미터로 전달하는 방식을 이용해 단순한 몇 가지 설정만으로 인증 메니저생성 
//		 
//		 가장 핵심이 되는 메소드는 userByUsernameQuery()와 authoritiesByUsernameQuery()라는 메소드입니다.
//		 username을 이용해서 특정한 인증 주체(사용자)정보를 세팅하고, username을 이용해서 권한에 대한 정보를 조회합니다. 
//		 */
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.usersByUsernameQuery(query1)
//		.rolePrefix("ROLE_")
//		.authoritiesByUsernameQuery(query2);
//	}
//
	

}
