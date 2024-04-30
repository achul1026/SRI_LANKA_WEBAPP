package com.sri.lanka.traffic.webapp.config.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sri.lanka.traffic.webapp.common.entity.TcUserMng;
import com.sri.lanka.traffic.webapp.config.authentication.admin.AdminAuthenticationService;
import com.sri.lanka.traffic.webapp.config.authentication.code.CodeAuthenticationService;

@Component
public class WebAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @Autowired
    private CodeAuthenticationService codeAuthenticationService;
    
    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder 추가
    
    @Autowired(required = false)
    private HttpServletRequest request;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	String loginType = request.getParameter("loginType");
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();
        
        UserDetails userDetails = null;
        //TODO:: 기획서 회원 로그인 방식 확인 후 주석 해제
//        if ("code".equals(loginType)) {
//        	TcUserMng tcUserMng = new TcUserMng();
        	userDetails = codeAuthenticationService.loadUserByUsername(username);
//        } else {
//            userDetails = adminAuthenticationService.loadUserByUsername(username);
//            String password = credentials.toString();
//            // 비밀번호를 암호화하여 DB 정보와 비교
//            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//                throw new BadCredentialsException("Invalid username or password");
//            }
//        }
        return new UsernamePasswordAuthenticationToken(userDetails, credentials, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
