package com.bsa.springdata.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByName(String teamName);

    Long countByTechnologyName(String newTechnology);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teams u SET name = CONCAT(u.name,'_',p.name,'_',t.name) " +
            "FROM projects p, technologies t WHERE u.name = :name AND p.id = u.project_id AND t.id = u.technology_id",
            nativeQuery = true)
    void normalizeName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Team u SET u.technology = :newTechnology " +
            "WHERE u.users.size < :devsNumber " +
            "AND u.technology = :oldTechnology")
    void setTechnology(int devsNumber, Technology oldTechnology, Technology newTechnology);
}
