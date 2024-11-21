package org.example;

import org.example.interfaces.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.model.Customer;
import org.example.model.Fragrance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class InputValidatorTest {
    @Mock
    private Input input;
    private List<Customer> customers;
    private List<Fragrance> products;
    private InputValidator inputValidator;
    private FragranceInputHandler inputHandler;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customers = Arrays.asList(
                new Customer(1, "Arina", 1000.0),
                new Customer(2, "Dasha", 500.0)
        );
        products = Arrays.asList(
                new Fragrance(1, "Bleu de Chanel ", 150, 5),
                new Fragrance(2, "The Scent by Hugo Boss", 110, 3)
        );

        inputValidator = new InputValidator(input);
    }

    @Test
    void getValidDoubleInput_InvalidDouble_ShowsErrorUntilValid() {
        // Несколько вводов: неправильный ввод и потом правильный
        when(input.nextLine()).thenReturn("abc");
        when(input.nextDouble()).thenThrow(new java.util.InputMismatchException()).thenReturn(12.5);
        double result = inputValidator.getValidDoubleInput("Введите число:");
        assertEquals(12.5, result, 0.01);
    }
//
    @Test
    void getValidIntInput_ValidInt_ReturnsInt() {
        // Мокаем ввод целого числа
        when(input.nextInt()).thenReturn(42);
        int result = inputValidator.getValidIntInput("Введите целое число:");
        assertEquals(42, result);
    }

    @Test
    void getValidIntInput_InvalidInt_ShowsErrorUntilValid() {
        when(input.nextLine()).thenReturn("abc"); // Сначала текст, который вызовет ошибку
        when(input.nextInt()).thenThrow(new java.util.InputMismatchException()) // Имитируем ошибку при вводе нечислового значения
                .thenReturn(42); // Затем корректное целое число
        when(input.nextLine()).thenReturn(""); // Очистка после nextInt

        int result = inputValidator.getValidIntInput("Введите целое число:");
        assertEquals(42, result);
    }

    @Test
    void getValidUniqueProductName_ValidUniqueName_ReturnsName() {
        // Проверка на уникальность
        when(input.nextLine()).thenReturn("Orange Blossom by Hugo Boss");
        String result = inputValidator.getValidUniqueProductName("Введите название аромата:", products);
        assertEquals("Orange Blossom by Hugo Boss", result);
    }

    @Test
    void getValidUniqueProductName_ExistingName_ShowsErrorUntilUnique() {
        // Несколько вводов: сначала существующее имя, потом уникальное
        when(input.nextLine()).thenReturn("Bleu de Chanel").thenReturn("The Scent").thenReturn("Scandal");
        String result = inputValidator.getValidUniqueProductName("Введите название аромата:", products);
        assertEquals("Scandal", result);
    }

    @Test
    void getValidExistingCustomerId_InvalidId_ShowsErrorUntilValid() {
        // Несколько вводов: сначала неверный ID, потом верный
        when(input.nextInt()).thenReturn(10).thenReturn(2);
        int result = inputValidator.getValidExistingCustomerId("Введите ID покупателя:", customers);
        assertEquals(2, result);
    }

    @Test
    void getValidExistingProductId_ValidId_ReturnsId() {
        // Мокаем ввод ID аромата
        when(input.nextInt()).thenReturn(2);
        int result = inputValidator.getValidExistingProductId("Введите ID аромата:", products);
        assertEquals(2, result);
    }

    @Test
    void getValidExistingProductId_InvalidId_ShowsErrorUntilValid() {
        // Симуляция ввода: сначала неверный ID, затем корректный ID
        when(input.nextInt()).thenReturn(10).thenReturn(1);
        when(input.nextLine()).thenReturn(""); // Сброс после каждого nextInt()

        int result = inputValidator.getValidExistingProductId("Введите ID аромата:", products);
        assertEquals(1, result);
    }

}