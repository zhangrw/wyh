package com.banshion.portal.sys.authentication;


import com.banshion.portal.web.sys.dao.SysPermissionMapper;
import com.banshion.portal.web.sys.dao.SysRoleMapper;
import com.banshion.portal.web.sys.dao.SysRolePermissionMapper;
import com.banshion.portal.web.sys.dao.SysUserRoleMapper;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.domain.SysUser;
import com.banshion.portal.web.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang.rw on 16-4-12.
 */
public class ShiroDbRealm extends AuthorizingRealm
{

    @Autowired
    protected UserService userService;
    @Autowired
    protected SysRolePermissionMapper permissionService;
    @Autowired
    protected SysUserRoleMapper roleService;
    @Autowired
    private SysPermissionMapper permissionmapper;

    @Autowired
    private SysRoleMapper roleDao;
    /**
     * 授权查询
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        // 可以根据用户信息表po定制  使得可以携带更多用户信息
        // 默认时只携带username password少量信息
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if("admin".equals(user.getLoginName())) // 管理员
        {
            //info.addStringPermission("permissionmgt:administrator");
            List<SysRole> lr =  roleDao.selectByExample(null);
            for( SysRole role : lr ){
                info.addRole(role.getRolename());
            }

            List<SysPermission> lp =  permissionmapper.selectByExample(null);
            for( SysPermission sp : lp ){
                if( sp.getCode() != null && !"".equals(sp.getCode()) ){
                    info.addStringPermission(sp.getCode());
                }
            }


        }else{
            List<SysRole> roleList = null;// 角色集合//
            List<SysPermission> listP = null;// 权限集合//

            String userid = user.getId();

            roleList = roleService.getRoleByUserid(userid);
            for( SysRole role : roleList ){
                info.addRole(role.getRolename());
            }

            listP = permissionService.getPermissionByUserid(userid);
            for( SysPermission p : listP ){
                if( p.getCode() != null && !"".equals(p.getCode()) ){
                    info.addStringPermission(p.getCode());
                }
            }
        }
        return info;
    }

    /**
     * 登录认证
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
    {
            MyAuthenticationToken token = (MyAuthenticationToken) authcToken;
            ShiroUser user = userService.login(token.getUsername(),
                    new String(token.getPassword()));  // 校验登录
            // 抛出用户不存在异常
            if( user == null ){
                throw new UnknownAccountException();
            }
            List<String> roleIdList = new ArrayList<String>();

            boolean isadmin = user.getLoginName().indexOf("admin") == -1 ? false : true;
            user.setAdmin(isadmin);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
                    token.getPassword(), getName());
            // 添加登陆日志//
            //logService.log(Consts.SystemModel.SYS, LogType.BUSINESS, LogLevel.INFO, "用户" + user.getLoginName() + "登录",su.getTenantId(), su.getId(), su.getIP(), su.getName());
            return info;
    }
}
