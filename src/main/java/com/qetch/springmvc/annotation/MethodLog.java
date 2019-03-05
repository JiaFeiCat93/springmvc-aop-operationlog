package com.qetch.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否添加操作日志注解
 * @author ZCW
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

	/**
	 * 记录操作描述
	 * @return
	 */
	String remark() default "";
	
	/**
	 * 增：0， 删：1， 改：2， 查：3（默认为查）
	 * @return
	 */
	String operateType() default "3"; 
}
