package com.weatherapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.weatherapi.security.AuthEntryPointJwt;
import com.weatherapi.security.AuthTokenFilter;
import com.weatherapi.service.UserService;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	private  String[] paths= {
			"/v2/api-docs", "/webjars/**", "/metrics", "/monitor", "/ping", "/health",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/swagger-resources",
			"/swagger-resources/configuration/ui", "/swagger-resources/configuration/security", "/console/**","/login","/signup","/h2/**", "/favicon.ico/**"
	};
	
	@Autowired 
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean 
	public AuthTokenFilter authenticationJwtTokenFilter() { 
		return new AuthTokenFilter(); 
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().headers().frameOptions().disable().and().csrf().disable().exceptionHandling().
		         authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(paths)
				.permitAll().antMatchers("/**").authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
