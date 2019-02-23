package org.saleban.carparts.service;

import org.saleban.carparts.domain.User;
import org.saleban.carparts.security.PasswordResetToken;
import org.saleban.carparts.security.UserRole;

import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordTokenForUser(final String token);
    void createPasswordResetTokenUser(final User user, final String token);
    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}
