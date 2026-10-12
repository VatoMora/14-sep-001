package com.bootcamp.descuento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "discounts")
public class DiscountEntity {

    @Id
    private Long productId;
    private Double percentage;
    private String description;

    public DiscountEntity() {}

    public DiscountEntity(Long productId, Double percentage, String description) {
        this.productId = productId;
        this.percentage = percentage;
        this.description = description;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}