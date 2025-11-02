package service.shipping;

import model.order.ShoppingCart;
import model.product.PhysicalProduct;
import model.product.Product;

import java.util.Map;

public class WeightBasedShipping implements ShippingStrategy{
    private final double costPerKilogram;

    public WeightBasedShipping(double costPerKilogram) {
        this.costPerKilogram = costPerKilogram;
    }

    @Override
    public double calculateCost(ShoppingCart cart) {
        double totalWeight = 0.00;

        for (Map.Entry<Product, Integer> entry: cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product instanceof PhysicalProduct physicalProduct) {
                totalWeight += physicalProduct.getWeight() * quantity;
            }
        }

        return totalWeight * this.costPerKilogram;
    }
}
