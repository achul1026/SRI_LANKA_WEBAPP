package com.sri.lanka.traffic.webapp.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.support.exception.NoLoginException;

/**
 * 설명
 * @author : KY.LEE
 * @fileName :  LoginSessionUtils
 * @since : 2023.9.11
 */
public class LoginExmnUtils {

	
	public LoginExmnUtils() {
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
	public static LoginExmnDTO getTmExmnMngInfo() {
		LoginExmnDTO tcUserMngInfo = null;
		// 현재 인증된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !"anonymousUser".equals(authentication.getPrincipal())) {
			tcUserMngInfo = (LoginExmnDTO) authentication.getPrincipal();
		} else {
//			throw new NoLoginException(ErrorCode.SESSION_NOT_FOUND);
		}
		return tcUserMngInfo;
	}
	
	/**
	  * @Method Name : getOprtrNm
	  * @작성일 : 2023. 9. 11.
	  * @작성자 : KY.LEE
	  * @Method 설명 : 로그인 조사명 조회
	  * @return String
	  */
	public static String getExmnNm() {
		if(getTmExmnMngInfo()!= null) {
			return getTmExmnMngInfo().getExmnNm();
		} else {
			//TODO:: 예외처리
			throw new NoLoginException();
		}
	}
	
	/**
	 * @Method Name : getOprtrNm
	 * @작성일 : 2023. 9. 11.
	 * @작성자 : KY.LEE
	 * @Method 설명 : 로그인 세션 ID 조회
	 * @return String
	 */
	public static String getExmnmngId() {
		String accountId = "";
		if(!CommonUtils.isNull(getTmExmnMngInfo())){
			accountId = getTmExmnMngInfo().getExmnmngId();
		}
		return accountId;
	}

	/**
	 * @Method Name : loginUserChk
	 * @작성일 : 2023. 9. 25.
	 * @작성자 : KY.LEE
	 * @Method 설명 : 로그인 사용자 아이디와 가져온 파라미터 값을 비교 
	 * @return boolean
	 */
	public static boolean loginUserChk(String userId) {
		boolean isUserChk = false;
		String sessionUserId = getExmnmngId();
		if(sessionUserId.equals(userId)) {
			isUserChk = true;
		}
		return isUserChk;
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