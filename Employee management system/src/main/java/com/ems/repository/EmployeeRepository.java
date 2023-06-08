package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	@Query("from Employee where dept=(from Department where deptId=:d)")
	List<Employee> getEmployeeByDeptId(@Param("d") int deptId);
	
	//get Emp using Name
	List<Employee> getByEmpName(String name);
	
	//get Emp using email
}
