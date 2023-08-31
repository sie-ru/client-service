package com.test.client.repository;

import com.test.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Modifying
    @Query(value = "DELETE FROM client AS c WHERE c.id = :clientId", nativeQuery = true)
    void deleteClientById(@Param("clientId") Long clientId);
}
