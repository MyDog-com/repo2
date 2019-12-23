package com.xsw.logistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.BaseDataExample;

public interface BaseDataService {
	int deleteByPrimaryKey(Long BaseDataId);

    int insert(BaseData record);

    int insertSelective(BaseData record);

    List<BaseData> selectByExample(BaseDataExample example);

    BaseData selectByPrimaryKey(Long BaseDataId);

    int updateByPrimaryKeySelective(BaseData record);

    int updateByPrimaryKey(BaseData record);

    List<BaseData> selectBaseDatasByParentName(@Param("parentName")String parentName);

}
