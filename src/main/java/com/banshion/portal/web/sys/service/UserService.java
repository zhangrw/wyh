package com.banshion.portal.web.sys.service;


import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.domain.SysUser;
import com.banshion.portal.web.sys.domain.UserInfo;

import java.util.List;

/**
 * Created by zhang.rw on 16-4-8.
 */
public interface UserService {

    UserInfo getUserById(int id);

    List<UserInfo> getUsers();

    int insert(UserInfo userInfo);

    ShiroUser login(String username , String password);

    SysUser getUserinfoByid( String id );

    List<SysRole>  getAllRoles();

    List<SysRole> getOwnRolesById(String userid);

    String save(SysUser user);

    String saveUserAndRole(SysUser user,String[] roleids);

    String UpdateUserRole(SysUser user,String[] roleids);

    void deleteUser(String[] ids);

    void resetPassword(String id , String newpwd);

    boolean checkloginnameExists(String loginname , String id);
}