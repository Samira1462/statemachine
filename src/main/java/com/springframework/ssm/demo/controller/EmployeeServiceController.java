package com.springframework.ssm.demo.controller;

import com.springframework.ssm.demo.domain.Employee;
import com.springframework.ssm.demo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeServiceController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping(path = "/add")
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/checkin/{employee_id}")
    public ResponseEntity checkin(@PathVariable Long employee_id) {
        employeeService.checkin(employee_id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/approve/{employee_id}")
    public ResponseEntity approve(@PathVariable Long employee_id) {
        employeeService.approve(employee_id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/active/{employee_id}")
    public ResponseEntity active(@PathVariable Long employee_id) {
        employeeService.active(employee_id);
        return ResponseEntity.noContent().build();
    }

}
