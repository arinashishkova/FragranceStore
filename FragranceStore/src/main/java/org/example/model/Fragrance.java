package org.example.model;

import java.util.Objects;

public class Fragrance {
    private int id;
    private String name;
    private double price;
    private int quantity; // Новое поле для количества

    public Fragrance(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Проверка, есть ли аромат в наличии
    public boolean isAvailable(int requestedQuantity) {
        return quantity >= requestedQuantity;
    }

    // Метод для уменьшения количества после покупки
    public void reduceQuantity(int amount) {
        if (isAvailable(amount)) {
            this.quantity -= amount;
        }
    }

    // Getters и Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fragrance fragrance = (Fragrance) o;
        return id == fragrance.id &&
                Double.compare(fragrance.price, price) == 0 &&
                quantity == fragrance.quantity &&
                Objects.equals(name, fragrance.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity);
    }
}
