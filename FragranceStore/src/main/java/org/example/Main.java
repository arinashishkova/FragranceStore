package org.example;

import org.example.interfaces.ICustomerManager;
import org.example.interfaces.IFragranceManager;
import org.example.manageClasses.CustomerManager;
import org.example.manageClasses.FragranceManager;

import java.io.IOException;
import org.example.input.ConsoleInput;
import org.example.interfaces.ICustomerManager;
import org.example.interfaces.IFragranceManager;
import org.example.interfaces.IPurchaseManager;
import org.example.manageClasses.PurchaseManager;

public class Main {
    public static void main(String[] args) {
        // Создаем менеджеры
        IFragranceManager fragranceManager = new FragranceManager();
        ICustomerManager customerManager = new CustomerManager();

        // Создаем объект для сохранения информации о покупках
        PurchaseFileSaver purchaseFileSaver = new PurchaseFileSaver();

        // Создаем менеджер покупок, передав все необходимые параметры
        IPurchaseManager purchaseManager = new PurchaseManager(fragranceManager, customerManager, purchaseFileSaver);

        // Создаем InputHandler
        FragranceInputHandler inputHandler = new FragranceInputHandler(new ConsoleInput());

        // Инициализируем данные через ShopInitializer
        ShopInitializer initializer = new ShopInitializer(fragranceManager, customerManager);
        initializer.initializeFragrances();
        initializer.initializeCustomers();

        // Создаем и запускаем магазин
        Shop shop = new Shop(fragranceManager, customerManager, purchaseManager, inputHandler);
        try {
            shop.start();
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
