package com.sz91online.bgms.module.user.service.mybatis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.user.dao.DeptMapper;
import com.sz91online.bgms.module.user.dao.DeptMapperExt;
import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.bgms.module.user.service.DeptService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@EnableCaching
public class DeptServiceImpl extends DefaultSearchService<Dept> implements DeptService  {

	@Autowired
	private DeptMapper deptMapper;
	
	@Autowired
	private DeptMapperExt deptMapperExt;
	
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return deptMapperExt;
	}

	@Override
	public ICrudGenericDAO<Dept> getCrudMapper() {
		return deptMapper;
	}

	@Override
	//@Cacheable(value="memoryMapCache",key="'subDept'+#deptCode")
	public List<Dept> getSubDeptByDeptId(String deptCode) {
		
		List<Dept> deptList = new ArrayList<>();
		querySubDept(deptCode,deptList);
		
		return deptList;
	}

	private void querySubDept(String deptCode,List<Dept> deptList){
		List<Dept> subList = deptMapperExt.getSubDeptByDeptId(deptCode);
		deptList.addAll(subList);
		for (Iterator<Dept> iterator = subList.iterator(); iterator.hasNext();) {
			Dept dept = iterator.next();
			querySubDept(dept.getDeptCode(),deptList);
		}
	}
}
