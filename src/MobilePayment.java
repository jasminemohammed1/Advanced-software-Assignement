public class MobilePayment implements PaymentStategy{

    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Mobile Wallet.");
    }
}
