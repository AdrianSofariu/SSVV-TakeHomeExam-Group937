package com.ssvv.inventory.service;

import com.ssvv.inventory.entity.Product;
import com.ssvv.inventory.entity.Shipment;
import com.ssvv.inventory.entity.Supplier;
import com.ssvv.inventory.repository.ProductRepository;
import com.ssvv.inventory.repository.ShipmentRepository;
import com.ssvv.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final ProductRepository productRepo;
    private final SupplierRepository supplierRepo;
    private final ShipmentRepository shipmentRepo;

    public InventoryService(ProductRepository productRepo, SupplierRepository supplierRepo, ShipmentRepository shipmentRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
        this.shipmentRepo = shipmentRepo;
    }

    @Transactional
    public Shipment processRestock(Long productId, Long supplierId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cannot order a zero or negative quantity.");
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Supplier supplier = supplierRepo.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        if (product.getStockLevel() + quantity > product.getMaxCapacity()) {
            throw new IllegalStateException("Restock quantity exceeds maximum warehouse capacity for this product.");
        }

        product.setStockLevel(product.getStockLevel() + quantity);
        productRepo.save(product);

        Shipment shipment = new Shipment();
        shipment.setProduct(product);
        shipment.setSupplier(supplier);
        shipment.setQuantity(quantity);
        shipment.setShipmentDate(LocalDate.now());

        return shipmentRepo.save(shipment);
    }

    public List<Product> getLowStockReport() {
        return productRepo.findProductsBelowMinThreshold();
    }
}