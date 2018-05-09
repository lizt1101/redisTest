package com.lzt.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lzt.dao.EmpDao;
import com.lzt.entity.Emp;

@Service("empService")
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmpDao empDao;
	
	@Cacheable(value="common",key="'empno_'+#empno")
	public Emp getEmplist(Integer empno) {
		System.out.println(123);
		return empDao.getEmpList(empno);
	}
	
	@Cacheable(value="EmpList")
	public List<Emp> getList(){
		return empDao.getList();
		
	}
	
	@CachePut(value="common",key="'empno_'+#empno")
	public void updateById(Integer empno){
		empDao.updateById(empno);
	}
	
	
	

}














