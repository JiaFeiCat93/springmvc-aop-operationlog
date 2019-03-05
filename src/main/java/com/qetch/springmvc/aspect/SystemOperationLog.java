package com.qetch.springmvc.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.qetch.springmvc.annotation.MethodLog;
import com.qetch.springmvc.common.PublicConfig;
import com.qetch.springmvc.domain.OperationLog;
import com.qetch.springmvc.service.OperationLogService;

@Component
@Aspect
@Order(0)
public class SystemOperationLog {
	private static final Log logger = LogFactory.getLog(SystemOperationLog.class);
	
	private String isOpen = "0";
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Before("execution(* com..*.controller..*(..))")
	public void enterController(JoinPoint point) throws Throwable {
		logger.info("--->enterController--->");
		isOpen = PublicConfig.OPEN;
	}
	
	@AfterReturning(pointcut = "execution(* com..*controller..*(..))", returning = "result")
	public void logAfterExecution(JoinPoint joinPoint, Object result) throws Throwable {
		logger.info("--->logAfterExecution--->");
		if ("1".equals(isOpen)) {
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			MethodLog methodLog = getMethodRemark(joinPoint);
			
			Class<?> targetClass = Class.forName(className);
			Method[] methods = targetClass.getMethods();
			boolean isAjax = false;
			
			for (Method method : methods) {
				// 判断该方法是否有@ResponseBody注解
				if (method.getName().equals(methodName) && method.isAnnotationPresent(ResponseBody.class)) {
					isAjax = true;
					break;
				}
			}
			
			String toPage = null;
			if (result != null) {
				if (!isAjax) {
					if (result instanceof ModelAndView) {
						toPage = ((ModelAndView) result).getViewName();
					} else {
						toPage = result.toString();
					}
				} else {
					toPage = "ajaxReturn";
				}
			} else {
				toPage = "noReturn";
			}
			
			if (className != null && methodName != null && methodLog != null) {
				HttpServletRequest request = this.getRequest(joinPoint);
				this.insertLog(request, toPage, methodLog);
			}
		}
	}
	
	/**
	 * 获取方法的中文备注——用于记录用户的操作日志描述
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	private MethodLog getMethodRemark(JoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		
		Class<?> targetClass = Class.forName(className);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == arguments.length) {
					MethodLog annotation = method.getAnnotation(MethodLog.class);
					if (annotation != null && !"".equals(annotation.remark())) {
						return annotation;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取参数request
	 * @param joinPoint
	 * @return
	 */
	private HttpServletRequest getRequest(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for (Object obj : args) {
			if (obj instanceof HttpServletRequest) {
				return (HttpServletRequest) obj;
			}
		}
		
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = servletRequestAttributes.getRequest();
		return request;
	}
	
	/**
	 * 保存操作日志
	 * @param request
	 * @param toPage
	 * @param methodLog
	 * @return
	 */
	private int insertLog(HttpServletRequest request, String toPage, MethodLog methodLog) {
		OperationLog log = new OperationLog();
		log.setIp(this.getRequestIP(request));
		//log.setUserName(UserUtils.getUserModel(request).getUserName());
		log.setUserName("");
		log.setLogType(methodLog.operateType());
		log.setLogDesc(methodLog.remark());
		log.setLogPage(toPage);
		log.setLogOperateParam(this.getRequestParam(request));
		logger.info("--->insertLog--->log:" + log);
		return operationLogService.saveLog(log);
	}
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	private String getRequestIP(HttpServletRequest request) {
		String ip = null;
		if (request.getHeader("x-forward-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forward-for");
		}
		return ip;
	}
	
	/**
	 * 获取前台传过来的参数
	 * @param request
	 * @return
	 */
	private String getRequestParam(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> returnMap = new HashMap<>();
		Iterator<Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
		Map.Entry<String, String[]> entry;
		String key = "";
		String value = "";
		while (iterator.hasNext()) {
			entry = iterator.next();
			key = entry.getKey();
			Object valueObj = entry.getValue();
			value = null;
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					if (null == value) {
						value = (null == values[i] ? "" : values[i]);
					} else {
						value += "," + (null == values[i] ? "" : values[i]); 
					}
				}
			} else {
				value = valueObj.toString();
			}
			returnMap.put(key, value);
		}
		
		return new Gson().toJson(returnMap);
	}
}
