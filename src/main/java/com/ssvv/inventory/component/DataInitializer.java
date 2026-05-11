package com.ssvv.inventory.component;

import com.ssvv.inventory.entity.Product;
import com.ssvv.inventory.entity.Supplier;
import com.ssvv.inventory.repository.ProductRepository;
import com.ssvv.inventory.repository.SupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepo;
    private final SupplierRepository supplierRepo;

    public DataInitializer(ProductRepository productRepo, SupplierRepository supplierRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepo.count() == 0 && supplierRepo.count() == 0) {

            Supplier s1 = new Supplier();
            s1.setName("TechCorp Logistics");
            s1.setContactEmail("sales@techcorp.com");
            s1.setLeadTimeDays(5);
            supplierRepo.save(s1);

            Supplier s2 = new Supplier();
            s2.setName("Global Supplies Inc.");
            s2.setContactEmail("orders@globalsupplies.com");
            s2.setLeadTimeDays(2);
            supplierRepo.save(s2);

            Product p1 = new Product();
            p1.setName("Laptop Monitors");
            p1.setStockLevel(50);
            p1.setMinThreshold(20);
            p1.setMaxCapacity(200);
            productRepo.save(p1);

            Product p2 = new Product();
            p2.setName("Wireless Keyboards");
            p2.setStockLevel(5);
            p2.setMinThreshold(15);
            p2.setMaxCapacity(100);
            productRepo.save(p2);

            Product p3 = new Product();
            p3.setName("USB-C Cables");
            p3.setStockLevel(950);
            p3.setMinThreshold(100);
            p3.setMaxCapacity(1000);
            productRepo.save(p3);
        }
    }
}