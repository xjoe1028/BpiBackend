package com.bpi.aspect;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import com.bpi.cconstant.ErrorCode;
import com.bpi.model.RqType;
import com.bpi.model.rs.ApiResponse;
import com.bpi.util.BpiRsUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class RqAspect {
	
	final Validator validator;

	// 切入點為com.bpi.controller下的所有類別的所有方法
	@Around(value = "execution(* com.bpi.controller.*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//		String wholeClassName = joinPoint.getTarget().getClass().getName(); // 取得切入點的類別名稱(含package.class)
		String className = joinPoint.getSignature().getDeclaringType().getSimpleName(); // 取得切入點的類別名稱(只有類別名稱)
		String annotatedMethodName = joinPoint.getSignature().getName(); // 取得切入點的方法名稱
		log.info("----- className.MethodName : {}.{} start -----", className, annotatedMethodName);
		Object[] args = joinPoint.getArgs(); // 取得輸入參數值
		log.info("----- params : {} -----", args);

		// 取得有含RqType的 annotation
		RqType rqType = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RqType.class);

		// 判斷有無 @RqType annotation
		if (rqType != null) {
			log.info("-----validate rq start-----");
			Object rq = args[0];
			// validate rq
			for (ConstraintViolation<Object> violation : validator.validate(rq)) {
				if (StringUtils.isNotBlank(violation.getMessage())) {
					log.info(violation.getMessage());
					return BpiRsUtil.getFailed(ErrorCode.VALIDATION_ERROR, violation.getMessage());
				}
			}
			log.info("-----validate rq end-----");
		}

		ApiResponse<?> apiRs = (ApiResponse<?>) joinPoint.proceed();
		
		if (apiRs.isSuccess())
			log.info("----- className.MethodName : {}.{} success : {} -----", className, annotatedMethodName, apiRs.getMessage());
		else
			log.info("----- className.MethodName : {}.{} failed : {} -----", className, annotatedMethodName, apiRs.getMessage());
		
		log.info("----- RsData : {} -----", apiRs.getData());
		log.info("----- className.MethodName : {}.{} end -----", className, annotatedMethodName);
		return apiRs;
	}
	
}
