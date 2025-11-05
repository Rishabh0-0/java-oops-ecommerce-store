package service;

import model.product.PhysicalProduct;
import model.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
    private static final InventoryService INSTANCE = new InventoryService();
    private final Map<String, Integer> stockLevels;
    private final List<InventoryObserver> observers;

    private InventoryService() {
        this.stockLevels = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public static InventoryService getInstance() {
        return INSTANCE;
    }

    public void addObserver(InventoryObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(InventoryObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers(Product product, int newStockLevel) {
        for (InventoryObserver observer: this.observers) {
            observer.onStockUpdate(product, newStockLevel);
        }
    }


    public void addProduct(PhysicalProduct product) {
        this.stockLevels.putIfAbsent(product.getProductId(), product.getStockLevel());
        notifyObservers(product, product.getStockLevel());
    }

    public int getStock(Product product) {
        return this.stockLevels.getOrDefault(product.getProductId(), 0);
    }

    public boolean isInStock(Product product, int quantityNeeded) {
        return getStock(product) >= quantityNeeded;
    }

    public boolean reduceStock(Product product, int quantityToReduce) {
        if (isInStock(product, quantityToReduce))  {
            int currentStock = getStock(product);
            int newStock = currentStock - quantityToReduce;

            this.stockLevels.put(product.getProductId(), currentStock - quantityToReduce);
            System.out.println("Inventory: Reduced stock for " + product.getName() + " by " + quantityToReduce);

            notifyObservers(product, newStock);
            return true;
        } else {
            System.out.println("Inventory: Not enough stock for " + product.getName());
            return false;
        }
    }
}
