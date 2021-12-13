package com.springframework.ssm.demo.config;

import com.springframework.ssm.demo.domain.EmployeeEvent;
import com.springframework.ssm.demo.domain.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
        states.withStates()
                .initial(EmployeeState.ADD)
                .state(EmployeeState.IN_CHECK)
                .and()
                    .withStates()
                        .parent(EmployeeState.IN_CHECK)
                        .initial(EmployeeState.SECURITY_CHECK_STARTED)
                        .end(EmployeeState.SECURITY_CHECK_FINISHED)
                        .and()
                    .withStates()
                        .parent(EmployeeState.IN_CHECK)
                        .initial(EmployeeState.WORK_PERMIT_CHECK_STARTED)
                        .end(EmployeeState.WORK_PERMIT_CHECK_FINISHED)
                .state(EmployeeState.APPROVED)
                .end(EmployeeState.ACTIVE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(EmployeeState.ADD).target(EmployeeState.IN_CHECK).event(EmployeeEvent.IN_CHECK)
                .and()

                .withExternal()
                .source(EmployeeState.IN_CHECK).target(EmployeeState.SECURITY_CHECK_STARTED ).event(EmployeeEvent.SECURITY_CHECK_STARTED )
                .and()

                .withExternal()
                .source(EmployeeState.SECURITY_CHECK_STARTED).target(EmployeeState.SECURITY_CHECK_FINISHED ).event(EmployeeEvent.SECURITY_CHECK_FINISHED )
                .and()

                .withExternal()
                .source(EmployeeState.SECURITY_CHECK_STARTED).target(EmployeeState.SECURITY_CHECK_FINISHED ).event(EmployeeEvent.SECURITY_CHECK_FINISHED )
                .and()

                .withExternal()
                .source(EmployeeState.SECURITY_CHECK_FINISHED).target(EmployeeState.WORK_PERMIT_CHECK_STARTED ).event(EmployeeEvent.WORK_PERMIT_CHECK_STARTED )
                .and()

                .withExternal()
                .source(EmployeeState.WORK_PERMIT_CHECK_STARTED).target(EmployeeState.WORK_PERMIT_CHECK_FINISHED ).event(EmployeeEvent.WORK_PERMIT_CHECK_FINISHED )
                .and()

                .withExternal()
                .source(EmployeeState.SECURITY_CHECK_STARTED).target(EmployeeState.WORK_PERMIT_CHECK_STARTED ).event(EmployeeEvent.WORK_PERMIT_CHECK_STARTED )
                .and()

                .withExternal()
                .source(EmployeeState.SECURITY_CHECK_FINISHED).target(EmployeeState.WORK_PERMIT_CHECK_STARTED ).event(EmployeeEvent.WORK_PERMIT_CHECK_STARTED )
                .and()

                .withExternal()
                .source(EmployeeState.SECURITY_CHECK_FINISHED).target(EmployeeState.WORK_PERMIT_CHECK_FINISHED ).event(EmployeeEvent.WORK_PERMIT_CHECK_FINISHED )
                .and()

                .withExternal()
                .source(EmployeeState.WORK_PERMIT_CHECK_FINISHED).target(EmployeeState.APPROVED).event(EmployeeEvent.APPROVED)
                .and()

                .withExternal()
                .source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE).event(EmployeeEvent.ACTIVE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config) throws Exception {
        StateMachineListenerAdapter<EmployeeState, EmployeeEvent> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<EmployeeState, EmployeeEvent> from, State<EmployeeState, EmployeeEvent> to) {
                log.info(String.format("statechanged(from:%s, to:%s)",from,to));
            }
        };
        config.withConfiguration().listener(adapter);
    }

}
