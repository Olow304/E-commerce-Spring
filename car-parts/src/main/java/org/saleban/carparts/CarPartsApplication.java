package org.saleban.carparts;

import org.saleban.carparts.domain.User;
import org.saleban.carparts.security.Role;
import org.saleban.carparts.security.UserRole;
import org.saleban.carparts.service.UserService;
import org.saleban.carparts.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CarPartsApplication {

	// implements CommandLineRunner

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(CarPartsApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user1 = new User();
//		user1.setFirstname("saleban");
//		user1.setLastname("olow");
//		user1.setUsername("sale");
//		user1.setPassword(SecurityUtility.passwordEncoder().encode("123"));
//		user1.setEmail("olow304@gmail.com");
//		Set<UserRole> userRoles = new HashSet<>();
//		Role role1 = new Role();
//		role1.setRoleId(1);
//		role1.setName("ROLE_USER");
//		userRoles.add(new UserRole(user1, role1));
//
//		userService.createUser(user1, userRoles);
//
//	}
}

