package org.saleban.admin;

import org.saleban.admin.domain.User;
import org.saleban.admin.domain.security.Role;
import org.saleban.admin.domain.security.UserRole;
import org.saleban.admin.service.UserService;
import org.saleban.admin.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AdminApplication{

	//implements CommandLineRunner

	@Autowired
	private UserService userService;


	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user1 = new User();
//		user1.setUsername("admin");
//		user1.setPassword(SecurityUtility.passwordEncoder().encode("123"));
//		user1.setEmail("admin@carpart.com");
//		Set<UserRole> userRoles = new HashSet<>();
//		Role role1 = new Role();
//		role1.setRoleId(3);
//		role1.setName("ROLE_ADMIN");
//		userRoles.add(new UserRole(user1, role1));
//
//		userService.createUser(user1, userRoles);
//
//	}
}

