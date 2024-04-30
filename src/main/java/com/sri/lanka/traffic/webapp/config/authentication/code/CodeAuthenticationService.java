package com.sri.lanka.traffic.webapp.config.authentication.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.querydsl.QTmExmnMngRepository;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

@Service
public class CodeAuthenticationService implements UserDetailsService {

    @Autowired
    QTmExmnMngRepository qTmExmnMngRepository;
    
    @Value("${srilanka.auth.code.role}")
    public String AUTH_CODE_ROLE;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	LoginExmnDTO result = qTmExmnMngRepository.getInvstInfoByPartcptCd(username); 
    	
        CodeAuthenticationEntity codeAuthenticationEntity = null;

        if(!CommonUtils.isNull(result)){
        	codeAuthenticationEntity = new CodeAuthenticationEntity(result);
        	codeAuthenticationEntity.setUserId(result.getPartcptCd());
        	codeAuthenticationEntity.setUserAuth(AUTH_CODE_ROLE);
        	codeAuthenticationEntity.setUserPswd(result.getPartcptCd());
        	
        }else{
            throw new UsernameNotFoundException("참여코드 정보 및 조사 일정을 확인해주세요.");
        }
        return codeAuthenticationEntity;
    }
}
