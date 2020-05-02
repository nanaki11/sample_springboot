package com.example.demo.common;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Api用インターセプター
 *
 */

@Aspect
@Component
public class ApiInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

	/**
	 * RestController用の 開始ログ
	 * @param jp
	 */
	@Before("execution(public * com.example.demo.controller.api.*Controller.*(..))")
	public void startLog(JoinPoint jp) {

		logger.info("API開始[ {}, 引数: {} ", getMethodInfo(jp), Arrays.toString(jp.getArgs()));
	}

	/**
	 * RestController用の 終了ログ
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(public * com.example.demo.controller.api.*Controller.*(..))", returning = "returnValue")
	public void endLog(JoinPoint jp, Object returnValue) {

		logger.info("API終了[ {}, 返却値: {} ", getMethodInfo(jp), returnValue.toString());
	}

	/**
	 * RestController用の 例外ログ
	 * @param jp
	 */
	@AfterThrowing(value = "execution(public * com.example.demo.controller.api.*Controller.*(..))", throwing = "e")
	public void endLog(JoinPoint jp, Throwable e) {

		logger.error("APIエラー終了[ {}, 引数: {} ", getMethodInfo(jp), Arrays.toString(jp.getArgs()), e);
	}

	/**
	 * メソッド情報取得処理
	 * @param jp
	 * @return クラス名.メソッド名
	 */
	private String getMethodInfo(JoinPoint jp) {

		String className = jp.getTarget().getClass().toString();
		String method = jp.getSignature().getName();
		return className + "." + method;
	}
}
