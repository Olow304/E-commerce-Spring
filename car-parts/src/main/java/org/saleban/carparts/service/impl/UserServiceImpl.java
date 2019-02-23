package org.saleban.carparts.service.impl;

import org.saleban.carparts.domain.User;
import org.saleban.carparts.repository.PasswordResetTokenRepository;
import org.saleban.carparts.repository.RoleRepository;
import org.saleban.carparts.repository.UserRepository;
import org.saleban.carparts.security.PasswordResetToken;
import org.saleban.carparts.security.UserRole;
import org.saleban.carparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PasswordResetToken getPasswordTokenForUser(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenUser(User user, String token) {
        final PasswordResetToken mytoken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(mytoken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception{
        User localUser = userRepository.findByUsername(user.getUsername());
        if(localUser != null){
            user.setId(localUser.getId());
            userRepository.updateEmail(user.getId(),localUser.getEmail());
        }else{
            for(UserRole ur : userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRole().addAll(userRoles);
            localUser = userRepository.save(user);
        }
        return localUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
