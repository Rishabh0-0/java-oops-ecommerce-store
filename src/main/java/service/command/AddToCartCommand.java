package service.command;

import model.order.ShoppingCart;
import model.product.Product;

public class AddToCartCommand implements CartCommand{
    private final ShoppingCart cart;
    private final Product product;
    private final int quantity;

    public AddToCartCommand(ShoppingCart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        this.cart.addItem(product, quantity);
        System.out.println("Command: Added " + quantity + " x " + product.getName() + " to cart.");
    }

    @Override
    public void undo() {
        int currentQuantity = this.cart.getItems().getOrDefault(product, 0);
        int newQuantity = currentQuantity - this.quantity;

        this.cart.updateQuantity(product, newQuantity);
        System.out.println("Command: Undid adding " + quantity + " x " + product.getName() + " from cart.");
    }
}
