package com.ssvv.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Supplier name cannot be blank")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email format")
    @Column(nullable = false)
    private String contactEmail;

    @NotNull(message = "Lead time is required")
    @Min(value = 0, message = "Lead time cannot be negative")
    @Column(nullable = false)
    private Integer leadTimeDays;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Shipment> shipments;
}