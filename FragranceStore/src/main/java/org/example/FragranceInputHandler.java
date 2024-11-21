package org.example;

import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.model.Fragrance;

import java.util.List;

public class FragranceInputHandler {
    private final InputValidator inputValidator;

    public FragranceInputHandler(Input input) {
        this.inputValidator = new InputValidator(input);
    }

    public String getUniqueFragranceName(List<Fragrance> fragrances) {
        return inputValidator.getValidUniqueProductName("Введите название аромата: ", fragrances);
    }

    public double getValidFragrancePrice() {
        return inputValidator.getValidDoubleInput("Введите цену аромата: ");
    }

    public int getValidFragranceQuantity() {
        return inputValidator.getValidPositiveQuantity("Введите количество флаконов аромата: ");
    }

    public double getValidCustomerBalance(){
        return inputValidator.getValidDoubleInput("Введите баланс покупателя: ");
    }

    public int getExistingFragranceId(List<Fragrance> fragrances) {
        return inputValidator.getValidExistingFragranceId("Введите ID аромата: ", fragrances);
    }

    public int getExistingCustomerId(List<Customer> customers) {
        return inputValidator.getValidExistingCustomerId("Введите ID покупателя: ", customers);
    }

    public String getContinueShoppingChoice() {
        return inputValidator.getValidTextInput("Хотите продолжить покупки? (да/нет): ").toLowerCase();
    }

    public int getValidChoice() {
        return inputValidator.getValidIntInput("Введите ваш выбор: ");
    }

    public String getValidTextInput(String message) {
        return inputValidator.getValidTextInput(message);
    }
}
