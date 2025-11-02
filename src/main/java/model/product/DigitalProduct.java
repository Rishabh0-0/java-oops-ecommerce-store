package model.product;

public abstract class DigitalProduct extends Product{
    private String downloadUrl;
    private double fileSize;

    public DigitalProduct(String productId, String name, String description, double price, Category category, String downloadUrl, double fileSize) {
        super(productId, name, description, price, category);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public double getFileSize() {
        return fileSize;
    }
}
