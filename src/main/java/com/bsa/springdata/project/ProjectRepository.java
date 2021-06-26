package com.bsa.springdata.project;


import com.bsa.springdata.project.dto.ProjectSummaryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query("SELECT p FROM Project p " +
            "INNER JOIN Team t ON t.project = p " +
            "WHERE t.technology.name = :technology " +
            "GROUP BY p " +
            "ORDER BY SUM(t.users.size) ASC")
    List<Project> findFiveByTechnology(String technology, Pageable pageable);

    @Query("SELECT COUNT (DISTINCT p) FROM Project p " +
            "INNER JOIN Team t ON t.project = p " +
            "INNER JOIN User u ON u.team = t " +
            "INNER JOIN Role r ON u IN (SELECT user FROM r.users user) " +
            "WHERE r.name = :role ")
    int getCountWithRole(String role);

    @Query("SELECT p FROM Project p " +
            "INNER JOIN Team t ON t.project = p " +
            "GROUP BY p " +
            "ORDER BY p.teams.size DESC, SUM(t.users.size) DESC, p.name DESC")
    List<Project> findTheBiggestProject(Pageable pageable);

    @Query(value = "SELECT p.name as Name, COUNT(DISTINCT t) as TeamsNumber, " +
            "COUNT(DISTINCT u) as DevelopersNumber , " +
            "string_agg(DISTINCT tech.name, ',') as Technologies " +
            "FROM projects p " +
            "INNER JOIN teams t ON t.project_id=p.id " +
            "INNER JOIN users u ON u.team_id=t.id " +
            "INNER JOIN technologies tech ON tech.id=t.technology_id " +
            "GROUP BY p.name " +
            "ORDER BY p.name", nativeQuery = true)
    List<ProjectSummaryDto> getSummary();
}