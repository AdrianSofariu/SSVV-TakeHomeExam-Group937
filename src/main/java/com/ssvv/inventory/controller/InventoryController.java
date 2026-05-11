package com.ssvv.inventory.controller;

import com.ssvv.inventory.service.InventoryService;
import com.ssvv.inventory.repository.ProductRepository;
import com.ssvv.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductRepository productRepo;
    private final SupplierRepository supplierRepo;

    public InventoryController(InventoryService inventoryService, ProductRepository productRepo, SupplierRepository supplierRepo) {
        this.inventoryService = inventoryService;
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/restock")
    public String showRestockPage(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("suppliers", supplierRepo.findAll());
        return "restock";
    }

    @PostMapping("/restock")
    public String processRestock(@RequestParam Long productId,
                                 @RequestParam Long supplierId,
                                 @RequestParam int quantity,
                                 Model model) {
        try {
            inventoryService.processRestock(productId, supplierId, quantity);
            model.addAttribute("successMessage", "Restock successful!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("suppliers", supplierRepo.findAll());
        return "restock";
    }

    @GetMapping("/report")
    public String showReport(Model model) {
        model.addAttribute("lowStockProducts", inventoryService.getLowStockReport());
        return "report";
    }
}