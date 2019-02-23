package org.saleban.admin.service;


import org.saleban.admin.domain.User;
import org.saleban.admin.domain.security.PasswordResetToken;
import org.saleban.admin.domain.security.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordTokenForUser(final String token);
    void createPasswordResetTokenUser(final User user, final String token);
    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    List<User> findAll();

    void delete(Long id);

    Optional<User> findOne(Long id);
}
