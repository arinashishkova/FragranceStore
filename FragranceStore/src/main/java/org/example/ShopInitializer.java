package org.example;

import org.example.interfaces.ICustomerManager;
import org.example.interfaces.IFragranceManager;
import org.example.model.Customer;
import org.example.model.Fragrance;

public class ShopInitializer {
    private final IFragranceManager FragranceManager ;
    private final ICustomerManager customerManager;

    public ShopInitializer(IFragranceManager FragranceManager , ICustomerManager customerManager) {
        this.FragranceManager  = FragranceManager ;
        this.customerManager = customerManager;
    }

    public void initializeFragrances() {
        FragranceManager.addFragrance(new Fragrance(1, "Scandal by Jean Paul Gaultier", 119.99, 5));
        FragranceManager.addFragrance(new Fragrance(2, "Chanel No. 5", 129.99, 3));
        FragranceManager.addFragrance(new Fragrance(3, "Black Orchid by Tom Ford", 179.99, 2));
        FragranceManager.addFragrance(new Fragrance(4, "Dior Sauvage", 99.99, 1));
        FragranceManager.addFragrance(new Fragrance(5, "Flowerbomb by Viktor & Rolf", 159.99, 2));
        System.out.println("Ароматы успешно инициализированы.");
    }


    public void initializeCustomers() {
        customerManager.addCustomer(new Customer(1, "Arina", 1000.00));
        customerManager.addCustomer(new Customer(2, "Dasha", 500.00));
        customerManager.addCustomer(new Customer(3, "Natasha", 750.00));
        customerManager.addCustomer(new Customer(4, "Valera", 1200.00));
        System.out.println("Покупатели успешно инициализированы.");
    }
}