package com.workup.contracts.repositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.workup.contracts.models.Contract;

public interface ContractRepository extends CassandraRepository<Contract, UUID>{

}
