package com.springframework.ssm.demo.repository;

import com.springframework.ssm.demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
