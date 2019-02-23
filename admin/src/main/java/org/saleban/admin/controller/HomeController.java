package org.saleban.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.saleban.admin.domain.Product;
import org.saleban.admin.domain.User;
import org.saleban.admin.service.ProductService;
import org.saleban.admin.service.UserService;
import org.saleban.admin.util.Mappings;
import org.saleban.admin.util.ViewNames;
import org.saleban.admin.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(Mappings.HOME)
    public String home(){
        return "redirect:/home";
    }

    // once the user logged in
    @RequestMapping(Mappings.INDEX)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView(ViewNames.INDEX);
        return mv;
    }

    @RequestMapping(value = "/images/logo.png")
    public String redirect() {
        return "redirect:/home";
    }

    // login
    @RequestMapping(Mappings.LOGIN)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView(ViewNames.LOGIN);
        return mv;
    }

    // once user logout out
    @RequestMapping(Mappings.LOGOUT)
    public String logout(){
        return "redirect:/login";
    }

    // ============== ADD PRODUCTS ===============
    @GetMapping(Mappings.ADD_PRODUCT)
    public ModelAndView add_product(){
        Product product = new Product();
        ModelAndView mv = new ModelAndView(ViewNames.ADD_PRODUCT);
        mv.addObject("product", product);
        return mv;
    }

    @PostMapping("product/add")
    public String addProduct(@ModelAttribute("product") Product product, HttpServletRequest request, ModelMap modelMap){
        if(product.getDescription().length() > 5000){
            log.info("ADDING TOO LONG PRODUCT DESCRIPTION");
        }
        productService.save(product);
        MultipartFile productImage = product.getProductImage();
        try {
            byte[] bytes = productImage.getBytes();
            String imageExt = product.getId() + ".jpg";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/product_images/"+imageExt)));
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/view-products";
    }

    @GetMapping("/view-products")
    public String productLists(ModelMap modelMap){
        List<Product> productList = productService.findAll();
        modelMap.addAttribute("productList", productList);
        return "all_products";
    }

    @GetMapping("/view-users")
    public String userLists(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "all_users";
    }

    @RequestMapping("/deleteUser")
    public String userDelete(@RequestParam("id") Long id){
        userService.delete(id);
        return "redirect:/view-users";
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("id") Long id, Model model){
        userService.findOne(id).ifPresent(users -> model.addAttribute("users", users));
        return "userInfo";
    }

    @GetMapping("/userUpdate")
    public String updateUser(@RequestParam("id") Long id, Model model){
        userService.findOne(id).ifPresent(user -> model.addAttribute("user", user));
        return "user_update";
    }

    @PostMapping("/updateUser")
    public String updateUserPost(@ModelAttribute("user") User user, HttpServletRequest request){
        user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/view-users";
    }

    @GetMapping("/add-user")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "add_user";
    }

    @PostMapping("/add-new-user")
    public String addNewUser(@ModelAttribute("user") User user){
        user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/view-users";
    }

    @GetMapping("/productInfo")
    public String productInfo(@RequestParam("id") Long id, Model model){
        productService.findOne(id).ifPresent(product -> model.addAttribute("product", product));
        return "productInfo";
    }

    @RequestMapping("/updateProduct")
    public String upateProduct(@RequestParam("id") Long id, Model model){
        productService.findOne(id).ifPresent(product -> model.addAttribute("product", product));
        return "productUpdate";
    }

    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") Long id){

        String productImage = id.toString() + ".jpg";
        try{
            Files.delete(Paths.get("src/main/resources/static/product_images/" + productImage));
            productService.delete(id);
        } catch (IOException e) {
            log.info("NO IMAGE FOUND");
            e.printStackTrace();
        }
        return "redirect:/view-products";
    }

    @PostMapping("/updateProduct")
    public String updateProductPost(@ModelAttribute("product") Product product, HttpServletRequest request){
        productService.save(product);
        MultipartFile productImage = product.getProductImage();
        if(!productImage.isEmpty()){
            try {
                byte[] bytes = productImage.getBytes();
                String imageExt = product.getId() + ".jpg";
                boolean check = new File("src/main/resources/static/product_images/"+imageExt).exists();
                if(check){
                    try{
                        Files.delete(Paths.get("src/main/resources/static/product_images/"+imageExt));
                    }catch(NoSuchFileException e) {
                        log.info("No Such File Exists.");
                    }
                }else{
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(new File("src/main/resources/static/product_images/"+imageExt)));
                    stream.write(bytes);
                    log.info("FILE CREATING NOW");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/productInfo?id="+product.getId();
    }



}
