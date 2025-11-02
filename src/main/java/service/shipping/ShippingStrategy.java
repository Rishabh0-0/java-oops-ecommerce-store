package service.shipping;

import model.order.ShoppingCart;

public interface ShippingStrategy {
    double calculateCost(ShoppingCart cart);
}
