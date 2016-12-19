package com.banshion.portal.util;

import com.banshion.portal.sys.authentication.ShiroUser;
import org.apache.shiro.SecurityUtils;



/**
 * <pre>
 * 功能说明：安全相关工具类
 * </pre>
 * 
 * @author <a href="mailto:liu.w@gener-tech.com">liuwei</a>
 * @version 1.0
 */
public class Securitys extends SecurityUtils
{

    /***
     * 获取ShiroUser
     * @return
     */
    public static ShiroUser getUser() {
    
        return (ShiroUser) getSubject().getPrincipal(); 
    }

    /***
     * 获取用户ID
     * @return userId
     */
    public static String getUserId() {
        return getUser().getId();
    }

    /***
     * 获取登录名
     * @return loginName
     */
    public static String getLoginName() {
        return getUser().getLoginName();
    }

    /***
     * 获取用户名
     * @return userName
     */
    public static String getUserName() {
        return getUser().getName();
    }
    
    /***
     * 获取lccCode
     * @return
     */
//    public static String getLccCode(){
//    	return getUser().getLccCode();
//    }
    
    /***
     * 获取当前项目Id
     * @return userName
     */
//    public static String getCurrentProject() {
//        return getUser().getCurrent_projectId() ;
//    }
    
    /***
     * 获取当前用户所属部门Id
     * @return userName
     */
//    public static String getCurrentDepart() {
//        return getUser().getDepartmentId();
//    }
    
    /***
     * 获取当前用户所属公司Id
     * @return userName
     */
//    public static String getCurrentCompany() {
//        return getUser().getCompanyId() ;
//    }

    /**
     * 获取所属组织ID
     * @return tenantId
     */
//    public static String getOrganId() {
//        return getUser().getOrganId();
//
//    }
    

    /**
     * 获取当前用户IP
     * @return tenantId
     */
    public static String getIp() {
        return getUser().getIP();
    }
    
    /**
     * 是否为超级管理员
     * @return isAdmin
     */
    public static boolean isAdmin(){
    	return getUser().isAdmin();
    }
    
    /**
     * 是否为租户管理员
     * @return isTenantAdmin
     */
//    public static boolean isOrganAdmin(){
//    	return getUser().isOrganAdmin();
//    }
    
//    public static String getIcon(){
//       return getUser().getIcon();
//     }
}
