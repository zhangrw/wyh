package com.banshion.portal.sys.authentication;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 */
@SuppressWarnings("serial")
public class MyAuthenticationToken extends UsernamePasswordToken
{

	private String loginType;
	private String key;
	private String url;

	public MyAuthenticationToken() {
	};
    //public MyAuthenticationToken(UsernamePasswordToken token){
    //    MyAuthenticationToken mytoken = new MyAuthenticationToken();
    //    mytoken.setHost(token.getHost());
    //    mytoken.setPassword(token.getPassword());
    //    mytoken.setUsername(token.getUsername());
    //}

	public MyAuthenticationToken(final String username, final char[] password) {
		super(username, password, false, null);
	}

	public MyAuthenticationToken(final String username, final String password) {
		super(username, password, false, null);
	}

	public MyAuthenticationToken(final String username, final char[] password,
								 final String host) {
		super(username, password, false, host);
	}

	public MyAuthenticationToken(final String username, final String password,
								 final String host) {
		super(username, password, false, host);
	}

	public MyAuthenticationToken(final String username, final char[] password,
								 final boolean rememberMe) {
		super(username, password, rememberMe, null);
	}

	public MyAuthenticationToken(final String username, final String password,
								 final boolean rememberMe) {
		super(username, password, rememberMe, null);
	}

	public MyAuthenticationToken(final String username, final char[] password,
								 final boolean rememberMe, final String host,
								 final String loginType, final String key, final String url) {
		super(username, password, rememberMe, host);
		this.key = key;
		this.loginType = loginType;
		this.url = url;
	}

	public MyAuthenticationToken(final String username, final String password,
								 final boolean rememberMe, final String host,
								 final String loginType, final String key, final String url) {
		super(username, password, rememberMe, host);
		this.key = key;
		this.loginType = loginType;
		this.url = url;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.genertech.sahm.common.shiro.MyAuthenticationToken#getLoginType()
	 */
	public String getLoginType() {
		return this.loginType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.genertech.sahm.common.shiro.MyAuthenticationToken#getKey()
	 */
	public String getKey() {
		return this.key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void clear() {
		super.clear();
		this.key = null;
		this.loginType = null;
		this.url = null;
		//this.tenantName = null;
	}

}
