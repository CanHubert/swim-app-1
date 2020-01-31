package com.can.app.swim.swimapp.repository;

import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(EnumRole roleUser);
}
