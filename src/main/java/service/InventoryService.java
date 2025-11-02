package service;

import model.product.PhysicalProduct;
import model.product.Product;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private static final InventoryService INSTANCE = new InventoryService();
    private final Map<String, Integer> stockLevels;

    private InventoryService() {
        this.stockLevels = new HashMap<>();
    }

    public void addProduct(PhysicalProduct product) {
        this.stockLevels.putIfAbsent(product.getProductId(), product.getStockLevel());
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
            this.stockLevels.put(product.getProductId(), currentStock - quantityToReduce);
            System.out.println("Inventory: Reduced stock for " + product.getName() + " by " + quantityToReduce);
            return true;
        } else {
            System.out.println("Inventory: Not enough stock for " + product.getName());
            return false;
        }
    }
}
