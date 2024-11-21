package org.example;

import java.util.List;

import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.model.Fragrance;

public class InputValidator {

    private final Input input;

    public InputValidator( Input input ) {
        this.input = input;
    }

    public String getValidTextInput(String promptMessage) {
        String user_input = "";
        while (true) {
            System.out.print(promptMessage);
            user_input = input.nextLine().trim();
            if (user_input.isEmpty()) {
                System.out.println("Ошибка: ввод не может быть пустым.");
            }
            else if (user_input.matches("[a-zA-Zа-яА-ЯёЁ\\s]+")) {
                break;
            } else {
                System.out.println("Ошибка! Ввод не должен быть числом. Пожалуйста, введите текст заново.");
            }
        }
        return user_input;
    }

    public double getValidPositivePrice(String promptMessage) {
        double price;
        while (true) {
            price = getValidDoubleInput(promptMessage);
            if (price >= 0) {
                break; // Valid price
            } else {
                System.out.println("Ошибка: цена не может быть отрицательной или нулевой. Введите положительное число.");
            }
        }
        return price;
    }

    public int getValidPositiveQuantity(String promptMessage) {
        int quantity;
        while (true) {
            quantity = getValidIntInput(promptMessage);
            if (quantity > 0) {
                break; // Если количество положительное, выходим из цикла
            } else {
                System.out.println("Ошибка: количество не может быть отрицательным или нулевым. Введите положительное число.");
            }
        }
        return quantity;
    }




    public double getValidDoubleInput(String promptMessage) {
        double user_input;
        while (true) {
            System.out.print(promptMessage);
            try {
                user_input = input.nextDouble();
                input.nextLine(); // Очистка буфера после ввода

                if (user_input > 0) {
                    return user_input; // Если ввод положительный, возвращаем значение
                } else {
                    System.out.println("Ошибка: введите положительное число.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка! Введите корректное число.");
                input.nextLine(); // Очистка некорректного ввода
            }
        }
    }

    public int getValidIntInput(String promptMessage) {
        int user_input;
        while (true) {
            System.out.print(promptMessage);
            try{
                user_input = input.nextInt();
                input.nextLine();
                break;
            }catch (Exception e) {
                System.out.println("Ошибка! Введите корректное целое число.");
                input.nextLine();

            }
                // Очистка некорректного ввода
        }
        return user_input;
    }


    public String getValidUniqueProductName(String promptMessage, List<Fragrance> products) {
        String name;
        boolean exists=false;

        do {
            name = getValidTextInput(promptMessage);

            // Проверка, что имя не является числом
            if (name.matches(".*\\d.*")) {
                System.out.println("Ошибка! Название продукта не должно содержать цифры. Пожалуйста, введите другое название.");
                continue; // Повторно запрашиваем ввод, если в имени есть цифры
            }
            // Проверка уникальности имени
            String finalName = name;
            exists = products.stream()
                    .anyMatch(product -> product.getName().equalsIgnoreCase(finalName));
            if (exists) {
                System.out.println("Ошибка! Продукт с таким названием уже существует. Пожалуйста, введите другое название.");
            }else {
                System.out.println("Название уникально, продолжаем добавление.");
            }



        } while (exists); // Повторяем запрос, пока не введем уникальное имя

        return name;
    }

    public int getValidExistingCustomerId(String promptMessage, List<Customer> customers) {
        int customerId;
        while (true) {
            customerId = getValidIntInput(promptMessage);

            // Проверка, существует ли введенный ID среди существующих покупателей
            int finalCustomerId = customerId;
            boolean exists = customers.stream().anyMatch(c -> c.getId() == finalCustomerId);

            if (exists) {
                break; // Если ID найден, выходим из цикла
            } else {
                System.out.println("Ошибка! Покупатель с таким ID не существует. Попробуйте снова.");
            }
        }
        return customerId;
    }

    public int getValidExistingFragranceId(String promptMessage, List<Fragrance> fragrances) {
        int productId;
        while (true) {
            productId = getValidIntInput(promptMessage);

            // Проверка, существует ли введенный ID среди существующих продуктов
            int finalProductId = productId;
            boolean exists = fragrances.stream().anyMatch(p -> p.getId() == finalProductId);

            if (exists) {
                break; // Если ID найден, выходим из цикла
            } else {
                System.out.println("Ошибка! Продукт с таким ID не существует. Попробуйте снова.");
            }
        }
        return productId;
    }


}
