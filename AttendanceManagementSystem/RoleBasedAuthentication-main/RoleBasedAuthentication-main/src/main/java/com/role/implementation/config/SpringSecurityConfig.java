package com.role.implementation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.role.implementation.service.DefaultUserServiceImpl;


@SuppressWarnings("deprecation")
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DefaultUserServiceImpl customUserDetailsService;
	
	@Autowired
	AuthenticationSuccessHandler successHandler;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws 
                                                Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
        		"/registration",
        		"/user-details**",
        		//"userDeatils**",
				"/view-leave-requests**",
				"/leave-requests/{id}/approve**",
				"/leave-requests/{id}/reject",
				"/userPage/apply-leave**", 
				"/userPage/view-leave-status**",
				"/users/edit/**",
				"/users/**",
				"/admin-home**",
				"/css",
				"/js",
				"/adminHome")
        		.permitAll()
        		.anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/login")
                .successHandler(successHandler)
                .permitAll().and().logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();

    }
}
