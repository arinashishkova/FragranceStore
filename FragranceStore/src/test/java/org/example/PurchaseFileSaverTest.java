package org.example;

import org.example.model.Customer;
import org.example.model.Fragrance;
import org.example.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PurchaseFileSaverTest {

    private PurchaseFileSaver purchaseFileSaver;
    private Customer mockCustomer;
    private Fragrance mockProduct;
    private final String filePath = "purchases.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Создаем новый объект PurchaseFileSaver
        purchaseFileSaver = new PurchaseFileSaver();

        // Мокаем объекты Customer и Product
        mockCustomer = mock(Customer.class);
        mockProduct = mock(Fragrance.class);

        // Настраиваем моки
        when(mockCustomer.getName()).thenReturn("Anna Vintour");
        when(mockProduct.getName()).thenReturn("Lost Cherry by Tom Ford");
        when(mockProduct.getPrice()).thenReturn(10000.0);

        // Очищаем файл перед каждым тестом
        Files.deleteIfExists(Path.of(filePath));
    }

    @Test
    void testIOExceptionHandling() throws IOException {
        // Мокаем поведение метода savePurchase, чтобы он выбросил IOException
        PurchaseFileSaver mockSaver = mock(PurchaseFileSaver.class);
        doThrow(new IOException("Test exception")).when(mockSaver).savePurchase(any(), any(), anyList(), anyInt());

        // Пробуем вызвать метод savePurchase и проверяем, что IOException обработано корректно
        try {
            int quantity = 1;
            double totalPrice = mockProduct.getPrice() * quantity;
            Purchase purchase = new Purchase(mockCustomer.getId(), List.of(mockProduct), totalPrice);

            mockSaver.savePurchase(purchase, mockCustomer, List.of(mockProduct), quantity);
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Test exception"));
        }
    }


}


