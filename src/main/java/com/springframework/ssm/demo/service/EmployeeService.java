package com.springframework.ssm.demo.service;

import com.springframework.ssm.demo.domain.Employee;
import com.springframework.ssm.demo.domain.EmployeeEvent;
import com.springframework.ssm.demo.domain.EmployeeState;
import org.springframework.statemachine.StateMachine;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    StateMachine<EmployeeState, EmployeeEvent> checkin(Long employeeId);
    StateMachine<EmployeeState, EmployeeEvent> approve(Long employeeId);
    StateMachine<EmployeeState, EmployeeEvent> active(Long employeeId);

}
