package com.sri.lanka.traffic.webapp.support.exception;

import com.sri.lanka.traffic.webapp.common.util.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@RestControllerAdvice
public class WebAppExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView exceptionHandler(Exception e){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/common/errorPage");
		return mav;
	}
	
	// LoginCheckException
	@ExceptionHandler(value = NoLoginException.class)
	public ModelAndView loginChkExceptionHandler(NoLoginException ne, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		String errorMsg = ne.getMessage();
		if(!CommonUtils.isNull(errorMsg)){
			redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
		}
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	// customRuntimeException 
	@ExceptionHandler(value = CommonResponseException.class)
	public ResponseEntity<ErrorResponse> webExceptionHandler(CommonException ce, String message) {
		ErrorResponse response = new ErrorResponse(ce.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ce.getErrorCode().getStatus()));
	}
}
