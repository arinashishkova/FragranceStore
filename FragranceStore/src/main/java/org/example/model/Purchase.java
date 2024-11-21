package org.example.model;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {
    private int customerId;
    private List<Fragrance> fragrances;
    private double totalAmount;
    private LocalDateTime purchaseDate;

    public Purchase(int customerId, List<Fragrance> fragrances, double totalAmount) {
        this.customerId = customerId;
        this.fragrances = fragrances;
        this.totalAmount = totalAmount;
        this.purchaseDate = LocalDateTime.now();
    }



    public int getCustomerId() {
        return customerId;
    }



    public List<Fragrance> getFragrances() {
        return fragrances;
    }

    public void setFragrances(List<Fragrance> fragrances) {
        this.fragrances = fragrances;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    // Getters and Setters
}
