package com.sri.lanka.traffic.webapp.config.authentication;

import com.sri.lanka.traffic.webapp.config.authentication.code.CodeAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private CodeAuthenticationService codeAuthenticationService;
    
    @Autowired(required = false)
    private HttpServletRequest request;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	String loginType = request.getParameter("loginType");
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();
        UserDetails userDetails = codeAuthenticationService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, credentials, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
