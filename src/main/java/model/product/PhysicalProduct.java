package model.product;

public abstract class PhysicalProduct extends Product{
    private double weight;
    private int stockLevel;

    public PhysicalProduct(String productId, String name, String description, double price, Category category, double weight, int stockLevel) {
        super(productId, name, description, price, category);
        this.weight = weight;
        this.stockLevel = stockLevel;
    }

    public double getWeight() {
        return weight;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = Math.max(0, stockLevel);
    }
}
