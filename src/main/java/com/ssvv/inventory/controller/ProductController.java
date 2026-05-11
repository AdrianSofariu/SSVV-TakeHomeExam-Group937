package com.ssvv.inventory.controller;

import com.ssvv.inventory.entity.Product;
import com.ssvv.inventory.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepo.findAll());
        if (!model.containsAttribute("newProduct")) {
            model.addAttribute("newProduct", new Product());
        }
        return "products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("newProduct", productRepo.findById(id).orElse(new Product()));
        return "products";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("newProduct") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("products", productRepo.findAll());
            return "products";
        }

        try {
            productRepo.save(product);
        } catch (Exception e) {
            result.rejectValue("name", "error.product", "Product name already exists or database error.");
            model.addAttribute("products", productRepo.findAll());
            return "products";
        }

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/products";
    }
}