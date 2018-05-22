package com.smdev.smsj.security.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.smdev.smsj.security.config.handler.CustomAccessDeniedHandler;
import com.smdev.smsj.security.config.handler.CustomAuthenticationEntryPoint;
import com.smdev.smsj.security.config.handler.CustomLoginFailureHandler;
import com.smdev.smsj.security.config.handler.CustomLoginSuccessfulHandler;
import com.smdev.smsj.security.config.handler.CustomLogoutSuccessfulHandler;
import com.smdev.smsj.security.service.UserDetailsServiceImpl;

/**
 * @author sm, in 2018
 *
 *         |> WebSecurityConfig ~~ [com.smdev.smsj.security.config]
 * 
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomLoginSuccessfulHandler loginSuccessfulHandler;
	@Autowired
	private CustomLoginFailureHandler loginFailureHandler;
	@Autowired
	private CustomLogoutSuccessfulHandler logoutSuccessfulHandler;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@Resource(name = "userDetailsServiceImpl")
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//@formatter:off
      auth.userDetailsService(userDetailsService)
        .and().jdbcAuthentication().dataSource(securityDataSource);
  // @formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//@formatter:off
      http
      .csrf().disable()
          .formLogin()
              .loginProcessingUrl("/auth/login")
              .successHandler(loginSuccessfulHandler)
              .failureHandler(loginFailureHandler)
          .and()
              .logout()
              .deleteCookies("JSESSIONID")
              .logoutUrl("/auth/logout")
              .logoutSuccessHandler(logoutSuccessfulHandler)
          .and()
            .authorizeRequests()
            .antMatchers("/auth/login", "/h2**").permitAll()
            .antMatchers("/secure/admin").access("hasRole('ADMIN')")//.access("hasAuthority('ROLE_ADMIN')")
            .anyRequest().authenticated()
           .and()
             .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
             .authenticationEntryPoint(customAuthenticationEntryPoint)
          .and()
            .anonymous()
              .disable();
  // @formatter:on
	}

}