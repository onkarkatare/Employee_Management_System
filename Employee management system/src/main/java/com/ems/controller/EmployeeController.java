package com.ems.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.entity.Employee;
import com.ems.model.EmployeeDTO;
import com.ems.service.EmployeeService;
import com.ems.util.EmployeeConverter;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private EmployeeConverter converter;
	
	//@PostMapping("/createEmployee")
//	@PostMapping
//	public String createEmployee(@RequestBody Employee employee)
//	{
//		return empService.createEmployee(employee);
//	}

	//@PostMapping("/saveEmployee")
	@PostMapping
	public EmployeeDTO saveEmployee(@Valid @RequestBody EmployeeDTO edto)
	{
		Employee emp = converter.convertToEntity(edto);
		return empService.saveEmployee(emp);
	}
	
	//@GetMapping("getEmployeeById/{empId}")
	@GetMapping("/{empId}")
	public EmployeeDTO getEmployeeById(@PathVariable("empId") int id)
	{
		return empService.getEmployeeById(id);
	}
	
	//@GetMapping("/getAllEmployees")
	@GetMapping
	public List<EmployeeDTO> getAllEmployees()
	{
		return empService.getAllEmployees();
	}
	
	//@PutMapping("/updateEmployee/{id}")
	@PutMapping("/{empId}")
	public EmployeeDTO updateEmployee(@PathVariable("empId") int id, 
			@Valid @RequestBody EmployeeDTO edto)
	{
		Employee e = converter.convertToEntity(edto);
		return empService.updateEmployee(id, e);
	}
	
	//@DeleteMapping("/deleteEmpById/{id}")
	@DeleteMapping("/{id}")
	public String deleteEmployeeById(@PathVariable("id") int id)
	{
		return empService.deleteEmployeeById(id);
	}
	
	//@DeleteMapping("/deleteAllEmps")
	@DeleteMapping
	public ResponseEntity<String> deleteAllEmployee()
	{
		 empService.deleteAllEmployees();
		 return new ResponseEntity<String>("All employee details deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("/assignEmp/{empId}/assignToDept/{deptId}")
	public EmployeeDTO assignEmpToDept(@PathVariable("empId") int empId,
			@PathVariable("deptId") int deptId)
	{
		return empService.assignEmpToDept(empId, deptId);
	}
	
	@GetMapping("/getEmpByDeptId/{deptId}")
	public List<EmployeeDTO> getEmpByDeptId(@PathVariable("deptId") int deptId)
	{
		return empService.getEmpByDeptId(deptId);
	}
	
	@GetMapping("/getEmpByName/{name}")
	public List<EmployeeDTO> getEmpByName(@PathVariable("name") String name)
	{
		return empService.getEmpByEmpName(name);
	}
}
