package com.banshion.portal.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 功能说明：Service层公用的Exception.
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * </pre>
 * 
 * @author <a href="mailto:liu.w@gener-tech.com">liuwei</a>
 * @version 1.0
 */
public class SmartException extends RuntimeException {

	private static final long serialVersionUID = 1401593546385403720L;

	// 自定义异常消息
	private String message = null;

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		if (!StringUtils.isBlank(this.message))
			return this.message;
		else
			return super.getMessage();
	}

	public SmartException() {
		super();
	}

	public SmartException(String message) {
		super(message);
	}

	public SmartException(Throwable cause) {
		super(cause);
	}

	public SmartException(String message, Throwable cause) {
		super(message, cause);
	}

	/***
	 * 自定义可采用{}传参的方式抛出新的异常
	 * 
	 * @param message
	 *            <String>异常内容
	 * @param paras
	 *            <String>参数
	 * 
	 */
	public SmartException(String message, Object... paras) {
		super();
		this.setMessage(getExceptionMessage(message, paras));
	}

	/***
	 * 自定义可采用{}传参的方式抛出新的异常
	 * 
	 * @param message
	 *            <String>异常内容
	 * @param paras
	 *            <String>参数
	 * 
	 */
	public SmartException(String message, Throwable cause, Object... paras) {
		super(cause);
		this.setMessage(getExceptionMessage(message, paras));
	}

	/***
	 * 得到异常消息
	 * 
	 * @param message
	 * @param paras
	 * @return
	 */
	protected String getExceptionMessage(String message, Object... paras) {
		StringBuffer _msg = new StringBuffer();
		// 拆分需要添加参数的消息段
		String[] msgs = message.split("\\{}");
		for (int i = 0; i < msgs.length; i++) {
			String msg = msgs[i];
			try {
				if (i < msgs.length - 1)
					msg += paras[i];
			} catch (Exception e) {
				e.printStackTrace();
			}
			_msg.append(msg);
		}
		return _msg.toString();
	}

}
