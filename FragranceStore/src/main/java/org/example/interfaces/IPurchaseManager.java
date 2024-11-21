package org.example.interfaces;

import org.example.model.Purchase;

import java.io.IOException;

public interface IPurchaseManager {
    void makePurchase(int customerId, int productId, int quantity) throws IOException;


    void savePurchaseInfo(Purchase purchase);
}
