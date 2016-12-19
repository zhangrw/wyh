package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.domain.SysPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisRepository
public interface SysPermissionMapper {
    int countByExample(SysPermissionExample example);

    int deleteByExample(SysPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    List<SysPermission> selectByExample(SysPermissionExample example);

    SysPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    int updateByExample(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    SysPermission checkRepeat(SysPermission record);
}