package model.order;

import model.product.Product;

public final class OrderItem {
    private final String productId;
    private final String productName;
    private final int quantity;
    private final double pricePaid;

    public OrderItem(Product product, int quantity) {
        this.productId = product.getProductId();
        this.productName = product.getName();
        this.quantity = quantity;
        this.pricePaid = product.getPrice();
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public double getTotalPrice() {
        return this.pricePaid * this.quantity;
    }
}
