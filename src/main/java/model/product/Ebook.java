package model.product;

public class Ebook extends DigitalProduct{
    private final String author;
    private final String format;

    public Ebook(String productId, String name, String description, double price, Category category, String downloadUrl, double fileSize, String author, String format) {
        super(productId, name, description, price, category, downloadUrl, fileSize);
        this.author = author;
        this.format = format;
    }

    public String getAuthor() {
        return author;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String getProductDetails() {
        return String.format("Author: %s\nFormat: %s\nFile Size: %.2f MB", author, format, getFileSize());
    }
}
