package com.banshion.portal.web.index.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.index.domain.TindexUser;
import com.banshion.portal.web.index.domain.TindexUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface TindexUserMapper {
    int countByExample(TindexUserExample example);

    int deleteByExample(TindexUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(TindexUser record);

    int insertSelective(TindexUser record);

    List<TindexUser> selectByExample(TindexUserExample example);

    TindexUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TindexUser record, @Param("example") TindexUserExample example);

    int updateByExample(@Param("record") TindexUser record, @Param("example") TindexUserExample example);

    int updateByPrimaryKeySelective(TindexUser record);

    int updateByPrimaryKey(TindexUser record);

    int deleteByIds(String[] ids);
}