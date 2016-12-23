package com.banshion.portal.web.index.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.index.domain.TTransInfo;
import com.banshion.portal.web.index.domain.TTransInfoExample;
import java.util.List;
import java.util.Map;

import com.banshion.portal.web.index.filter.TransFilter;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface TTransInfoMapper {
    int countByExample(TTransInfoExample example);

    int deleteByExample(TTransInfoExample example);

    int deleteByPrimaryKey(String serialNumber);

    int insert(TTransInfo record);

    int insertSelective(TTransInfo record);

    List<TTransInfo> selectByExample(TTransInfoExample example);

    TTransInfo selectByPrimaryKey(String serialNumber);

    int updateByExampleSelective(@Param("record") TTransInfo record, @Param("example") TTransInfoExample example);

    int updateByExample(@Param("record") TTransInfo record, @Param("example") TTransInfoExample example);

    int updateByPrimaryKeySelective(TTransInfo record);

    int updateByPrimaryKey(TTransInfo record);


    List<Map<String,Object>> getData(TransFilter filter);

    List<Map<String,Object>> exportData(TransFilter filter);

    int deleteByIds(String[] ids);

}