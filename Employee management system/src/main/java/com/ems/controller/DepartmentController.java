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

import com.ems.entity.Department;
import com.ems.model.DepartmentDTO;
import com.ems.service.DepartmentService;
import com.ems.util.DepartmentConverter;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private DepartmentConverter converter;
	
//	@PostMapping("/createDepartment")
//	public String createDepatment(@RequestBody Department department)
//	{
//		return deptService.createDepartment(department);
//	}

	//@PostMapping("/saveDepartment")
	@PostMapping
	public DepartmentDTO saveDepartment(@Valid @RequestBody DepartmentDTO ddto)
	{
		Department dept = converter.convertToDeptEntity(ddto);
		return deptService.saveDepartment(dept);
	}
	
	//@GetMapping("/getDepartment/{deptId}")
	@GetMapping("/{deptId}")
	public DepartmentDTO getDepartmentById(@PathVariable("deptId") int id)
	{
		return deptService.getDepartmentById(id);
	}
	
	//@GetMapping("/getAllDepartments")
	@GetMapping
	public List<DepartmentDTO> getAllDepartments()
	{
		return deptService.getAllDepartments();
	}
	
	//@PutMapping("/updateDepartment/{id}")
	@PutMapping("/{deptId}")
	public DepartmentDTO updateDepartment(@PathVariable("deptId") int id,
			@Valid @RequestBody DepartmentDTO ddto)
	{
		Department d = converter.convertToDeptEntity(ddto);
		return deptService.updateDepartment(id, d);
	}
	
	//@DeleteMapping("/deleteDeptById/{id}")
	@DeleteMapping("/{id}")
	public String deleteDepartmentById(@PathVariable("id") int id)
	{
		return deptService.deleteDepartmentById(id);
	}
	
	//@DeleteMapping("/deleteAllDepts")
	@DeleteMapping
	public ResponseEntity<String> deleteAllDepartment()
	{
		deptService.deleteAllDepartments();
		return new ResponseEntity<String>("All department details deleted successfully", HttpStatus.OK);
	}
	
	@GetMapping("/getDeptByName/{name}")
	public List<DepartmentDTO> getDeptByName(@PathVariable("name") String name)
	{
		return deptService.getDeptByDeptName(name);
	}
}
