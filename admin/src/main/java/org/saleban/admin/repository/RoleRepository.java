package org.saleban.admin.repository;

import org.saleban.admin.domain.security.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
