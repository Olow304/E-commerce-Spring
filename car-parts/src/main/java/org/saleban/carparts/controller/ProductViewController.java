package org.saleban.carparts.controller;

import org.saleban.carparts.domain.Product;
import org.saleban.carparts.domain.User;
import org.saleban.carparts.service.ProductService;
import org.saleban.carparts.service.UserService;
import org.saleban.carparts.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductViewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @RequestMapping("/buy")
    public String viewProduct(@PathParam("id") Long id, Model modelMap, Principal principal){
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            modelMap.addAttribute("user", user);
        }

        productService.findOne(id).ifPresent(prod -> modelMap.addAttribute("product", prod));

        List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8);
        modelMap.addAttribute("qtyList", qtyList);
        modelMap.addAttribute("qty", 1);

        return "single";
    }

    @PostMapping("/getPriceByEmail")
    public String sendPriceThroughEmail(
            @ModelAttribute("name") String name, @ModelAttribute("email") String email,
            @ModelAttribute("subject") String subject, @ModelAttribute("msg") String msg,
            @ModelAttribute("price") String price, @ModelAttribute("qty") String qty, Model model
    ){
        SimpleMailMessage simpleMailMessage = mailConstructor.sendPriceByEmail(name, price, qty, email, subject, msg);

        mailSender.send(simpleMailMessage);
        model.addAttribute("emailsend", true);
        return "products";
    }

}
