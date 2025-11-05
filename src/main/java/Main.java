import model.order.ShoppingCart;
import model.product.Category;
import model.product.DigitalProduct;
import model.product.PhysicalProduct;
import model.product.Product;
import model.user.Customer;
import service.CartService;
import service.InventoryService;
import service.LowStockNotifier;
import service.ProductFactory;
import service.command.AddToCartCommand;
import service.command.CartCommand;
import service.payment.CreditCardPayment;
import service.payment.PayPalPayment;
import service.payment.PaymentStrategy;
import service.shipping.FlatRateShipping;
import service.shipping.ShippingStrategy;
import service.shipping.WeightBasedShipping;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * The main interactive entry point for the e-commerce application.
 * This class runs a console menu to simulate a shopping session.
 */
public class Main {

    // --- Class-level variables ---
    private static final InventoryService inventory = InventoryService.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static Customer customer;
    private static CartService cartService;

    private static Product book;
    private static Product laptop;
    private static Product ebook;

    public static void main(String[] args) {
        setupStore();
        mainMenu();
        scanner.close(); // Close the scanner when the app exits
    }
    private static void setupStore() {
        System.out.println("===== 1. Setting up the Store =====");

        // --- Use the OBSERVER Pattern ---
        inventory.addObserver(new LowStockNotifier());

        // --- Use the FACTORY Pattern ---
        Category booksCategory = new Category("C1", "Fiction Books");
        Category techCategory = new Category("C2", "Laptops");
        Category ebooksCategory = new Category("C3", "Digital Books");

        // (ID, Name, Desc, Price, Category, weight, stock, author, isbn)
        book = ProductFactory.createProduct("book", "B101", "The Java Mind", "A book on OOP",
                29.99, booksCategory, 0.6, 6, "A. Coder", "123-456");

        // (ID, Name, Desc, Price, Category, weight, stock, brand, screen, ram)
        laptop = ProductFactory.createProduct("laptop", "L202", "ProBook 15", "A powerful laptop",
                1299.99, techCategory, 2.2, 10, "TechCorp", "15.6-inch", 16);

        // (ID, Name, Desc, Price, Category, url, size, author, format)
        ebook = ProductFactory.createProduct("ebook", "E303", "Design Patterns", "Digital guide",
                19.99, ebooksCategory, "http://.../download", 5.5, "A. Coder", "PDF");

        // --- Use the SINGLETON Pattern ---
        // Load products into the inventory
        inventory.addProduct((PhysicalProduct) book);
        inventory.addProduct((PhysicalProduct) laptop);

        // Create our customer and their CartService
        customer = new Customer("U001", "javaFan", "fan@test.com", "pass123");
        cartService = new CartService(customer.getCart());

        System.out.println("Store setup complete. Welcome, " + customer.getUsername() + "!\n");
    }

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View Products");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Please choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        displayProducts();
                        break;
                    case 2:
                        addToCart();
                        break;
                    case 3:
                        viewCart();
                        break;
                    case 4:
                        doCheckout();
                        break;
                    case 5:
                        running = false;
                        System.out.println("\nThank you for shopping with us!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    /**
     * Displays all available products.
     */
    private static void displayProducts() {
        System.out.println("\n--- Available Products ---");
        // Book
        System.out.printf("ID: %s | %s | $%.2f | Stock: %d\n",
                book.getProductId(), book.getName(), book.getPrice(), inventory.getStock(book));
        // Laptop
        System.out.printf("ID: %s | %s | $%.2f | Stock: %d\n",
                laptop.getProductId(), laptop.getName(), laptop.getPrice(), inventory.getStock(laptop));
        // Ebook (Infinite stock)
        System.out.printf("ID: %s | %s | $%.2f | Stock: N/A (Digital)\n",
                ebook.getProductId(), ebook.getName(), ebook.getPrice());
    }

    /**
     * Handles the logic for adding an item to the cart.
     */
    private static void addToCart() {
        System.out.println("\n--- Add to Cart ---");
        System.out.print("Enter Product ID (e.g., B101, L202, E303): ");
        String id = scanner.nextLine().toUpperCase();
        Product productToAdd;

        // Find the product
        if (id.equals(book.getProductId())) productToAdd = book;
        else if (id.equals(laptop.getProductId())) productToAdd = laptop;
        else if (id.equals(ebook.getProductId())) productToAdd = ebook;
        else {
            System.out.println("Invalid Product ID.");
            return;
        }

        System.out.print("Enter quantity: ");
        try {
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                return;
            }

            // For digital products, stock is irrelevant
            if (productToAdd instanceof DigitalProduct) {
                // Digital products don't use the inventory service
            }
            // For physical products, check stock
            else if (!inventory.isInStock(productToAdd, quantity)) {
                System.out.println("Sorry, not enough stock. Available: " + inventory.getStock(productToAdd));
                return;
            }

            // --- Use the COMMAND Pattern ---
            CartCommand command = new AddToCartCommand(cartService.getCart(), productToAdd, quantity);
            cartService.executeCommand(command);
            System.out.println("Added " + quantity + " x " + productToAdd.getName() + " to cart.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid quantity. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    /**
     * Displays the cart and offers an "undo" option.
     */
    private static void viewCart() {
        System.out.println("\n--- Your Shopping Cart ---");
        ShoppingCart cart = cartService.getCart();
        Map<Product, Integer> items = cart.getItems();

        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                System.out.printf("- %s | Qty: %d | Price: $%.2f | Subtotal: $%.2f\n",
                        entry.getKey().getName(),
                        entry.getValue(),
                        entry.getKey().getPrice(),
                        entry.getValue() * entry.getKey().getPrice());
            }
            System.out.println("------------------------");
            System.out.printf("Total: $%.2f\n", cart.calculateTotal());
        }

        // --- Use the COMMAND Pattern (Undo) ---
        System.out.print("Type 'undo' to remove the last item, or 'back' to return to menu: ");
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("undo")) {
            cartService.undoLastCommand();
            System.out.println("Last action undone.");
            viewCart(); // Show the cart again
        }
    }

