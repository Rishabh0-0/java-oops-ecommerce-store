package model.product;

public abstract class Product {
    protected String productId;
    protected String name;
    protected String description;
    protected double price;
    protected Category category;

    public Product (String productId, String name, String description, double price, Category category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public abstract String getProductDetails();

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
