package com.lzt.dao;

import java.util.List;

import com.lzt.entity.Emp;

public interface EmpDao {
	
	public Emp getEmpList(Integer empno);
	
	public List<Emp> getList();
	
	public void updateById(Integer empno);
}
