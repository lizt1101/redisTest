package com.lzt.dao;

import com.lzt.domain.Emp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpDao {
	
	Emp getEmp(Integer empno);
	
	List<Emp> getList();
	
	void updateById(Integer empno);

	int saveEmp(Emp emp);

	int deleteEmp(Integer empno);
}
