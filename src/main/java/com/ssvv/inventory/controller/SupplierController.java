package com.ssvv.inventory.controller;

import com.ssvv.inventory.entity.Supplier;
import com.ssvv.inventory.repository.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierRepository supplierRepo;

    public SupplierController(SupplierRepository supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierRepo.findAll());
        if (!model.containsAttribute("newSupplier")) {
            model.addAttribute("newSupplier", new Supplier());
        }
        return "suppliers";
    }

    @GetMapping("/edit/{id}")
    public String editSupplier(@PathVariable Long id, Model model) {
        model.addAttribute("suppliers", supplierRepo.findAll());
        model.addAttribute("newSupplier", supplierRepo.findById(id).orElse(new Supplier()));
        return "suppliers";
    }

    @PostMapping("/save")
    public String saveSupplier(@Valid @ModelAttribute("newSupplier") Supplier supplier, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierRepo.findAll());
            return "suppliers";
        }

        try {
            supplierRepo.save(supplier);
        } catch (Exception e) {
            result.rejectValue("name", "error.supplier", "Supplier name already exists or database error.");
            model.addAttribute("suppliers", supplierRepo.findAll());
            return "suppliers";
        }

        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierRepo.deleteById(id);
        return "redirect:/suppliers";
    }
}