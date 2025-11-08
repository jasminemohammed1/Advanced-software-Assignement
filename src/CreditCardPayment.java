public class CreditCardPayment implements PaymentStategy{
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}
