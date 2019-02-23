package org.saleban.carparts.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SecurityUtility {
    static final String SALT = "salt";

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Bean
    public static String randomPassword(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ124567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while(salt.length() < 18){
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltstr = salt.toString();
        return saltstr;
    }
}
