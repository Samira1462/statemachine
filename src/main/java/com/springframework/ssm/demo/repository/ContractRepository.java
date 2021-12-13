package com.springframework.ssm.demo.repository;

import com.springframework.ssm.demo.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {
}
