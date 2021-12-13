package com.springframework.ssm.demo.service;

import com.springframework.ssm.demo.domain.Contract;
import com.springframework.ssm.demo.domain.Employee;
import com.springframework.ssm.demo.domain.EmployeeEvent;
import com.springframework.ssm.demo.domain.EmployeeState;
import com.springframework.ssm.demo.repository.ContractRepository;
import com.springframework.ssm.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;
    private final EmployeeStateChangeInterceptor employeeStateChangeInterceptor;

    @Override
    public Employee addEmployee(Employee employee) {

        Contract contract = employee.getContract();

        if (contract == null) {
            log.info("there is no contract for the employee.");
            return null;
        }

        contractRepository.save(contract);

        employee.setEmployeeState(EmployeeState.ADD);
        employee.setCreationOn(new Date());

        return employeeRepository.save(employee);

    }

    @Transactional
    @Override
    public StateMachine<EmployeeState, EmployeeEvent> checkin(Long employeeId) {
        StateMachine<EmployeeState, EmployeeEvent> sm = build(employeeId);
        sendEvent(employeeId,sm, EmployeeEvent.IN_CHECK);
        return sm;
    }

    @Transactional
    @Override
    public StateMachine<EmployeeState, EmployeeEvent> approve(Long employeeId) {
        StateMachine<EmployeeState, EmployeeEvent> sm = build(employeeId);
        sendEvent(employeeId,sm, EmployeeEvent.APPROVED);
        return sm;
    }

    @Transactional
    @Override
    public StateMachine<EmployeeState, EmployeeEvent> active(Long employeeId) {
        StateMachine<EmployeeState, EmployeeEvent> sm = build(employeeId);
        sendEvent(employeeId,sm, EmployeeEvent.ACTIVE);
        return sm;
    }

    private StateMachine<EmployeeState, EmployeeEvent> build(Long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        StateMachine<EmployeeState, EmployeeEvent> sm = stateMachineFactory.getStateMachine(Long.toString(employee.getId()));
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachine(new DefaultStateMachineContext<>(employee.getEmployeeState(),null,null,null,null));
                    sma.addStateMachineInterceptor(employeeStateChangeInterceptor);
                });
        sm.start();
        return sm;
    }

    private void sendEvent(Long employeeId, StateMachine<EmployeeState, EmployeeEvent> sm , EmployeeEvent event){
        Message msg = MessageBuilder.withPayload(event)
                .setHeader("employee_id",employeeId)
                .build();
        sm.sendEvent(msg);
    }
}
