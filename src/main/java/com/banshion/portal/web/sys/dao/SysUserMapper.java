package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.web.index.filter.UserFilter;
import com.banshion.portal.web.sys.domain.SysUser;
import com.banshion.portal.web.sys.domain.SysUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface SysUserMapper {
    int countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByLoginName(@Param("loginname") String loginname,@Param("id") String id);

    int deleteByIds(String[] ids);

    List<ShiroUser> getShiroUser(UserFilter filter);

    ShiroUser getShiroUser(@Param("id") String id);

    List<ShiroUser> getShiroUser();

    List<Map<String,Object>> exportUser(UserFilter filter);
}