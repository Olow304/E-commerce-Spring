package org.saleban.carparts.controller;

import org.saleban.carparts.domain.Product;
import org.saleban.carparts.domain.User;
import org.saleban.carparts.security.PasswordResetToken;
import org.saleban.carparts.security.Role;
import org.saleban.carparts.security.UserRole;
import org.saleban.carparts.service.ProductService;
import org.saleban.carparts.service.UserSecurityService;
import org.saleban.carparts.service.UserService;
import org.saleban.carparts.util.Mappings;
import org.saleban.carparts.util.ViewNames;
import org.saleban.carparts.utility.MailConstructor;
import org.saleban.carparts.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class GetNewUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/index")
    public String redirect() {
        return "redirect:/";
    }


    @GetMapping(Mappings.ACCOUNT_LOGIN)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView(ViewNames.LOGIN_PAGE);
        mv.addObject("classActiveLogin", true);
        return mv;
    }

    /*
     * Home Page - GET
     */
    @GetMapping(Mappings.HOME)
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView(ViewNames.HOME_PAGE);
        List<Product> productList = productService.findAll();
        Product product4 = productService.findProductById(4L);
        Product product6 = productService.findProductById(6L);

        Product product2 = productService.findProductById(2L);
        Product product3 = productService.findProductById(3L);

        Product product11 = productService.findProductById(11L);
        Product product10 = productService.findProductById(10L);
        Product product12 = productService.findProductById(12L);
        Product product9 = productService.findProductById(9L);


        mv.addObject("productfour", product4);
        mv.addObject("productsix", product6);
        mv.addObject("producttwo", product2);
        mv.addObject("productthree", product3);

        mv.addObject("producteleven", product11);
        mv.addObject("productten", product10);
        mv.addObject("productwelve", product12);
        mv.addObject("productnine", product9);
        mv.addObject("productList", productList);

        mv.addObject("activeTabHome", true);
        return mv;
    }

    @GetMapping(Mappings.NEW_USER_FROM_EMAIL)
    public String register(ModelMap modelMap, Locale locale, @RequestParam("token") String token){
        PasswordResetToken passToken = userService.getPasswordTokenForUser(token);
        if (passToken == null) {
            String message = "Invalid token";
            modelMap.addAttribute("message", message);
            return "redirect:/login";
        }
        User user = passToken.getUser();
        String username = user.getUsername();
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Boolean lockedIn = false;
        if(user != null){
            lockedIn = true;
        }
        modelMap.addAttribute("userLoggedIn", lockedIn);
        modelMap.addAttribute("user", user);

        return ViewNames.MY_ACCOUNT_PAGE;
    }



    @PostMapping(Mappings.NEW_USER_FROM_EMAIL)
    public String newUserPost(HttpServletRequest request,
                              @ModelAttribute("email") String userEmail, @ModelAttribute("username") String username,
                              ModelMap modelMap) throws Exception{
        modelMap.addAttribute("email", userEmail);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("classActiveRegister", true);

//        if(userService.findByUsername(username) != null){
//            modelMap.addAttribute("usernameExists", true);
//            return ViewNames.LOGIN_PAGE;
//        }

        if(userService.findByUsername(userEmail) != null){
            modelMap.addAttribute("emailExists", true);
            return ViewNames.LOGIN_PAGE;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructorResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        modelMap.addAttribute("emailSent", "true");

        return ViewNames.LOGIN_PAGE;
    }


    @PostMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("forgot_email") String email, ModelMap model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return Mappings.ACCOUNT_LOGIN;
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        sendmail(request, user, password);

        model.addAttribute("forgetPasswordEmailSent", "true");
        model.addAttribute("classActiveForgot", true);

        return Mappings.ACCOUNT_LOGIN;
    }

    private void sendmail(HttpServletRequest request, User user, String password) {
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructorResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(newEmail);
    }
}
