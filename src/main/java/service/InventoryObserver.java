package service;

import model.product.Product;

public interface InventoryObserver {
    void onStockUpdate(Product product, int newStockLevel);
}