    /**
     * Handles the entire checkout process.
     */
    private static void doCheckout() {
        ShoppingCart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            System.out.println("\nYour cart is empty. Nothing to check out.");
            return;
        }

        System.out.println("\n--- Checkout ---");
        double subtotal = cart.calculateTotal();

        // --- Use STRATEGY Pattern (Shipping) ---
        ShippingStrategy shipping = selectShippingStrategy(cart);
        double shippingCost = shipping.calculateCost(cart);
        double finalTotal = subtotal + shippingCost;

        System.out.printf("Cart Subtotal: $%.2f\n", subtotal);
        System.out.printf("Shipping: $%.2f\n", shippingCost);
        System.out.printf("FINAL TOTAL: $%.2f\n", finalTotal);

        // --- Use STRATEGY Pattern (Payment) ---
        PaymentStrategy payment = selectPaymentStrategy();
        if (payment == null) {
            System.out.println("Checkout cancelled.");
            return;
        }

        // --- Process the Order ---
        System.out.println("\nProcessing order...");

        // 1. Process payment
        if (payment.pay(finalTotal)) {
            // 2. Reduce stock (This will trigger the OBSERVER)
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                // Only reduce stock for physical products
                if(entry.getKey() instanceof PhysicalProduct) {
                    inventory.reduceStock(entry.getKey(), entry.getValue());
                }
            }
            // 3. Clear cart
            cart.clearCart();
            System.out.println("\n--- Order Placed Successfully! ---");
            System.out.println("Thank you for your purchase.");
        } else {
            System.out.println("\n--- Payment Failed! ---");
            System.out.println("Order cancelled. Please try again.");
        }
    }

    /**
     * Helper method to let the user select a Shipping Strategy.
     */
    private static ShippingStrategy selectShippingStrategy(ShoppingCart cart) {
        System.out.println("Select shipping method:");
        System.out.println("1. Flat Rate ($5.00)");
        System.out.println("2. Weight Based ($1.50 / kg)");

        while(true) {
            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                return new FlatRateShipping(5.00);
            } else if (choice.equals("2")) {
                return new WeightBasedShipping(1.50);
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    /**
     * Helper method to let the user select a Payment Strategy.
     */
    private static PaymentStrategy selectPaymentStrategy() {
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.println("3. Cancel");

        while(true) {
            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.print("Enter card number (16 digits): ");
                String cc = scanner.nextLine();
                System.out.print("Enter CVV (3 digits): ");
                String cvv = scanner.nextLine();
                return new CreditCardPayment(cc, customer.getUsername(), cvv, "12/28");
            } else if (choice.equals("2")) {
                System.out.print("Enter PayPal email: ");
                String email = scanner.nextLine();
                return new PayPalPayment(email, "dummypass");
            } else if (choice.equals("3")) {
                return null;
            }
            System.out.println("Invalid choice. Try again.");
        }
    }
}
