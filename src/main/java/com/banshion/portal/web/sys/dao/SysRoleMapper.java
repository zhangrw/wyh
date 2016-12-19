package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.domain.SysRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisRepository
public interface SysRoleMapper {
    int countByExample(SysRoleExample example);

    int deleteByExample(SysRoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    List<SysRole> selectByExample(SysRoleExample example);

    SysRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    int updateByExample(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getOwnRolesById(@Param("userid") String userid);

    List<SysPermission> getPermissionsByRoleId(String id);

    SysRole checkRepeate(@Param("rolename")String name ,@Param("id")String id );

    long queryUserRoleCount(String[] ids);

    void deleteByIds(String[] ids);

    void deleteRolePermissions(String[] ids);

}