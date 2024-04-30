package com.sri.lanka.traffic.webapp.config.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginMngrDTO;
import com.sri.lanka.traffic.webapp.common.enums.code.MngrSttsCd;
import com.sri.lanka.traffic.webapp.common.querydsl.QTcUserMngRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    QTcUserMngRepository qTcUserMngRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	LoginMngrDTO result = qTcUserMngRepository.getTcUserInfoByUserId(username); 

        AuthenticationEntity authenticationEntity = null;

        if(!CommonUtils.isNull(result) && !CommonUtils.isNull(result.getUserPswd())){
        	if(!MngrSttsCd.APPROVAL.equals(result.getAthrztStts())) {
        		//TODO:: 예외 처리
        		
        	}
        	authenticationEntity = new AuthenticationEntity(result);
        	authenticationEntity.setUserId(result.getUserId());
        	authenticationEntity.setUserAuth(result.getUserAuth());
        	authenticationEntity.setUserPswd(result.getUserPswd());
        	
        }else{
            throw new UsernameNotFoundException(username);
        }
        
        return authenticationEntity;
    }
}
