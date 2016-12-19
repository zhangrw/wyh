package com.banshion.portal.exception;

import com.banshion.portal.beanvalidator.BeanValidators;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author <a href="mailto:shao.gq@gener-tech.com">ShaoGuoqing</a>
 * @version 1.0
 */
@ControllerAdvice
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler{
    
    Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public final Object handleException(ConstraintViolationException cve, WebRequest request) {
        String bv = BeanValidators.extractPropertyAndMessageAsString(cve.getConstraintViolations(), " ", "--", "\n");
        logger.warn(bv);
        String requestType = request.getHeader("X-Requested-With");
        if(StringUtils.isNotBlank(requestType)){
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("success", false);
            res.put("msg", "输入字段错误");
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
        }
        return "error/500";
    }
}
