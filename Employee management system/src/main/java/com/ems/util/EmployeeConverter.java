package com.ems.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ems.entity.Employee;
import com.ems.model.EmployeeDTO;

@Component
public class EmployeeConverter {
	
	//convert from employee to employeeDTO
	public EmployeeDTO convertToEmpDTO(Employee emp)
	{
		EmployeeDTO eDto = new EmployeeDTO();
		if(emp!=null)
		{
			BeanUtils.copyProperties(emp, eDto);
		}
		return eDto;
	}
	
	
	//convert from employeeDTO to employee
	public Employee convertToEntity(EmployeeDTO eDto)
	{
		Employee emp = new Employee();
		if(eDto!=null)
		{
			BeanUtils.copyProperties(eDto, emp);
		}
		return emp;
	}

}
