package com.lzt.service;

import com.lzt.domain.Emp;

/**
 * @Title
 * @Description
 * @Author:lizitao
 * @Create 2018/5/8
 * @Version 1.0
 * @Copyright:2016 www.jointem.com
 */
public interface EmpService {

    void saveEmp(Emp emp);

    Emp getEmp(Integer empno);

    void deleteEmp(Integer empno);


}
