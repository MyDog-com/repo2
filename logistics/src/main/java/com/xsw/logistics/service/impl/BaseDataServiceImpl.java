package com.xsw.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsw.logistics.mapper.BaseDataMapper;
import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.BaseDataExample;
import com.xsw.logistics.service.BaseDataService;
@Service
public class BaseDataServiceImpl implements BaseDataService {

		@Autowired
		private BaseDataMapper BaseDataMapper;
	
	@Override
	public int deleteByPrimaryKey(Long BaseDataId) {
		
		return BaseDataMapper.deleteByPrimaryKey(BaseDataId);
	}

	@Override
	public int insert(BaseData record) {
		return BaseDataMapper.insert(record);
	}

	@Override
	public int insertSelective(BaseData record) {
		return BaseDataMapper.insertSelective(record);
	}

	@Override
	public List<BaseData> selectByExample(BaseDataExample example) {
		return BaseDataMapper.selectByExample(example);
	}

	@Override
	public BaseData selectByPrimaryKey(Long BaseDataId) {
		return BaseDataMapper.selectByPrimaryKey(BaseDataId);
	}

	@Override
	public int updateByPrimaryKeySelective(BaseData record) {
		return BaseDataMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BaseData record) {
		return BaseDataMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<BaseData> selectBaseDatasByParentName(String parentName) {
		// TODO Auto-generated method stub
		return BaseDataMapper.selectBaseDatasByParentName(parentName);
	}

}
