package com.waait.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.waait.dto.Employee;

public interface EmployeeDao {
	
	int insertEmployee(SqlSession session,Employee employee);
	Employee selectEmployee(SqlSession session,String empId);
	List<Employee> selectAllEmployees(SqlSession session);
	Employee selectByEmpNo(SqlSession session, int no);
	
}
