package org.example;

import org.example.interfaces.ICustomerManager;
import org.example.interfaces.IFragranceManager;
import org.example.interfaces.IPurchaseManager;
import org.example.model.Fragrance;
import org.example.model.Customer;

import java.io.IOException;

public class Shop {
    private final IFragranceManager fragranceManager;
    private final ICustomerManager customerManager;
    private final IPurchaseManager purchaseManager;
    private final FragranceInputHandler FragranceInputHandler;

    public Shop(IFragranceManager fragranceManager, ICustomerManager customerManager,
                IPurchaseManager purchaseManager, FragranceInputHandler FragranceInputHandler) {
        this.fragranceManager = fragranceManager;
        this.customerManager = customerManager;
        this.purchaseManager = purchaseManager;
        this.FragranceInputHandler = FragranceInputHandler;
    }

    public void start() throws IOException {
        System.out.println("Добро пожаловать в магазин парфюмерии!");
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите опцию:");
            System.out.println("1. Добавить аромат");
            System.out.println("2. Обновить аромат");
            System.out.println("3. Удалить аромат");
            System.out.println("4. Показать все ароматы");
            System.out.println("5. Добавить покупателя");
            System.out.println("6. Показать всех покупателей");
            System.out.println("7. Совершить покупку");
            System.out.println("8. Выход");
            System.out.println("9. Удалить покупателя");

            int choice = FragranceInputHandler.getValidChoice();
            switch (choice) {
                case 1 -> addFragrance();
                case 2 -> updateFragrance();
                case 3 -> deleteFragrance();
                case 4 -> showAllFragrances();
                case 5 -> addCustomer();
                case 6 -> showAllCustomers();

                case 7 -> makePurchase();
                case 8 -> running = false;
                case 9 -> deleteCustomer();
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    public void addFragrance() {
        System.out.println("Список ароматов перед добавлением");
        System.out.println("__________________________________");
        showAllFragrances();
        String name = FragranceInputHandler.getUniqueFragranceName(fragranceManager.getAllFragrances());
        double price = FragranceInputHandler.getValidFragrancePrice();
        int quantity = FragranceInputHandler.getValidFragranceQuantity();

        Fragrance fragrance = new Fragrance(fragranceManager.getAllFragrances().size() + 1, name, price, quantity);
        fragranceManager.addFragrance(fragrance);

        System.out.println("Аромат успешно добавлен.");

        System.out.println("Список ароматов после добавления");
        showAllFragrances();
    }

    public void updateFragrance() {
        System.out.println("Список ароматов которые можно обновить");
        showAllFragrances();
        int fragranceId = FragranceInputHandler.getExistingFragranceId(fragranceManager.getAllFragrances());

        String name = FragranceInputHandler.getValidTextInput("Введите новое название аромата:");
        double price = FragranceInputHandler.getValidFragrancePrice();
        int quantity = FragranceInputHandler.getValidFragranceQuantity();

        Fragrance updatedFragrance = new Fragrance(fragranceId, name, price, quantity);
        fragranceManager.updateFragrance(fragranceId, updatedFragrance);
        System.out.println("Аромат успешно обновлен.");
        System.out.println("Список ароматов после обновления");
        showAllFragrances();
    }

    public void deleteFragrance() {
        int fragranceId = FragranceInputHandler.getExistingFragranceId(fragranceManager.getAllFragrances());
        fragranceManager.deleteFragrance(fragranceId);
        System.out.println("Аромат успешно удален.");
        System.out.println("Список ароматов после удаления");
        showAllFragrances();
    }

    public void showAllFragrances() {
        System.out.println("Список ароматов:");
        fragranceManager.getAllFragrances().forEach(fragrance ->
                System.out.println("- ID: " + fragrance.getId() + ", Название: " + fragrance.getName() +
                        ", Цена: " + fragrance.getPrice() + ", Количество: " + fragrance.getQuantity()));
    }

    public void addCustomer() {
        System.out.println("Список покупателей перед добавлением");
        showAllCustomers();
        String name = FragranceInputHandler.getValidTextInput("Введите имя покупателя: ");
        double balance = FragranceInputHandler.getValidCustomerBalance();
        if (balance < 0) {
            System.out.println("Баланс не может быть отрицательным.Введите положительный баланс");
            return;
        }

        Customer customer = new Customer(customerManager.getAllCustomers().size() + 1, name, balance);
        customerManager.addCustomer(customer);
        System.out.println("Покупатель добавлен.");
        System.out.println("Список покупателей после добавления");
        showAllCustomers();
    }

    public void showAllCustomers() {
        System.out.println("Список покупателей:");
        customerManager.getAllCustomers().forEach(customer ->
                System.out.println("- ID: " + customer.getId() + ", Имя: " + customer.getName() +
                        ", Баланс: " + customer.getBalance()));
    }

    public  void makePurchase() throws IOException {
        boolean shopping = true;

        while (shopping) {
            showAllCustomers();
            int customerId = FragranceInputHandler.getExistingCustomerId(customerManager.getAllCustomers());

            showAllFragrances();
            int fragranceId = FragranceInputHandler.getExistingFragranceId(fragranceManager.getAllFragrances());
            int quantity = FragranceInputHandler.getValidFragranceQuantity();

            // Вызов логики покупки в PurchaseManager
            purchaseManager.makePurchase(customerId, fragranceId, quantity);

            // Проверка, хочет ли пользователь продолжить покупки
            String continueChoice = FragranceInputHandler.getContinueShoppingChoice();
            if (continueChoice == null) {
                throw new IllegalStateException("Продолжение покупок не указано.");
            }

            shopping = continueChoice.equalsIgnoreCase("да");
        }
    }

    public void deleteCustomer() throws  IOException{
        System.out.println("Список покупателей перед удалением");
        showAllCustomers();
        int customerId = FragranceInputHandler.getExistingCustomerId(customerManager.getAllCustomers());
        customerManager.deleteCustomer(customerId);
        System.out.println("Покупатель успешно удален");
        System.out.println("Список покупателей после удаления");
        showAllCustomers();





    }
}

