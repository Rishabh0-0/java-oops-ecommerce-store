package model.order;

import java.time.LocalDateTime;
import java.util.List;

public final class Order {
    private final String orderId;
    private final String customerId;
    private final List<OrderItem> items;
    private final LocalDateTime createdAt;
    private final double totalPrice;

    private OrderStatus status;

    public Order(String orderId, String customerId, List<OrderItem> items, double totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = List.copyOf(items);
        this.totalPrice = totalPrice;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
