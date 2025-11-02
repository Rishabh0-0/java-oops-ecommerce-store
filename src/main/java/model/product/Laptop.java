package model.product;

public class Laptop extends PhysicalProduct{
    private final String brand;
    private final String screenSize;
    private final int ram;

    public Laptop(String productId, String name, String description, double price, Category category, double weight, int stockLevel, String brand, String screenSize, int ram) {
        super(productId, name, description, price, category, weight, stockLevel);
        this.brand = brand;
        this.screenSize = screenSize;
        this.ram = ram;
    }

    public String getBrand() {
        return brand;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public int getRam() {
        return ram;
    }

    @Override
    public String getProductDetails() {
        return String.format("Brand: %s\nScreen: %s\nRAM: %d GB\nWeight: %.2f kg", brand, screenSize, ram, getWeight());
    }
}
