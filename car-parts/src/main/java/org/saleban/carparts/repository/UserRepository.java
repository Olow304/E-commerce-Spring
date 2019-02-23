package org.saleban.carparts.repository;

import org.saleban.carparts.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User c SET c.email = :email WHERE c.id = :userId")
    int updateEmail(@Param("userId") Long userId, @Param("email") String email);
}
