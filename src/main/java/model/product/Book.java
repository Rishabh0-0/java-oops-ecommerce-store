package model.product;

public class Book extends PhysicalProduct{
    private final String author;
    private final String isbn;

    public Book(String productId, String name, String description, double price, Category category, double weight, int stockLevel, String author, String isbn) {
        super(productId, name, description, price, category, weight, stockLevel);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getProductDetails() {
        return String.format("Author: %s\nISBN: %s\nWeight: %.2f kg", author, isbn, getWeight());
    }
}
