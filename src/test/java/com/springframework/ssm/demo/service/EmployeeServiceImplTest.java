package com.springframework.ssm.demo.service;

import com.springframework.ssm.demo.domain.Contract;
import com.springframework.ssm.demo.domain.Employee;
import com.springframework.ssm.demo.domain.EmployeeEvent;
import com.springframework.ssm.demo.domain.EmployeeState;
import com.springframework.ssm.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;
    Employee employee;

    @BeforeEach
    void setUp() {
        Contract contarct = Contract.builder().salary(BigDecimal.valueOf(12.5)).build();
        employee = Employee.builder().age(20).email("samira@gmail.com").firstName("Samira").lastName("radmanesh").contract(contarct).build();
    }

    @Test
    @Transactional
    void preAuth() {

        Employee savedEmployee = employeeService.addEmployee(employee);
        System.out.println("should be Add  "+ savedEmployee.getEmployeeState());

        employeeService.checkin(savedEmployee.getId());
        Employee checkin = employeeRepository.getOne(savedEmployee.getId());
        System.out.println("should be checkin  "+ checkin.getEmployeeState());

        employeeService.approve(savedEmployee.getId());
        Employee approve = employeeRepository.getOne(savedEmployee.getId());
        System.out.println("should be approve  "+ approve.getEmployeeState());

        employeeService.active(savedEmployee.getId());
        Employee active = employeeRepository.getOne(savedEmployee.getId());
        System.out.println("should be active  "+ active.getEmployeeState());





    }
}