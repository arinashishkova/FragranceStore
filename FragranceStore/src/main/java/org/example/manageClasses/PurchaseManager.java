package org.example.manageClasses;

import org.example.PurchaseFileSaver;
import org.example.interfaces.ICustomerManager;
import org.example.interfaces.IFragranceManager;
import org.example.interfaces.IPurchaseManager;
import org.example.model.Customer;
import org.example.model.Fragrance;
import org.example.model.Purchase;

import java.io.IOException;
import java.util.List;

public class PurchaseManager implements IPurchaseManager {
    private IFragranceManager fragranceManager;
    private ICustomerManager customerManager;
    private PurchaseFileSaver purchaseFileSaver;


    public PurchaseManager(IFragranceManager fragranceManager, ICustomerManager customerManager, PurchaseFileSaver purchaseFileSaver) {
        this.fragranceManager = fragranceManager;
        this.customerManager = customerManager;
        this.purchaseFileSaver = purchaseFileSaver;


    }

    @Override
    public void makePurchase(int customerId, int fragranceId, int quantity) throws IOException {
        Customer customer = customerManager.getAllCustomers().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElse(null);

        Fragrance fragrance = fragranceManager.getAllFragrances().stream()
                .filter(p -> p.getId() == fragranceId)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            System.out.println("Покупатель с ID " + customerId + " не найден.");
            return;
        }

        if (fragrance == null) {
            System.out.println("Аромат с ID " + fragranceId + " не найден.");
            return;
        }

        if (!fragrance.isAvailable(quantity)) {
            System.out.println("Недостаточно аромата на складе. Доступно: " + fragrance.getQuantity());
            return;
        }

        double totalPrice = fragrance.getPrice() * quantity;




        if (!customer.hasSufficientBalance(totalPrice)) {
            System.out.println("У покупателя недостаточно средств. Необходимо: " + totalPrice + ", Доступно: " + customer.getBalance());
            return;
        }

        // Если все проверки пройдены
        customer.deductBalance(totalPrice);
        fragrance.reduceQuantity(quantity);

        Purchase purchase = new Purchase(customerId, List.of(fragrance), totalPrice);
        purchaseFileSaver.savePurchase(purchase, customer, List.of(fragrance), quantity);

        System.out.println("Покупка успешно совершена!");
        System.out.println("Баланс покупателя после покупки: " + customer.getBalance());
        System.out.println("Купленный аромат: " + fragrance.getName() + ", Количество: " + quantity);
    }


    @Override
    public void savePurchaseInfo(Purchase purchase) {

    }


}