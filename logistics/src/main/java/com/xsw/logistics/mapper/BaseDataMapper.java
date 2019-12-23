package com.xsw.logistics.mapper;

import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.BaseDataExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseDataMapper {
    int deleteByPrimaryKey(Long baseId);

    int insert(BaseData record);

    int insertSelective(BaseData record);

    List<BaseData> selectByExample(BaseDataExample example);

    BaseData selectByPrimaryKey(Long baseId);

    int updateByPrimaryKeySelective(BaseData record);

    int updateByPrimaryKey(BaseData record);
    
    List<BaseData> selectBaseDatasByParentName(@Param("parentName")String parentName);
}