package com.banshion.portal.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能说明：ServiceException处理器
 * </pre>
 * 
 * @author <a href="mailto:shao.gq@gener-tech.com">ShaoGuoqing</a>
 * @version 1.0
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler{

    Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public final Object handleException(ServiceException se, WebRequest request) {
        String message = se.getMessage();
        String redirect = se.getRedirect();
        Map<String, Object> params = se.getParams();
        Throwable throwable = se.getThrowable();
        if (null != throwable) {
            logger.error("ServiceException", throwable);
        }
        if (StringUtils.isNotBlank(redirect)) {
            ModelAndView mv = new ModelAndView();
            if (params != null) {
                for (String key : params.keySet()) {
                    mv.addObject(key, params.get(key));
                }
            }
            mv.addObject("success", false);
            mv.setViewName(redirect);
            return mv;
        } else {
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("success", false);
            res.put("msg", message);
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.BAD_REQUEST);
        }
    }
}
