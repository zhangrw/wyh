package com.banshion.portal.web.sys.dao;

import com.banshion.intf.MyBatisRepository;
import com.banshion.portal.web.sys.domain.UserInfoExample;
import com.banshion.portal.web.sys.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface UserInfoMapper {
    int countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}