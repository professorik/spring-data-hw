package com.bsa.springdata.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfficeRepository extends JpaRepository<Office, UUID> {
    @Query("SELECT DISTINCT o FROM Office o INNER JOIN User u ON u.office = o " +
            "INNER JOIN Team t ON u.team = t " +
            "WHERE t.technology.name = :technology")
    List<Office> getOfficesByTechnology(String technology);

    @Modifying
    @Transactional
    @Query("UPDATE Office o SET o.address = :newAddress " +
            "WHERE o.address = :oldAddress AND " +
            "EXISTS (SELECT u FROM User u WHERE u.office = o AND " +
            "EXISTS (SELECT p FROM Project p WHERE u.team " +
            "IN (SELECT t FROM p.teams t)))")
    void updateAddressIfExist(String oldAddress, String newAddress);

    Optional<Office> getAllOfficeByAddress(String newAddress);
}
