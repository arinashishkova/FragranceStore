package org.example;
import org.example.model.Customer;
import org.example.model.Fragrance;
import org.example.model.Purchase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchaseFileSaver {

    public void savePurchase(Purchase purchase, Customer customer, List<Fragrance> fragrances, int quantity)  throws IOException{
        // Форматирование текущей даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        // Запись информации о покупке в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchases.txt", true))) {
            for (Fragrance fragrance : fragrances) {
                String purchaseInfo = String.format("Дата: %s, Покупатель: %s, Аромат: %s, Количество: %d, Цена: %.2f\n",
                        currentDate, customer.getName(), fragrance.getName(), quantity, fragrance.getPrice() * quantity);
                writer.write(purchaseInfo);
            }
            System.out.println("Информация о покупке сохранена в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении информации о покупке в файл: " + e.getMessage());
        }
    }

}
