package com.springframework.ssm.demo;

import com.springframework.ssm.demo.domain.EmployeeEvent;
import com.springframework.ssm.demo.domain.EmployeeState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

    @Test
    void contextLoads() {
        StateMachine<EmployeeState, EmployeeEvent> sm = stateMachineFactory.getStateMachine();
        sm.start();
        System.out.println(sm.getState().toString());

        sm.sendEvent(EmployeeEvent.IN_CHECK);
        System.out.println(sm.getState().toString());
        sm.sendEvent(EmployeeEvent.ACTIVE);
        System.out.println(sm.getState().toString());
        sm.sendEvent(EmployeeEvent.APPROVED);
        System.out.println(sm.getState().toString());

    }

}
