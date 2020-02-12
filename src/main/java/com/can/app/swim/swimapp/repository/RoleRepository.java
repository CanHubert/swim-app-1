package com.can.app.swim.swimapp.repository;

import com.can.app.swim.swimapp.entity.Role;
import com.can.app.swim.swimapp.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "*", maxAge = 3600)
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(EnumRole roleUser);

    List<Role> findByNameIn(List<EnumRole> names);
}
