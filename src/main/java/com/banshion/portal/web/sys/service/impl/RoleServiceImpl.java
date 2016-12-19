package com.banshion.portal.web.sys.service.impl;

import com.banshion.portal.exception.ServiceException;
import com.banshion.portal.web.sys.dao.SysRoleMapper;
import com.banshion.portal.web.sys.dao.SysRolePermissionMapper;
import com.banshion.portal.web.sys.domain.*;
import com.banshion.portal.web.sys.filter.RoleFilter;
import com.banshion.portal.web.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhang.rw on 16-4-21.
 */
@Service
public class RoleServiceImpl implements RoleService
{

    @Autowired
    private SysRoleMapper roleDao;

    @Autowired
    private SysRolePermissionMapper rolePerDao;

    public List<SysRole> getAllRole(RoleFilter filter){
        SysRoleExample se = new SysRoleExample();
        se.setOrderByClause(" rolename desc ");
        return roleDao.selectByExample(se);
    }

    public SysRole getRoleByid(String id){
        return roleDao.selectByPrimaryKey(id);
    }

    public List<SysPermission> getPermissionsByRoleId(String roleid){
       return roleDao.getPermissionsByRoleId(roleid);
    }

    // 新增
    public void saveRolePermission( SysRole role , String[] pids ){
        String id = UUID.randomUUID().toString();
        role.setId(id);
        roleDao.insert(role);

        SysRolePermission rp = new SysRolePermission();
        rp.setRoleId(id);
        for(String pid : pids){
            rp.setPermissionId(pid);
            rolePerDao.insert(rp);
        }

    }

    // 检查 rolename是否重复
    @Transactional
    public SysRole checkRepaeat(String rolename , String id){
        return roleDao.checkRepeate(rolename , id);
    }

    public boolean updateRole( SysRole role , String[] ids ){
        String roleId = role.getId();
        try
        {
            roleDao.updateByPrimaryKey(role);
        }catch (Exception e){
            throw new ServiceException("更新角色信息失败",e);
        }
         try{
            // 更新权限信息
            SysRolePermissionExample se = new SysRolePermissionExample();
            se.createCriteria().andRoleIdEqualTo(roleId);
            rolePerDao.deleteByExample(se);

            SysRolePermission srp = new SysRolePermission();
            srp.setRoleId(roleId);
            if( ids != null || ids.length > 0){
                for( String pid : ids ){
                    srp.setPermissionId(pid);
                    rolePerDao.insert(srp);
                }
            }
        }catch (Exception e){
             throw new ServiceException("更新角色权限信息失败",e);
        }
        return true;
    }

    public long getUserRoleCount(String[] sp)
    {
        try {
            return roleDao.queryUserRoleCount(sp);
        } catch (Exception e) {
            throw new ServiceException("查询角色关联用户失败", e);
        }
    }

    public void deleteByIds(String[] sp)
    {
        try {
            if (roleDao.queryUserRoleCount(sp) > 0) {
                throw new ServiceException("角色下存在用户关联不能删除");
            }
            roleDao.deleteByIds(sp);
            roleDao.deleteRolePermissions(sp);
            //logService.warning(Consts.SystemModel.SYS, "批量删除角色");
        } catch (Exception e) {
            //logService.error(Consts.SystemModel.SYS, "批量删除角色失败");
            throw new ServiceException("批量删除角色失败", e);
        }
    }


}
