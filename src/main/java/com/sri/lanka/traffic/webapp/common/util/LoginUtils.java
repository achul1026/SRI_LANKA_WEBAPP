package com.sri.lanka.traffic.webapp.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.enums.code.ExmnTypeCd;
import com.sri.lanka.traffic.webapp.support.exception.ErrorCode;
import com.sri.lanka.traffic.webapp.support.exception.NoLoginException;

/**
 * 설명
 * @author : KY.LEE
 * @fileName :  LoginSessionUtils
 * @since : 2023.9.11
 */
@Component
public class LoginUtils {

	
	public LoginUtils() {
		super();
	}
	
	/**
	  * @Method Name : getSessionRequest
	  * @작성일 : 2023. 9. 11.
	  * @작성자 : KY.LEE
	  * @Method 설명 : 현재 세션 값 조회
	  * @return HttpServletRequest
	  */
	public static HttpServletRequest getSessionRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	  * @Method Name : getOprtrNm
	  * @작성일 : 2023. 9. 11.
	  * @작성자 : KY.LEE
	  * @Method 설명 : 로그인 사용자 정보 조회
	  * @return String
	  */
	public static LoginExmnDTO getInvstInfo() {
		LoginExmnDTO exmnInfo = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !"anonymousUser".equals(authentication.getPrincipal())) {
			exmnInfo = (LoginExmnDTO) authentication.getPrincipal();
		} else {
			throw new NoLoginException(ErrorCode.NOT_FOUNT_INVST_INFO);
		}
		return exmnInfo;
	}
	
	
	
	/**
	  * @Method Name : getExmnmngId
	  * @작성일 : 2023. 9. 11.
	  * @작성자 : KY.LEE
	  * @Method 설명 : 로그인 조사 PK get
	  * @return String
	  */
	public static String getExmnmngId() {
		if(getInvstInfo()!= null) {
			return getInvstInfo().getExmnmngId();
		} else {
			throw new NoLoginException(ErrorCode.NOT_FOUNT_INVST_INFO);
		}
	}
	
	/**
	  * @Method Name : getExmnmngTypeCd
	  * @작성일 : 2024. 4. 8.
	  * @작성자 : NK.KIM
	  * @Method 설명 : 타입 가져오기
	  * @return
	  */
	public static ExmnTypeCd getExmnmngTypeCd() {
		if(getInvstInfo()!= null) {
			return getInvstInfo().getExmnType();
		} else {
			throw new NoLoginException(ErrorCode.NOT_FOUNT_INVST_INFO);
		}
	}
	

	
	
	/**
	 * @Method Name : getUserIpAddr
	 * @작성일 : 2023. 9. 25.
	 * @작성자 : KY.LEE
	 * @Method 설명 : 현재 세션에 접속한 유저의 IP를 가져오는 메소드
	 * @return String
	 */
	public static String getUserIpAddr() {
		String ip = null;
		HttpServletRequest request = getSessionRequest();
		
		ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        }
        
		return ip;
	}
}