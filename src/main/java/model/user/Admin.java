package model.user;

import model.product.Product;

public class Admin extends User{
    public Admin(String userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    // Placeholders
    public void addNewProduct(Product product) {
        System.out.println("Admin " + getUsername() + " is adding product: " + product.getName());
    }

    public void updateStock(Product product, int newStockLevel) {
        System.out.println("Admin " + getUsername() + " is updating stock for: " + product.getName());
    }
}
