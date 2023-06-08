package com.ems.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.exception.ResourceNotFoundException;
import com.ems.model.DepartmentDTO;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.DepartmentService;
import com.ems.util.DepartmentConverter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService{
	
	//logger statically created
		private static final Logger log = LoggerFactory.getLogger(Department.class);
		
	@Autowired
	private DepartmentRepository deptRepository;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private DepartmentConverter converter;
	
	@Override
	public String createDepartment(Department department) {
		
		department.setDeptName(department.getDeptName());
		department.setLocation(department.getLocation());
		department.setTotalEmp(department.getTotalEmp());
		
		deptRepository.save(department);
		
		if(department!=null)
		{
			return "Department saved successfully.";
		}
		return "Department details not saved.";
		
	}

	//method to save new department details and return department details
	@Override
	public DepartmentDTO saveDepartment(Department department) {
		deptRepository.save(department);
		
		return converter.convertToDeptDTO(department);
	}

	//method to get/fetch department details using id
	@Override
	public DepartmentDTO getDepartmentById(int id) {
		Department dept = deptRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Department", "Id", id));
		return converter.convertToDeptDTO(dept);
	}

	@Override
	public List<DepartmentDTO> getAllDepartments() {
		List<Department> departments = deptRepository.findAll();
		
		List<DepartmentDTO> departmentDTO = new ArrayList<>();
		for(Department d: departments)
		{
			departmentDTO.add(converter.convertToDeptDTO(d));
		}
		return departmentDTO;
	}

	@Override
	public DepartmentDTO updateDepartment(int id, Department department) {
		//we need to check whether department exist with id or not in DB
		Department existDept = deptRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Department", "Id", id));
		
		//we will get data and set it in existing department
		existDept.setDeptName(department.getDeptName());
		existDept.setLocation(department.getLocation());
		existDept.setTotalEmp(department.getTotalEmp());
		
		deptRepository.save(existDept);
		
		return converter.convertToDeptDTO(existDept);
	}

	@Override
	public String deleteDepartmentById(int id) {
		String msg=null;
		
		Optional<Department> opDept = deptRepository.findById(id);
		if(opDept.isPresent())
		{
// need ? to add list of employees and make department as null	
			deptRepository.deleteById(id);
			msg = "Record deleted successfully";
		}
		else
		{
			throw new ResourceNotFoundException("Department", "Id", id);
		}
		return msg;
	}

	@Override
	public void deleteAllDepartments() {
		deptRepository.deleteAll();
		
	}

	//query method to get department details using department name
	@Override
	public List<DepartmentDTO> getDeptByDeptName(String name) {
		List<Department> dept = deptRepository.getByDeptName(name);
		List<DepartmentDTO> dto = new ArrayList<>();
		
		for(Department d : dept)
		{
			dto.add(converter.convertToDeptDTO(d));
		}
		return dto;
	}

}
