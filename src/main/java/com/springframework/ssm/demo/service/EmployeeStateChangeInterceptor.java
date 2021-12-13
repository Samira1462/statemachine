package com.springframework.ssm.demo.service;

import com.springframework.ssm.demo.domain.Employee;
import com.springframework.ssm.demo.domain.EmployeeEvent;
import com.springframework.ssm.demo.domain.EmployeeState;
import com.springframework.ssm.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EmployeeStateChangeInterceptor extends StateMachineInterceptorAdapter<EmployeeState, EmployeeEvent> {

    private final EmployeeRepository employeeRepository;

    @Override
    public void preStateChange(State<EmployeeState, EmployeeEvent> state, Message<EmployeeEvent> message, Transition<EmployeeState, EmployeeEvent> transition, StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
        Optional.ofNullable(message).ifPresent(msg->{
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault("employee_id",-1L)))
                    .ifPresent(employeeId -> {
                        Employee employee = employeeRepository.getOne(employeeId);
                        employee.setEmployeeState(state.getId());
                        employeeRepository.save(employee);
                    });
        });
    }
}
