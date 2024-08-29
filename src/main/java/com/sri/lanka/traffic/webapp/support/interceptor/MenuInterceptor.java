package com.sri.lanka.traffic.webapp.support.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.lanka.traffic.webapp.common.dto.common.LoginExmnDTO;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import com.sri.lanka.traffic.webapp.common.util.LoginExmnUtils;
import com.sri.lanka.traffic.webapp.support.exception.CommonResponse;
import com.sri.lanka.traffic.webapp.support.exception.ErrorCode;
import com.sri.lanka.traffic.webapp.support.exception.NoLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Configuration
public class MenuInterceptor implements HandlerInterceptor {

    /**
     * @brief 언어 설정
     * @return  boolean
     * @throws Exception
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response , Object handler) throws Exception {

		LoginExmnDTO loginSessionDTO = LoginExmnUtils.getTmExmnMngInfo();

		LocalDateTime nowTime = LocalDateTime.now();
		if("survey".equals(loginSessionDTO.getExmnType().getType())) {
			nowTime = CommonUtils.getStartOfDay(LocalDate.now());
		}

		String asyncReauest = request.getHeader("X-Requested-With");
		if (!CommonUtils.isNull(asyncReauest) && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			if(nowTime.isAfter(loginSessionDTO.getEndDt().plusMinutes(1))){
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType("application/json");
				response.getWriter().write(new ObjectMapper().writeValueAsString(new CommonResponse(ErrorCode.UNAUTHORIZED.getStatus(), CommonUtils.getMessage("survey.time.out"))));
				return false;
			}
		}else{
			if(nowTime.isAfter(loginSessionDTO.getEndDt().plusMinutes(1))){
				throw new NoLoginException(CommonUtils.getMessage("survey.time.out"));
			}

			//쿠키값 비교 다국어 처리
			Cookie[] cookies = request.getCookies();
			String lang = "eng";
			for(Cookie cookie : cookies) {
				if("lang".equals(cookie.getName())) {
					lang = cookie.getValue();
					request.setAttribute("lang", lang);
				}
			}
		}

		return true;
	}
}
