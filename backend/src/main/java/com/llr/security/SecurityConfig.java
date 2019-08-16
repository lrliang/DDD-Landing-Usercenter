package com.llr.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()


//      .antMatchers("/api/users/token/**","/api/users/login").permitAll()
//      .antMatchers("/**/api/**")
//      .authenticated()

      .anyRequest().permitAll()
      .and()
      .addFilterBefore(new JWTFilter(), BasicAuthenticationFilter.class)
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.headers().frameOptions().sameOrigin();
  }
}
