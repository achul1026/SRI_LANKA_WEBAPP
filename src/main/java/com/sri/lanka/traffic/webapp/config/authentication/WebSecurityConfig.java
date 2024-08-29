package com.sri.lanka.traffic.webapp.config.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value("${srilanka.auth.mngr.role}")
	public String AUTH_MNGR_ROLE;
    
    @Value("${srilanka.auth.code.role}")
    public String AUTH_CODE_ROLE;
    
    @Autowired
    MessageSource messageSource;
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    private WebAuthenticationProvider webAuthenticationProvider;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//http.requiresChannel().anyRequest().requiresSecure();
    	
        http.csrf().disable();
        
        //추후 수정
        http.authorizeRequests()
                .antMatchers("/login/**","/common/**","/css/**","/js/**","/fonts/**","/images/**","/error").permitAll()
                .antMatchers("/**").hasAnyRole(AUTH_MNGR_ROLE,AUTH_CODE_ROLE)
                .anyRequest().authenticated();
        
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/surveyor/confirm")
                .usernameParameter("userId")
                .passwordParameter("userPswd")
                .defaultSuccessUrl("/main",true)
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

        /*
        중복 로그인 방지
        http.sessionManagement()
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true)
            .expiredUrl("/login");*/
        
        http.exceptionHandling(exception -> exception
                .accessDeniedPage("/error")
            );

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(webAuthenticationProvider);
    }


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new LoginFailureHandler(messageSource);
    }
}
