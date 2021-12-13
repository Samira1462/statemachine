package com.springframework.ssm.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(name = "Employee")
@Table(name = "employee")
@Builder
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_on", updatable = false)
    private Date creationOn;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Column(name = "age")
    private Integer age;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EmployeeState employeeState;


    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;
}
