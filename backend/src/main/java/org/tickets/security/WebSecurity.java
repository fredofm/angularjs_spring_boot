package org.tickets.security;

import static org.tickets.security.SecurityConstants.SIGN_UP_URL;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.tickets.security.SecurityConstants.AUTHORIZATION_HEADER_STRING;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
 /*   private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/

    public WebSecurity() {
       // this.userDetailsService = userDetailsService;
       // this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web)
        throws Exception {
      
          web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/").loginProcessingUrl(SIGN_UP_URL)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
       // with this option you must implement a UserDetailService

       auth.inMemoryAuthentication()
        .withUser("admin")
        .password("password")
        .roles("ADMIN");
    }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    /*final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;*/

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(AUTHORIZATION_HEADER_STRING, "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", AUTHORIZATION_HEADER_STRING));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
  }
}