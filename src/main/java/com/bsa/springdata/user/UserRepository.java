package com.bsa.springdata.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByLastNameContainingIgnoreCaseOrderByLastNameAsc(String lastName, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.office.city = :city ORDER BY u.lastName ASC")
    List<User> findAllByCity(String city);

    @Query("SELECT u FROM User u WHERE u.office.city = :city AND u.team.room = :room ORDER BY u.lastName ASC")
    List<User> findAllByCityAndRoom(String city, String room);

    List<User> findAllByExperienceGreaterThanEqualOrderByExperienceDesc(int experience);

    @Modifying
    int deleteAllByExperienceLessThan(int experience);
}
