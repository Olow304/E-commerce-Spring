package org.saleban.carparts.repository;

import org.springframework.data.repository.CrudRepository;
import org.saleban.carparts.security.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
