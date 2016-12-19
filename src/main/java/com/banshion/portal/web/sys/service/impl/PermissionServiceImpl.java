package com.banshion.portal.web.sys.service.impl;

import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.service.PermissionService;
import com.banshion.portal.web.sys.dao.SysPermissionMapper;
import com.banshion.portal.web.sys.domain.SysPermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhang.rw on 16-4-20.
 */
@Service
public class PermissionServiceImpl implements PermissionService
{

    @Autowired
    SysPermissionMapper permissionmapper;

    public List<SysPermission> getAllPermission()
    {
        SysPermissionExample se = new SysPermissionExample();
        se.setOrderByClause(" code asc");
        return permissionmapper.selectByExample(se);
    }

    public void addPermission( SysPermission permission ){
        permission.setId(UUID.randomUUID().toString());
        permissionmapper.insert(permission);
    }

    public void delPermission( String id ){
        permissionmapper.deleteByPrimaryKey(id);
    }

    public void updatePermission(SysPermission permission){
        permissionmapper.updateByPrimaryKey(permission);
    }

    public SysPermission checkRepeat( SysPermission permission ){
        return permissionmapper.checkRepeat(permission);
    }

    public List<SysPermission> getParents(){
        SysPermissionExample se = new SysPermissionExample();
        se.createCriteria().andTypeLessThan(3);
        return permissionmapper.selectByExample(se);
    }
}

