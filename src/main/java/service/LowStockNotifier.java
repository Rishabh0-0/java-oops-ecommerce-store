package service;

import model.product.Product;

public class LowStockNotifier implements InventoryObserver{
    private static final int LOW_STOCK_THRESHOLD = 5;

    @Override
    public void onStockUpdate(Product product, int newStockLevel) {
        if (newStockLevel > 0 && newStockLevel <= LOW_STOCK_THRESHOLD) {
            System.out.println("--- LOW STOCK ALERT ---");
            System.out.println("Product: " + product.getName() + " (ID: " + product.getProductId() + ")");
            System.out.println("New stock level is: " + newStockLevel + ". Time to reorder!");
            System.out.println("-------------------------");
        } else if (newStockLevel == 0) {
            System.out.println("--- OUT OF STOCK ALERT ---");
            System.out.println("Product: " + product.getName() + " (ID: " + product.getProductId() + ") is now OUT OF STOCK.");
            System.out.println("----------------------------");
        }
    }
}
