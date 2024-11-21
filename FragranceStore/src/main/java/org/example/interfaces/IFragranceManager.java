package org.example.interfaces;

import org.example.model.Fragrance;

import java.util.List;

public interface IFragranceManager {
    void addFragrance(Fragrance product);
    void updateFragrance(int productId, Fragrance updatedFragrance);
    void deleteFragrance(int productId);
    List<Fragrance> getAllFragrances();
}
