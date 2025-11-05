# Java OOP & Design Patterns - E-commerce App

This is a simple, interactive Java console application that simulates an e-commerce store. The primary goal of this project is to demonstrate core Object-Oriented Programming (OOP) principles and implement a variety of fundamental design patterns in a practical way.

## ✨ Core Features

  * **Interactive Console Menu:** A loop-based menu to view products, add to cart, view cart, and check out.
  * **Complex OOP Model:** A rich object model built with abstraction, inheritance, and composition (e.g., `Product` -\> `PhysicalProduct` -\> `Book`).
  * **Shopping Cart:** A functional cart with the ability to add items and undo the last action.
  * **Simulated Checkout:** A checkout process with swappable payment and shipping options.
  * **Real-time Inventory:** A central inventory system that alerts the user when stock is low.

## 🚀 How to Run

This is a standard Maven project.

1.  Clone the repository:
    ```sh
    git clone https://github.com/your-username/your-repo-name.git
    ```
2.  Open the project in your favorite Java IDE (IntelliJ, Eclipse, VS Code).
3.  The IDE should automatically detect it as a Maven project and install dependencies.
4.  Navigate to `src/main/java/com/yourusername/ecommerce/Main.java` and run the `main` method.

## 🧠 Design Patterns Implemented

This project was built to be a hands-on lab for the following design patterns:

  * **Factory Pattern**

      * **Where:** `ProductFactory`
      * **Why:** To create different types of `Product` objects (`Book`, `Laptop`, `Ebook`) from a single method, decoupling the creation logic from the main application.

  * **Strategy Pattern**

      * **Where:** `PaymentStrategy` (and `CreditCardPayment`, `PayPalPayment`) & `ShippingStrategy` (and `FlatRateShipping`, `WeightBasedShipping`)
      * **Why:** To allow the payment and shipping algorithms to be selected and swapped at runtime (during checkout).

  * **Singleton Pattern**

      * **Where:** `InventoryService`
      * **Why:** To ensure that there is only **one** instance of the inventory manager, providing a single, global source of truth for all product stock.

  * **Observer Pattern**

      * **Where:** `InventoryService` (as the Subject) and `LowStockNotifier` (as the Observer)
      * **Why:** To allow `InventoryService` to notify other parts of the system about changes (like low stock) without being coupled to them.

  * **Command Pattern**

      * **Where:** `CartCommand` (and `AddToCartCommand`) & `CartService` (as the Invoker)
      * **Why:** To encapsulate "add to cart" actions into objects. This allows us to store a history of commands and easily implement an **undo** feature.

## Built With

  * **Java**
  * **Maven**
