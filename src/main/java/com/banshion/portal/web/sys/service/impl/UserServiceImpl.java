package com.banshion.portal.web.sys.service.impl;

import com.banshion.portal.exception.ServiceException;
import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.util.PasswordUtil;
import com.banshion.portal.web.index.filter.UserFilter;
import com.banshion.portal.web.sys.dao.SysRoleMapper;
import com.banshion.portal.web.sys.dao.SysUserMapper;
import com.banshion.portal.web.sys.dao.SysUserRoleMapper;
import com.banshion.portal.web.sys.domain.*;
import com.banshion.portal.web.sys.service.UserService;
import com.banshion.portal.web.sys.dao.UserInfoMapper;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhang.rw on 16-4-8.
 */
@Service
public class UserServiceImpl implements UserService
{
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private SysUserMapper sysusermapper;

    @Autowired
    private SysRoleMapper rolemapper;

    @Autowired
    private SysUserRoleMapper userrolemapper;

    public UserInfo getUserById(int id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }


    public List<UserInfo> getUsers() {
        return userInfoMapper.selectByExample(null);
    }


    public int insert(UserInfo userInfo) {

        int result = userInfoMapper.insert(userInfo);

        System.out.println(result);
        return result;
    }

    public ShiroUser login(String username, String password)
    {
        SysUserExample se = new SysUserExample();
        se.createCriteria().andLoginnameEqualTo(username);
        List<SysUser> list =  sysusermapper.selectByExample(se);
        if( list != null && list.size() > 0 ){

            SysUser user = list.get(0);
            byte[] salt = PasswordUtil.decodeHex(user.getSalt());
            String pwd = PasswordUtil.getEncodePassWord(password, salt);

            if( pwd.equals(user.getPassword())){

                UserFilter filter = new UserFilter();
                filter.setId(user.getId());
                List<ShiroUser> userList = sysusermapper.getShiroUser(filter);
//                if( userList != null && userList.size() == 1 ){
                    return userList.get(0);
//                }
            }else{
                throw new IncorrectCredentialsException();
            }

        }else{
            return null;
        }
    }
    public static void main(String[] a){
        System.out.println(Md5Crypt.apr1Crypt("admin"));
        byte[] salts = PasswordUtil.getSaltBytes();// 自定义加密串,目前获取8位随即
        System.out.println(PasswordUtil.getEncodeSalts(salts));
        System.out.println(PasswordUtil.getEncodePassWord("1",salts));
    }

    public String save(SysUser user)
    {
        SysUser oldUser = sysusermapper.selectByLoginName(user.getLoginname(),user.getId());
        if (oldUser != null) {
            throw new ServiceException("已存在相同登录名");
        }
        try {
            String userId = UUID.randomUUID().toString();
            byte[] salts = PasswordUtil.getSaltBytes();// 自定义加密串,目前获取8位随即
            user.setSalt(PasswordUtil.getEncodeSalts(salts));
            user.setPassword(PasswordUtil.getEncodePassWord(user.getPassword(),salts));
            user.setId(userId);
            sysusermapper.insert(user);
            return userId;
        } catch (Exception e) {
            throw new ServiceException("新增用户失败", e);
        }
    }

    public boolean checkloginnameExists(String loginname , String id){
        SysUser user = null;
        try
        {
            user = sysusermapper.selectByLoginName(loginname.trim(),id);
        }catch (Exception e){
            throw new ServiceException("根据用户名查询用户失败",e);
        }

        if( StringUtils.isNotBlank(id)){
            if( user != null && !id.equals(user.getId()) ){
                return true;
            }
            return false;
        }
        if( user != null ){
            return true;
        }
        return false;
    }

    // 通过ID查询用户信息
    public SysUser getUserinfoByid( String id ){
        SysUserExample se = new SysUserExample();
            se.createCriteria().andIdEqualTo(id);
        List<SysUser> list =  sysusermapper.selectByExample(se);
        if( list == null || list.size() == 0 ){
            return null;
        }
        return list.get(0);
    }

    public List<SysRole>  getAllRoles(){
        return rolemapper.selectByExample(null);
    }

    public List<SysRole> getOwnRolesById(String userid){
        return rolemapper.getOwnRolesById(userid);
    }

    public String saveUserAndRole(SysUser user,String[] roleids)
    {
        SysUser oldUser = sysusermapper.selectByLoginName(user.getLoginname(),user.getId());
        if (oldUser != null) {
            throw new ServiceException("已存在相同登录名");
        }
        try {
            String userId = UUID.randomUUID().toString();
            byte[] salts = PasswordUtil.getSaltBytes();// 自定义加密串,目前获取8位随即
            user.setSalt(PasswordUtil.getEncodeSalts(salts));
            user.setPassword(PasswordUtil.getEncodePassWord(user.getPassword(),salts));
            user.setId(userId);
            sysusermapper.insert(user);
            SysUserRole sur = new SysUserRole();
            for( String roleid : roleids ){
                sur.setUserId(userId);
                sur.setUserId(roleid);
                userrolemapper.insert(sur);
            }
            return userId;
        } catch (Exception e) {
            throw new ServiceException("新增用户失败", e);
        }
    }

    @Transactional
    public String UpdateUserRole(SysUser user,String[] roles){
        String userid = user.getId();
        if (StringUtils.isBlank(userid)) {
            throw new ServiceException("请选择要修改的用户！");
        }
        SysUser oldUser = sysusermapper.selectByLoginName(user.getLoginname(),user.getId());
        if (oldUser != null && !userid.equals(oldUser.getId())) {
            throw new ServiceException("已存在相同登录名");
        }
        // 更新user信息
        sysusermapper.updateByPrimaryKeySelective(user);

        SysUserRoleExample se = new SysUserRoleExample();
        se.createCriteria().andUserIdEqualTo(userid);
        userrolemapper.deleteByExample(se); //删除已有角色

        if(null == roles || roles.length==0){
            return userid;
        }

        // 插入新角色
        SysUserRole su = new SysUserRole();
        for( String roleid : roles ){
            su.setRoleId(roleid);
            su.setUserId(userid);
            userrolemapper.insert(su);
        }
        return userid;
    }

    @Transactional
    public void deleteUser(String[] ids){
        sysusermapper.deleteByIds(ids);
        userrolemapper.deleteByUserIds(ids);
    }

    public void resetPassword(String id , String newpwd){
        SysUser user = sysusermapper.selectByPrimaryKey(id);

        if( user == null  ){
            throw new ServiceException("请选择需要重置密码到用户");
        }

        try{
            byte[] salts = PasswordUtil.getSaltBytes();
            user.setSalt(PasswordUtil.getEncodeSalts(salts));
            user.setPassword(PasswordUtil.getEncodePassWord(newpwd , salts));

            sysusermapper.updateByPrimaryKeySelective(user);

        }catch (Exception e){
            throw new ServiceException("重置用户密码失败",e);
        }
    }
}