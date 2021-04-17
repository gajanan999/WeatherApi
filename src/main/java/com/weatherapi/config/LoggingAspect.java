package com.weatherapi.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("execution (* com.weatherapi.*.*.*(..)) && !execution(* com.weatherapi.security.*.*(..))")
	public void before(JoinPoint joinPoint) {
		// Advice
		logger.info("Entering in method {}", joinPoint);
	}
	
	@After(value = "execution(* com.weatherapi.*.*.*(..)) && !execution(* com.weatherapi.security.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("After execution of {}", joinPoint);
	}
	
	@AfterThrowing (pointcut = "execution(* com.ciphe.serviceImpl.*.*(..)) && !execution(* com.weatherapi.security.*.*(..))", throwing = "ex")
    public void logAfterThrowingAllMethods(Exception ex) throws Throwable
    {
		logger.error("Exception has been occured",ex.getMessage(),ex);
		
    }
}
