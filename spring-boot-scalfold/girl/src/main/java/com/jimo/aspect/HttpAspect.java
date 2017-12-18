package com.jimo.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HttpAspect {

	public static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

	@Pointcut("execution(public * com.jimo.controller.MenController.*(..))")
	public void log() {
	}

	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = attr.getRequest();
		// url
		log.info("url={}", req.getRequestURL());
		// method
		log.info("method={}", req.getMethod());
		// ip:
		log.info("ip={}", req.getRemoteAddr());
		// class method
		log.info("class_method={}",
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		// args
		log.info("args={}", joinPoint.getArgs());
	}

	@After("log()")
	public void doAfter() {
		System.out.println("After............................");
	}

	@AfterReturning(returning = "object", pointcut = "log()")
	public void doAfterReturning(Object object) {
		log.info("response={}", object.toString());
	}
}
