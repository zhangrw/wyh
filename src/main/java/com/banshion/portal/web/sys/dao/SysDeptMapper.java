package com.banshion.portal.web.sys.dao;


import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.SysDept;
import com.banshion.portal.web.sys.domain.SysDeptExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface SysDeptMapper {
    int countByExample(SysDeptExample example);

    int deleteByExample(SysDeptExample example);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    List<SysDept> selectByExample(SysDeptExample example);

    int updateByExampleSelective(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    int updateByExample(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    Integer getOrderCount(String parentId);

    SysDept checkNameExists(SysDept m);


    void updateDeptOrder(@Param("id") String id, @Param("deptOrder") String deptorder);

}