package service.payment;

public class CreditCardPayment implements PaymentStrategy{
    private final String cardNumber;
    private final String nameOnCard;
    private final String cvv;
    private final String expiryDate;

    public CreditCardPayment(String cardNumber, String nameOnCard, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean pay(double amount) {
        if (validateCreditCard()) {
            System.out.println("Processing credit card payment of $" + String.format("%.2f", amount) + "...");
            System.out.println("Payment successful for card ending in " + cardNumber.substring(cardNumber.length() - 4));
            return true;
        } else {
            System.out.println("Credit card validation failed. Payment declined.");
            return false;
        }
    }

    private boolean validateCreditCard() {
        if (cardNumber != null && cardNumber.length() == 16 && cvv != null && cvv.length() == 3) {
            return true;
        }
        return false;
    }
}
