package com.banshion.portal.web.sys.service;

import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.filter.RoleFilter;

import java.util.List;

/**
 * Created by zhang.rw on 16-4-21.
 */
public interface RoleService
{
    List<SysRole> getAllRole(RoleFilter filter);

    SysRole getRoleByid(String id);

    List<SysPermission> getPermissionsByRoleId(String id);

    void saveRolePermission( SysRole role , String[] pids );

    SysRole checkRepaeat(String rolename , String id);

    boolean updateRole( SysRole role , String[] ids );

    long getUserRoleCount(String[] sp);

    void deleteByIds(String[] sp);
}
