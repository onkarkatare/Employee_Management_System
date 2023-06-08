package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	//get Dept using deptName
	List<Department> getByDeptName(String name);
	
	//get Dept using location
}
