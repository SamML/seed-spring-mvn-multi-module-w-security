package com.smdev.smsj.security.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
@Primary
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
		http.httpBasic().and().authorizeRequests().antMatchers("/index.html", "/", "/**", "/h2", "/h2/**", "/h2**", "h2").permitAll();
     
		
		
//		http.httpBasic().and().authorizeRequests().antMatchers("/index.html", "/", "/h2**", "/login").permitAll()
//        .anyRequest().authenticated().and().csrf()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//http.cors().and().authorizeRequests().anyRequest().authenticated();
//
//http.authorizeRequests().anyRequest().hasAnyRole("ADMIN").and().authorizeRequests().antMatchers("/login**")
//        .permitAll().and().formLogin().loginPage("/login") /* Specifies the login page URL */
//        .loginProcessingUrl("/login") /*
//                                       * Specifies the URL,which is used in action attribute on the <from> tag
//                                       */
//        .usernameParameter("username") /*
//                                        * Username parameter, used in name attribute of the <input> tag. Default
//                                        * is 'username'.
//                                        */
//        .passwordParameter("password") /*
//                                        * Password parameter, used in name attribute of the <input> tag. Default
//                                        * is 'password'.
//                                        */
//        .successHandler((req, res, auth) -> { /* Success handler invoked after successful authentication */
//            for (GrantedAuthority authority : auth.getAuthorities()) {
//                System.out.println(authority.getAuthority());
//            }
//            System.out.println(auth.getName());
//            res.sendRedirect("/"); /* Redirect user to index/home page */
//        }).defaultSuccessUrl("/") /*
//                                   * URL, where user will go after authenticating successfully. Skipped if
//                                   * successHandler() is used.
//                                   */
//        .failureHandler((req, res, exp) -> { /* Failure handler invoked after authentication failure */
//            String errMsg = "";
//            if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
//                errMsg = "Invalid username or password.";
//            } else {
//                errMsg = "Unknown error - " + exp.getMessage();
//            }
//            req.getSession().setAttribute("message", errMsg);
//            res.sendRedirect("/login"); /* Redirect user to login page with error message. */
//        }).failureUrl("/login?error") /*
//                                       * URL, where user will go after authentication failure. Skipped if
//                                       * failureHandler() is used.
//                                       */
//        .permitAll() /* Allow access to any URL associate to formLogin() */
//        .and().logout().logoutUrl("/signout") /* Specifies the logout URL, default URL is '/logout' */
//        .logoutSuccessHandler((req, res, auth) -> { /* Logout handler called after successful logout */
//            req.getSession().setAttribute("message", "You are logged out successfully.");
//            res.sendRedirect("/login"); /* Redirect user to login page with message. */
//        }).logoutSuccessUrl("/login") /*
//                                       * URL, where user will be redirect after successful logout. Ignored if
//                                       * logoutSuccessHandler() is used.
//                                       */
//        .permitAll() /* Allow access to any URL associate to logout() */
//        .and().csrf().disable(); /* Disable CSRF support */
		
		
//      http
//      .csrf().disable()
//          .formLogin()
//              .loginProcessingUrl("/auth/login")
//              .successHandler(loginSuccessfulHandler)
//              .failureHandler(loginFailureHandler)
//          .and()
//              .logout()
//              .deleteCookies("JSESSIONID")
//              .logoutUrl("/auth/logout")
//              .logoutSuccessHandler(logoutSuccessfulHandler)
//          .and()
//            .authorizeRequests()
//            .antMatchers("/auth/login", "/h2**", "/", "/index.html").permitAll()
//            .antMatchers("/secure/admin").access("hasRole('ADMIN')")//.access("hasAuthority('ROLE_ADMIN')")
//            .anyRequest().authenticated()
//           .and()
//             .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
//             .authenticationEntryPoint(customAuthenticationEntryPoint)
//          .and()
//            .anonymous()
//              .disable();
//      
      
  // @formatter:on
	}

}