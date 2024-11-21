package org.example;

import org.example.interfaces.ICustomerManager;
import org.example.interfaces.IFragranceManager;
import org.example.interfaces.IPurchaseManager;
import org.example.model.Customer;
import org.example.model.Fragrance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopTest {

    @Mock
    private IFragranceManager fragranceManager;

    @Mock
    private ICustomerManager customerManager;

    @Mock
    private IPurchaseManager purchaseManager;

    @Mock
    private FragranceInputHandler inputHandler;

    @Mock
    private PurchaseFileSaver purchaseFileSaver;

    @InjectMocks
    private Shop shop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFragrance() {
        // Setup
        String productName = "Citrus Breeze by Amalfi";
        double productPrice = 75.5;
        int productQuantity = 4;
        Fragrance product = new Fragrance(1, productName, productPrice, productQuantity);

        when(fragranceManager.getAllFragrances()).thenReturn(Collections.emptyList());
        when(inputHandler.getUniqueFragranceName(anyList())).thenReturn(productName);
        when(inputHandler.getValidFragrancePrice()).thenReturn(productPrice);
        when(inputHandler.getValidFragranceQuantity()).thenReturn(productQuantity);

        // Act
        shop.addFragrance();

        // Verify
        verify(fragranceManager, times(1)).addFragrance(argThat(p ->
                p.getName().equals(productName) &&
                        p.getPrice() == productPrice &&
                        p.getQuantity() == productQuantity
        ));
    }

    @Test
    void testUpdateFragrance() {
        // Setup
        Fragrance existingFragrance = new Fragrance(1, "Apple Blossom by Helena", 2.0, 20);
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.singletonList(existingFragrance));

        when(inputHandler.getExistingFragranceId(anyList())).thenReturn(1);
        when(inputHandler.getValidTextInput(anyString())).thenReturn("Lavender");
        when(inputHandler.getValidFragrancePrice()).thenReturn(3.0);
        when(inputHandler.getValidFragranceQuantity()).thenReturn(30);

        // Act
        shop.updateFragrance();

        // Verify
        verify(fragranceManager, times(1)).updateFragrance(eq(1), argThat(p ->
                p.getName().equals("Lavender") &&
                        p.getPrice() == 3.0 &&
                        p.getQuantity() == 30
        ));
    }

    @Test
    void testDeleteFragrance() {
        // Setup
        Fragrance product = new Fragrance(1, "Test Fragrance", 100.0, 5);
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.singletonList(product));
        when(inputHandler.getExistingFragranceId(anyList())).thenReturn(1);

        // Act
        shop.deleteFragrance();

        // Verify
        verify(fragranceManager, times(1)).deleteFragrance(1);
    }

    @Test
    void testAddCustomer() {
        // Setup
        String customerName = "John";
        double customerBalance = 1000.0;

        when(customerManager.getAllCustomers()).thenReturn(Collections.emptyList());
        when(inputHandler.getValidTextInput(anyString())).thenReturn(customerName);
        when(inputHandler.getValidCustomerBalance()).thenReturn(customerBalance);

        // Act
        shop.addCustomer();

        // Verify
        verify(customerManager, times(1)).addCustomer(argThat(c ->
                c.getName().equals(customerName) &&
                        c.getBalance().compareTo(BigDecimal.valueOf(customerBalance)) == 0
        ));
    }

    @Test
    void testDeleteCustomer() throws IOException {
        // Setup
        Customer customer = new Customer(1, "Arina", 500.0);
        when(customerManager.getAllCustomers()).thenReturn(Collections.singletonList(customer));
        when(inputHandler.getExistingCustomerId(anyList())).thenReturn(1);

        // Act
        shop.deleteCustomer();

        // Verify
        verify(customerManager, times(1)).deleteCustomer(1);
    }

    @Test
    void testShowAllFragrances() {
        // Setup
        Fragrance product = new Fragrance(1, "Apple Blossom by Helena", 2.0, 20);
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.singletonList(product));

        // Act
        shop.showAllFragrances();

        // Verify
        verify(fragranceManager, times(1)).getAllFragrances();
    }

    @Test
    void testShowAllCustomers() {
        // Setup
        Customer customer = new Customer(1, "Arina", 500.0);
        when(customerManager.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        // Act
        shop.showAllCustomers();

        // Verify
        verify(customerManager, times(1)).getAllCustomers();
    }

    @Test
    void testMakePurchase() throws IOException {
        // Setup
        Customer customer = new Customer(1, "Arina", 500.0);
        Fragrance product = new Fragrance(1, "Apple Blossom by Helena", 2.0, 20);

        when(customerManager.getAllCustomers()).thenReturn(Collections.singletonList(customer));
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.singletonList(product));
        when(inputHandler.getExistingCustomerId(anyList())).thenReturn(1);
        when(inputHandler.getExistingFragranceId(anyList())).thenReturn(1);
        when(inputHandler.getValidFragranceQuantity()).thenReturn(5);
        when(inputHandler.getContinueShoppingChoice()).thenReturn("no"); // Завершаем цикл

        // Act
        shop.makePurchase();

        // Verify
        verify(purchaseManager, times(1)).makePurchase(1, 1, 5);
        verify(inputHandler, times(1)).getContinueShoppingChoice(); // Проверяем вызов для завершения цикла
    }

    @Test
    void testUpdateNonExistentFragrance() {
        // Setup
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.emptyList());
        when(inputHandler.getExistingFragranceId(anyList())).thenThrow(new IllegalArgumentException("Invalid product ID"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> shop.updateFragrance());

        // Verify
        verify(fragranceManager, never()).updateFragrance(anyInt(), any());
    }

    @Test
    void testDeleteNonExistentCustomer() throws IOException {
        // Setup
        when(customerManager.getAllCustomers()).thenReturn(Collections.emptyList());
        when(inputHandler.getExistingCustomerId(anyList())).thenThrow(new IllegalArgumentException("Invalid customer ID"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> shop.deleteCustomer());

        // Verify
        verify(customerManager, never()).deleteCustomer(anyInt());
    }

    @Test
    void testShowAllFragrancesEmptyList() {
        // Setup
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.emptyList());

        // Act
        shop.showAllFragrances();

        // Verify
        verify(fragranceManager, times(1)).getAllFragrances();
        // Дополнительно можно проверить вывод (если используется System.out)
    }

    @Test
    void testMultiplePurchases() throws IOException {
        // Setup
        Customer customer = new Customer(1, "Arina", 100.0);
        Fragrance product = new Fragrance(1, "Apple Blossom by Helena", 2.0, 20);

        when(customerManager.getAllCustomers()).thenReturn(Collections.singletonList(customer));
        when(fragranceManager.getAllFragrances()).thenReturn(Collections.singletonList(product));
        when(inputHandler.getExistingCustomerId(anyList())).thenReturn(1);
        when(inputHandler.getExistingFragranceId(anyList())).thenReturn(1);
        when(inputHandler.getValidFragranceQuantity()).thenReturn(5);
        when(inputHandler.getContinueShoppingChoice()).thenReturn("yes", "no"); // Две покупки

        // Act
        shop.makePurchase();

        // Verify
        verify(purchaseManager, times(2)).makePurchase(1, 1, 5); // Две покупки по 5 штук
        verify(inputHandler, times(2)).getContinueShoppingChoice();
    }

    @Test
    void testAddCustomerNegativeBalance() {
        // Setup
        when(customerManager.getAllCustomers()).thenReturn(Collections.emptyList());
        when(inputHandler.getValidTextInput(anyString())).thenReturn("John");
        when(inputHandler.getValidCustomerBalance()).thenReturn(-100.0); // Negative balance

        // Act
        shop.addCustomer();

        // Verify that addCustomer is never called due to negative balance
        verify(customerManager, never()).addCustomer(any());
    }
}
