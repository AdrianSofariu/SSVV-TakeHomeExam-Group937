package com.ssvv.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Stock level is required")
    @Min(value = 0, message = "Stock cannot be negative")
    @Column(nullable = false)
    private Integer stockLevel;

    @NotNull(message = "Minimum threshold is required")
    @Min(value = 0, message = "Threshold cannot be negative")
    @Column(nullable = false)
    private Integer minThreshold;

    @NotNull(message = "Maximum capacity is required")
    @Min(value = 1, message = "Max capacity must be at least 1")
    @Column(nullable = false)
    private Integer maxCapacity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Shipment> shipments;
}