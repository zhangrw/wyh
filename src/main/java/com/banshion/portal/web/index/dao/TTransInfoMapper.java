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

    int insert(TTransInfo record);

    int insertSelective(TTransInfo record);

    List<TTransInfo> selectByExample(TTransInfoExample example);

    int updateByExampleSelective(@Param("record") TTransInfo record, @Param("example") TTransInfoExample example);

    int updateByExample(@Param("record") TTransInfo record, @Param("example") TTransInfoExample example);

    List<Map<String,Object>> getData(TransFilter filter);

    int deleteByIds(String[] ids);
}