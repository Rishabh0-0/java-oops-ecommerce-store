package service;

import model.product.*;

public class ProductFactory {

    /* TODO: This method is a placeholder. In a real app, it would take a more generic set of parameters (like a Map<String, Object>) to be more flexible. */

    public static Product createProduct(String type, String productId, String name, String description, double price, Category category
    , Object... args) {

        /* TODO: Object... args: This "varargs" parameter is a simple (though not perfectly type-safe) way to pass in the different arguments */

        return switch(type.toLowerCase()) {
            case "book" ->
                    new Book(productId, name, description, price, category, (Double) args[0], (Integer) args[1], (String) args[2], (String) args[3]);
            case "laptop" ->
                    new Laptop(productId, name, description, price, category, (Double) args[0], (Integer) args[1], (String) args[2], (String) args[3], (Integer) args[4]);
            case "ebook" ->
                    new Ebook(productId, name, description, price, category, (String) args[0], (Double) args[1], (String) args[2], (String) args[3]);
            default -> throw new IllegalArgumentException("Unknown product type: " + type);
        };
    }
}
