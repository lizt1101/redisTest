package com.lzt.service;

import java.util.List;

import com.lzt.entity.Emp;

public interface EmpService {
	
	public Emp getEmplist(Integer empno);
	
	public List<Emp> getList();
	public void updateById(Integer empno);

}
