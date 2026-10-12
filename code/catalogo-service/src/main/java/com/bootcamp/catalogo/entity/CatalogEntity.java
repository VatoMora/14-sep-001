package com.bootcamp.catalogo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalog")
public class CatalogEntity {

    @Id
    private Long productId;
    private String name;
    private Double basePrice;
    private Integer stock;

    public CatalogEntity() {}

    public CatalogEntity(Long productId, String name, Double basePrice, Integer stock) {
        this.productId = productId;
        this.name = name;
        this.basePrice = basePrice;
        this.stock = stock;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}