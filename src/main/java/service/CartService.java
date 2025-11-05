package service;

import model.order.ShoppingCart;
import service.command.CartCommand;

import java.util.Stack;

public class CartService{

    private final ShoppingCart cart;
    private final Stack<CartCommand> commandHistory;

    public CartService(ShoppingCart cart) {
        this.cart = cart;
        this.commandHistory = new Stack<>();
    }

    public void executeCommand(CartCommand command) {
        command.execute();
        this.commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (this.commandHistory.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }

        CartCommand lastCommand = this.commandHistory.pop();
        lastCommand.undo();
    }

    public ShoppingCart getCart() {
        return cart;
    }
}
