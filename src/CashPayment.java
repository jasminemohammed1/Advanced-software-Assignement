public class CashPayment implements PaymentStategy{
    @Override
    public void pay(double amount) {
        System.out.println("Paid  "+ amount +  "using Cash.");
    }
}
