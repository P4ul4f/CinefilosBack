package com.movies.cinefilos.Repositories;

import com.movies.cinefilos.Entities.Role;
import com.movies.cinefilos.Entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findRolesByRoleEnumIn(List<RoleEnum>roleEnums);

    Optional<Role> findRoleByRoleEnum(RoleEnum roleEnum);

}
