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
 * 运行时业务异常
 *
 * @author langhsu
 *
 */
public class MtonsException extends RuntimeException {
	private static final long serialVersionUID = -7443213283905815106L;

	@Getter
	@Setter
	private String code;
	@Getter
	@Setter
	private String errorMessage;

	public MtonsException() {
	}

	/**
	 * MtonsException
	 * @param errorType 错误代码
	 */
	public MtonsException(ErrorType errorType) {
		super("code=" + errorType.getCode());
		this.code = errorType.getCode();
		errorMessage = errorType.getMessage();
	}

	/**
	 * MtonsException
	 * @param message 错误消息
	 */
	public MtonsException(String message) {
		super(message);
		this.code = ErrorType.INVALID_ERROR.getCode();
		errorMessage = message;
	}

	/**
	 * MtonsException
	 * @param cause 捕获的异常
	 */
	public MtonsException(Throwable cause) {
		super(cause);
		this.code = ErrorType.INVALID_ERROR.getCode();
		errorMessage = cause.getMessage();
	}

	/**
	 * MtonsException
	 * @param message 错误消息
	 * @param cause 捕获的异常
	 */
	public MtonsException(String message, Throwable cause) {
		super(message, cause);
		this.code = ErrorType.INVALID_ERROR.getCode();
		errorMessage = message;
	}
	
	/**
	 * MtonsException
	 * @param code 错误代码
	 * @param message 错误消息
	 */
	public MtonsException(String code, String message) {
		super(message);
		this.code = code;
		errorMessage = message;
	}

}
