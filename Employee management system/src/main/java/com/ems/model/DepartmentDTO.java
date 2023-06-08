package com.ems.model;

import java.util.List;

import javax.persistence.OneToMany;

import com.ems.entity.Employee;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartmentDTO {

	private int deptId;
	
	private String deptName;
	
	private int totalEmp;
	
	private String location;
	
	@OneToMany
	private List<Employee> employees;
}
