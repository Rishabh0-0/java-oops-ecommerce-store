package service.shipping;

import model.order.ShoppingCart;

public class FlatRateShipping implements ShippingStrategy{
    private final double rate;

    public FlatRateShipping(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateCost(ShoppingCart cart) {
        return this.rate;
    }
}
