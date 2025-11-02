package model.user;

import model.order.Order;
import model.order.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private final ShoppingCart cart;
    private final List<Order> orderHistory;

    public Customer(String userId, String username, String email, String password) {
        super(userId, username, email, password);
        this.cart = new ShoppingCart();
        this.orderHistory = new ArrayList<>();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }
}
