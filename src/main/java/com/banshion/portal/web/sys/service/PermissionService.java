package com.banshion.portal.web.sys.service;

import com.banshion.portal.web.sys.domain.SysPermission;

import java.util.List;

/**
 * Created by zhang.rw on 16-4-20.
 */
public interface PermissionService
{
    List<SysPermission> getAllPermission();

    void addPermission( SysPermission permission );

    void delPermission( String id );

    void updatePermission(SysPermission permission);

    SysPermission checkRepeat( SysPermission permission );

    List<SysPermission> getParents();
}
