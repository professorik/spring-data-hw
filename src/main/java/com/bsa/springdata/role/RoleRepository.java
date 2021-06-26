package com.bsa.springdata.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Modifying
    @Transactional
    void deleteByCodeAndUsersIsEmpty(String roleCode);
}
