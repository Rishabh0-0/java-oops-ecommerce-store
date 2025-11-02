package service.payment;

public class PayPalPayment implements PaymentStrategy{
    private final String email;
    private final String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean pay(double amount) {
        if (validateEmail()) {
            System.out.println("Logging into PayPal with email: " + email + "...");
            System.out.println("Processing PayPal payment of $" + String.format("%.2f", amount) + "...");
            System.out.println("Payment successful.");
            return true;
        } else {
            System.out.println("Invalid PayPal email. Payment declined.");
            return false;
        }
    }

    private boolean validateEmail() {
        return email != null && email.contains("@");
    }
}
