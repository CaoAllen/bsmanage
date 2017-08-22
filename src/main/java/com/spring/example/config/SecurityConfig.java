package com.spring.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.spring.example.service.CustomSaltSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired  
    @Qualifier("customUserDetailsService")  
    UserDetailsService userDetailsService;  
	
    @Autowired  
    DataSource dataSource;  
    
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
            .ignoring()
                    .antMatchers("/**/*.html", //
            		 "/app/**",//
            		 "/components/**",//
                     "/css/**", //
                     "/fonts/**",//
                     "/js/**", //
                     "/i18n/**",// 
                     "/libs/**",//
                     "/img/**", //
                     "/webjars/**",//
                     "/ico/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and()
			.logout().and()
			.authorizeRequests()
            	.antMatchers("/api/ping").permitAll()  
            	.antMatchers("/api/logout").permitAll()  
            	.antMatchers("/api/wx/**").permitAll()  
				.antMatchers("/home.html", "/login.html", "/").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf().disable();
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Autowired  
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {  
//        auth.userDetailsService(userDetailsService);  
		auth.authenticationProvider(authenticationProvider());
    }  
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(userDetailsService);
	    authenticationProvider.setPasswordEncoder(passwordEncoder()); 
//	    authenticationProvider.setSaltSource(saltSource());
	    return authenticationProvider;
	}

    @Bean  
    public PersistentTokenRepository persistentTokenRepository() {  
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();  
        tokenRepositoryImpl.setDataSource(dataSource);  
        return tokenRepositoryImpl;  
    }  

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder() ;
	}
	
	@Bean
	public SaltSource saltSource() {
	    return new CustomSaltSource();
	}

}
