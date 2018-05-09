package com.lzt.service;

import com.lzt.dao.EmpDao;
import com.lzt.domain.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Title
 * @Description
 * @Author:lizitao
 * @Create 2018/5/8
 * @Version 1.0
 * @Copyright:2016 www.jointem.com
 */
@Service
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpDao empDao;

    @Override
    public void saveEmp(Emp emp) {
        empDao.saveEmp(emp);
    }

    @Cacheable(value = "getEmpByKey")
    @Override
    public Emp getEmp(Integer empno) {
        System.out.println("数据库查询");
        return empDao.getEmp(empno);
    }

    @CacheEvict(value = {"getEmpByKey"})
    @Override
    public void deleteEmp(Integer empno) {
        System.out.println("数据库删除");
        //empDao.deleteEmp(empno);
    }
}
