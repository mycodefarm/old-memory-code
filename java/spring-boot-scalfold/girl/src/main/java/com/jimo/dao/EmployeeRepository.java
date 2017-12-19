package com.jimo.dao;

import org.springframework.data.repository.CrudRepository;

import com.jimo.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
