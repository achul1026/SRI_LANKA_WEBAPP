package com.sri.lanka.traffic.webapp.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MenuInterceptor implements HandlerInterceptor {

//	@Autowired
//	MozWebOprtrRepository	mozWebOprtrRepository;
//	
//	@Autowired
//	MozMenuRepository menuRepository;
	
    /**
     * @brief 사이드메뉴 설정
     * @return  boolean
     * @throws Exception
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response , Object handler ) throws Exception {
//		log.info("==============Menu Interceptor preHandle Start ===============");
//		log.info("Request Url : {}", request.getRequestURL());
//		
//		//반환 객체
//		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
//		SecurityAccount securityAccount = (SecurityAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		
//		//세션 정보 못가져올때
//		if(securityAccount == null) {
//			//TO DO : NO LOGIN EXCEPTION;
//			throw new NoLoginException();
//		}
//		String userNm = securityAccount.getUsername();
//		log.info(securityAccount.getAuthorities().toString());
//		MozWebOprtr mozWebOprtr = new MozWebOprtr(userNm);
//		//사용자 권한 조회
//		MozWebOprtr dbWebOprtr = mozWebOprtrRepository.selectUserByUserId(mozWebOprtr);
//		
//		//대 메뉴 리스트 조회
//		List<MozMenu> mainMenuList = menuRepository.selectParentMenuListByMenuAuth(dbWebOprtr.getAuthId());
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("authId", dbWebOprtr.getAuthId());
//		if(!mainMenuList.isEmpty()) {
//			for(int i = 0; i < mainMenuList.size(); i++) {
//				Map<String,Object> resultMap = new HashMap<String,Object>();
//				MozMenu dbMenu = mainMenuList.get(i);
//				String menuId = dbMenu.getMenuId();
//				paramMap.put("menuId", menuId);
//				List<MozMenu> subMenuList = menuRepository.selectSubMenuListByMenuAuthAndMenuId(paramMap);
//				MenuIcon menuIcon = MenuIcon.getCNameForMenuAbv(mainMenuList.get(i).getMenuAbv());
//				
//				resultMap.put("mainMenu", mainMenuList.get(i));
//				resultMap.put("menuIcon", menuIcon!=null?menuIcon.getCName():"");
//				resultMap.put("subMenuList", subMenuList);
//				result.add(resultMap);
//			}
//		}
//		//쿠키값 비교 다국어 처리
//		Cookie[] cookies = request.getCookies();
//		
//		for(Cookie cookie : cookies) {
//			if("lang".equals(cookie.getName())) {
//				request.setAttribute("lang", cookie.getValue());
//			}
//		}
//		request.setAttribute("sideMenuList", result);
		//로그 찍기
//		for(int i=0; i < result.size();  i++) {
//			log.info("main :::::::::::::"+ result.get(i).get("mainMenu"));
//			log.info("sub :::::::::::::"+ result.get(i).get("subMenuList"));
//		}
		return true;
	}
}
