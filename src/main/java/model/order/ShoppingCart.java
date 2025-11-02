package model.order;

import model.product.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        this.items.merge(product, quantity, Integer::sum);
    }

    public void removeItem(Product product) {
        this.items.remove(product);
    }

    public void updateQuantity(Product product, int newQuantity) {
        if (newQuantity <= 0) {
            removeItem(product);
        } else {
            this.items.put(product, newQuantity);
        }
    }

    public double calculateTotal() {
        double totalValue = 0.00;
        for (Map.Entry<Product, Integer> entry: this.items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            totalValue += product.getPrice() * quantity;
        }

        return totalValue;
    }

    public void clearCart() {
        this.items.clear();
    }

    public Map<Product, Integer> getItems() {
        return Map.copyOf(this.items);
    }
}
