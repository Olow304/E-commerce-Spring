package org.saleban.carparts.utility;

import org.saleban.carparts.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    public SimpleMailMessage constructorResetTokenEmail(String cp, Locale locale, String token, User user, String pass){
        String url = cp + "/newUser?token="+token;
        String message = "\nYour new password is: " + pass + "\nIf you want to change your password, please contact us at olow304@gmail.com";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("New user registered - auto parts (spring application)");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public SimpleMailMessage sendPriceByEmail(String name, String price, String qty, String email, String subject, String msg){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Product Name - " + name + " - " + subject);
        simpleMailMessage.setText("Product: " + name + "\nMessage: " + msg + "\nAsking Price: " + price + "\nRequester: " + email + "\nQuantity: " + qty);
        simpleMailMessage.setFrom(env.getProperty("support.email"));
        return simpleMailMessage;
    }
}
