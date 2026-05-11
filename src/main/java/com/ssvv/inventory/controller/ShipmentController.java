package com.ssvv.inventory.controller;

import com.ssvv.inventory.entity.Shipment;
import com.ssvv.inventory.repository.ShipmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentRepository shipmentRepo;

    public ShipmentController(ShipmentRepository shipmentRepo) {
        this.shipmentRepo = shipmentRepo;
    }

    @GetMapping
    public String listShipments(Model model) {
        model.addAttribute("shipments", shipmentRepo.findAll());
        return "shipments";
    }

    @GetMapping("/delete/{id}")
    public String deleteShipment(@PathVariable Long id) {
        shipmentRepo.deleteById(id);
        return "redirect:/shipments";
    }
}