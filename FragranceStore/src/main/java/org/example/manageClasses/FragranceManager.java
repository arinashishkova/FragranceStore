package org.example.manageClasses;

import org.example.interfaces.IFragranceManager;
import org.example.model.Fragrance;

import java.util.ArrayList;
import java.util.List;

public class FragranceManager implements IFragranceManager {

    private final List<Fragrance> products = new ArrayList<>();

    @Override
    public void addFragrance(Fragrance product) {
        products.add(product);


    }
    @Override
    public void updateFragrance(int productId, Fragrance updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                products.set(i, updatedProduct);
                break;
            }
        }
    }

    @Override
    public void deleteFragrance(int productId) {
        products.removeIf(product -> product.getId()==productId);

    }

    @Override
    public List<Fragrance> getAllFragrances() {
        return products;
    }
}
