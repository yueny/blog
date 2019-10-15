/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.exception;

import com.mtons.mblog.base.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

/**
 * 必须捕获的业务异常
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 16:35
 */
public class BizException extends Exception {
	private static final long serialVersionUID = -7443213283905815106L;

	@Getter
	@Setter
	private String code;
	@Getter
	@Setter
	private String errorMessage;

	public BizException() {
	}

	/**
	 *
	 * @param errorType 错误代码
	 */
	public BizException(ErrorType errorType) {
		super("code=" + errorType.getCode());
		this.code = errorType.getCode();
		errorMessage = errorType.getMessage();
	}

	/**
	 *
	 * @param errorType 错误代码
	 * @param args 占位符
	 */
	public BizException(ErrorType errorType, String... args) {
		super("code=" + errorType.getCode());

		this.code = errorType.getCode();
		if(errorType.getMessage().contains("%")){
			errorMessage = String.format(errorType.getMessage(), args);
		}else{
			errorMessage = errorType.getMessage();
		}
	}

	/**
	 *
	 * @param message 错误消息
	 */
	public BizException(String message) {
		super(message);
		this.code = ErrorType.INVALID_ERROR.getCode();
		errorMessage = message;
	}

	/**
	 *
	 * @param cause 捕获的异常
	 */
	public BizException(Throwable cause) {
		super(cause);
		this.code = ErrorType.INVALID_ERROR.getCode();
		errorMessage = cause.getMessage();
	}

	/**
	 *
	 * @param message 错误消息
	 * @param cause 捕获的异常
	 */
	public BizException(String message, Throwable cause) {
		super(message, cause);
		this.code = ErrorType.INVALID_ERROR.getCode();
		errorMessage = message;
	}

	/**
	 *
	 * @param code 错误代码
	 * @param message 错误消息
	 */
	public BizException(String code, String message) {
		super(message);
		this.code = code;
		errorMessage = message;
	}

}
