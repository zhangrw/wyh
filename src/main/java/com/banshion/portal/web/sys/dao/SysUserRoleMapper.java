package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.SysUserRole;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.domain.SysUserRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisRepository
public interface SysUserRoleMapper {
    int countByExample(SysUserRoleExample example);

    int deleteByExample(SysUserRoleExample example);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(SysUserRoleExample example);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int deleteByUserIds(String[] ids);

    List<SysRole> getRoleByUserid(String id);
}