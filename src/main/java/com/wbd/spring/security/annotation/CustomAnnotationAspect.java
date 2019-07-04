package com.wbd.spring.security.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 2.注解实现类
 * @author zgh
 *
 */
@Aspect //aop切面
@Component
public class CustomAnnotationAspect {

	//切入点
	@Pointcut(value="@annotation(com.wbd.spring.security.annotation.CustomAnnotation)")
	private void pointcut() {
		System.out.println("pointcut====");
	}
	
	//方法执行前后
	@Around(value="pointcut() && @annotation(customAnnotation)")
	public Object aroud(ProceedingJoinPoint point ,CustomAnnotation customAnnotation) {
		
		System.out.println("执行了aroud方法.......");
		String requestUrl = customAnnotation.requestUrl();
		//拦截的类名
		Class clz = point.getTarget().getClass();
		
		//拦截的方法名称
		
		Method method = ((MethodSignature)point.getSignature()).getMethod();
		
		 System.out.println("执行了 类:" + clz + " 方法:" + method.getName() + " 自定义请求地址:" + requestUrl);
	  //执行程序
		 try {
			return point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
	@AfterReturning(value="pointcut() && @annotation(customAnnotation)",returning="result")
	public Object afterReturning(JoinPoint jp,CustomAnnotation customAnnotation,Object result) {
		
		System.out.println("执行了 afterReturing方法");
		System.out.println("执行结果:"+result);
		return result;
	}
	
	/**
     * 方法执行后 并抛出异常
     *
     * @param joinPoint
     * @param myLog
     * @param 
     */
    @AfterThrowing(value = "pointcut() && @annotation(customAnnotation)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,CustomAnnotation customAnnotation, Exception ex) {
        System.out.println("++++执行了afterThrowing方法++++");
        System.out.println("请求：" + customAnnotation.requestUrl() + " 出现异常");
    }

}
