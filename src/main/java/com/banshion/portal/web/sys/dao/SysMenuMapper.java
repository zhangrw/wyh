package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.SysMenuExample;
import com.banshion.portal.web.sys.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by root on 16-4-30.
 */
@MyBatisRepository
public interface SysMenuMapper {

    int countByExample(SysMenuExample example);

    int deleteByExample(SysMenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(SysMenuExample example);

    SysMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    Integer getOrderCount(String parentId);

    SysMenu checkNameExists(SysMenu m);

    void updateMenuOder(@Param("id") String id, @Param("menuOrder") String menuOrder);

    List<SysMenu> getMenuByUserId(String userId);

    List<SysMenu> getMenuTreeById(String[] str);

}
