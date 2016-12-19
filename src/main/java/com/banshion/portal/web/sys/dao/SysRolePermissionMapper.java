package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.domain.SysRolePermission;
import com.banshion.portal.web.sys.domain.SysRolePermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisRepository
public interface SysRolePermissionMapper {
    int countByExample(SysRolePermissionExample example);

    int deleteByExample(SysRolePermissionExample example);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    List<SysRolePermission> selectByExample(SysRolePermissionExample example);

    int updateByExampleSelective(@Param("record") SysRolePermission record, @Param("example") SysRolePermissionExample example);

    int updateByExample(@Param("record") SysRolePermission record, @Param("example") SysRolePermissionExample example);

    List<SysPermission> getPermissionByUserid(String id);
}