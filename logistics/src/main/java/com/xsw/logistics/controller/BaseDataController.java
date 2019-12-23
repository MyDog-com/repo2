package com.xsw.logistics.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsw.logistics.model.MsgObject;
import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.BaseDataExample;
import com.xsw.logistics.pojo.BaseDataExample.Criteria;
import com.xsw.logistics.service.BaseDataService;

@Controller
@RequestMapping("/baseData")
public class BaseDataController {

	@Autowired
	private BaseDataService baseDataService;
	


	@RequestMapping("/baseDataPage")
	public String baseDataPage(Model m) {
		return "baseDataPage";
	}

	//查出所有的管理员信息
	@RequestMapping("list")
	@ResponseBody
	public PageInfo<BaseData> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		BaseDataExample example = new BaseDataExample();
		if (StringUtils.isNotBlank(keyword)) {
			Criteria criteria = example.createCriteria();
			criteria.andBaseNameLike("%"+keyword+"%");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andBaseDescLike("%"+keyword+"%");
			example.or(criteria2);
			
		}

		List<BaseData> baseDatas = baseDataService.selectByExample(example);
		PageInfo<BaseData> pageInfo = new PageInfo<>(baseDatas);
		return pageInfo;
	}
	//删除
	@RequestMapping("delete")
	@ResponseBody
	public MsgObject delete(Long baseId) {
		
		BaseDataExample example = new BaseDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(baseId);
		List<BaseData> baseDatas = baseDataService.selectByExample(example);
		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");
		if (baseDatas.size()==0) {
			int row = baseDataService.deleteByPrimaryKey(baseId);
			if (row==1) {
				mo= new MsgObject(1, "删除数据成功!");
			}
		}else {
			mo = new MsgObject(0, "该分类下面还有字数剧，不能删除！");
		}
		
		
		
		return mo;
	}
	//编辑
	@RequestMapping("edit")
	public String edit(Model m,Long baseId) {
		if (baseId!=null) {
			BaseData baseData = baseDataService.selectByPrimaryKey(baseId);
			m.addAttribute("baseData", baseData);
		}
		BaseDataExample example = new BaseDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdIsNull();
		List<BaseData> baseDatas = baseDataService.selectByExample(example);
		m.addAttribute("baseDatas", baseDatas);
		
		return "baseDataEdit";
	}
	//新增管理员
	@RequestMapping("insert")
	@ResponseBody
	public MsgObject insert(BaseData baseData) {
		Long parentId = baseData.getParentId();
		if (parentId==0) {
			baseData.setParentId(null);
		}
		MsgObject mo = new MsgObject(0, "新增数据失败，请联系管理员！");
		
		  int row = baseDataService.insert(baseData); if (row==1) { mo = new
		  MsgObject(1, "新增数据成功!"); }
		 
		
		return mo;
		
	}
	//验证用户名唯一性
	@RequestMapping("checkBaseDataname")
	@ResponseBody
	public boolean checkBaseDataname(String baseDataname) {
		BaseDataExample example = new BaseDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andBaseNameEqualTo(baseDataname);
		List<BaseData> baseDatas = baseDataService.selectByExample(example);
		if (baseDatas.size()>0) {
			return false;
		}
		return true;
	}
	//批量删除
	@RequestMapping("deleteBatches")
	public void deleteBatches(HttpServletRequest request) {
		String baseDatas = request.getParameter("baseDatas");
		System.out.println(baseDatas);
	}
	//管理员修改
	@RequestMapping("update")
	@ResponseBody
	public MsgObject update(BaseData baseData) {
		MsgObject mo = new MsgObject(0, "修改数据失败，请联系管理员！");
		int row = baseDataService.updateByPrimaryKeySelective(baseData);
		if (row==1) {
			mo = new MsgObject(1, "修改数据成功!");
		}
		
		return mo;
		
	}
		
	
}


























