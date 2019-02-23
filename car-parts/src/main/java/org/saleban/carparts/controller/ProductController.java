package org.saleban.carparts.controller;

import org.saleban.carparts.domain.Product;
import org.saleban.carparts.service.ProductService;
import org.saleban.carparts.util.Mappings;
import org.saleban.carparts.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    /*
     * All Products - GET
     */
    @GetMapping(Mappings.PRODUCTS)
    public ModelAndView products(){
        ModelAndView mv = new ModelAndView(ViewNames.PRODUCTS);

        List<Product> productList = productService.findAll();

        mv.addObject("activeTabProducts", true);
        mv.addObject("productList", productList);
        return mv;
    }
}
