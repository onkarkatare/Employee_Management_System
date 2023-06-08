package com.ems.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.exception.ResourceNotFoundException;
import com.ems.model.EmployeeDTO;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;
import com.ems.util.EmployeeConverter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j //adding logger using annotation from lombok
public class EmployeeServiceImpl implements EmployeeService{

	//logger statically created
	private static final Logger log = LoggerFactory.getLogger(Employee.class);
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private DepartmentRepository deptRepository;
	
	@Autowired
	private EmployeeConverter converter;
	
	@Override
	public String createEmployee(Employee employee) {
		
		employee.setUserName(employee.getUserName());
		employee.setPassword(employee.getPassword());
		employee.setRole("employee");
		
		empRepository.save(employee);
		
		if(employee!=null)
		{
			return "Employee saved successfully.";
		}
		
		return "Employee details not saved.";
		
	}
	
	//method to save new employee details and return employee details
	@Override
	public EmployeeDTO saveEmployee(Employee employee) {
		
		employee.setUserName(employee.getUserName());
		employee.setPassword(employee.getPassword());
		employee.setRole("employee");
		
		empRepository.save(employee);
		log.info("New employee details added at "+new Date());
		return converter.convertToEmpDTO(employee);
	}

	//method to get/fetch employee details using id
	@Override
	public EmployeeDTO getEmployeeById(int id) {
		Employee emp = empRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Employee", "Id", id));
		log.info("Employee with id "+id+" details fetched at "+new Date());
		return converter.convertToEmpDTO(emp);
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> employees = empRepository.findAll();
		
		List<EmployeeDTO> employeeDTO = new ArrayList<>();
		for(Employee e: employees)
		{
			employeeDTO.add(converter.convertToEmpDTO(e));
		}
		log.info("All employee details fetched at "+new Date());
		return employeeDTO;
	}

	@Override
	public EmployeeDTO updateEmployee(int id, Employee employee) {
		//we need to check whether employee exist with id or not in DB
		Employee existEmp = empRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Employee", "Id", id));
		
		//we will get data from the client and set it in existing employee
		existEmp.setEmpName(employee.getEmpName());
		existEmp.setSalary(employee.getSalary());
		existEmp.setContact(employee.getContact());
		existEmp.setEmail(employee.getEmail());
		existEmp.setDesignation(employee.getDesignation());
		existEmp.setDoj(employee.getDoj());
		existEmp.setUserName(employee.getUserName());
		existEmp.setPassword(employee.getPassword());
		
		empRepository.save(existEmp);
		log.info("Employee with id "+id+" details updated at "+new Date());
		return converter.convertToEmpDTO(existEmp);
	}

	@Override
	public String deleteEmployeeById(int id) {
		String msg=null;
		
		Optional<Employee> opEmp = empRepository.findById(id);
		if(opEmp.isPresent())
		{
			Department d =deptRepository.findById(opEmp.get().getDept().getDeptId()).get();
			d.setTotalEmp(d.getTotalEmp()-1);
			empRepository.deleteById(id);
			deptRepository.save(d);
			msg = "Record deleted successfully";
			log.info("Employee with id "+id+" deleted at "+new Date());
		}
		else
		{
			log.error("Employee with id "+id+ " not found.");
			throw new ResourceNotFoundException("Employee","ID",id);
		}
		return msg;
	}

	@Override
	public void deleteAllEmployees() {
		empRepository.deleteAll();
		
	}

	@Override
	public EmployeeDTO assignEmpToDept(int empId, int deptId) {
		Employee emp = empRepository.findById(empId).get();
		Department dept = deptRepository.findById(deptId).get();
		
		emp.setDept(dept); //setting dept with id deptID to employee
//		dept.setEmployees(List.of(emp));
		//increasing total employees by 1
		dept.setTotalEmp(dept.getTotalEmp()+1);
		
		deptRepository.save(dept);
		log.info("Empoloyee with id "+empId+" is assigned to department id "+deptId+" at "+new Date());
		return converter.convertToEmpDTO(emp);
	}

	//query method to get all employee details working under a department using department id
	@Override
	public List<EmployeeDTO> getEmpByDeptId(int deptId) {
		List<Employee> emps = empRepository.getEmployeeByDeptId(deptId);
		List<EmployeeDTO> dto = new ArrayList<>();
		
		for(Employee e : emps)
		{
			dto.add(converter.convertToEmpDTO(e));
		}
		return dto;
	}

	//query method to get employee details using employee name
	@Override
	public List<EmployeeDTO> getEmpByEmpName(String name) {
		List<Employee> emps = empRepository.getByEmpName(name);
		List<EmployeeDTO> dto = new ArrayList<>();
		
		for(Employee e : emps)
		{
			dto.add(converter.convertToEmpDTO(e));
		}
		return dto;
	}

	
}
