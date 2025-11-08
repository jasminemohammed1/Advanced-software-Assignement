public class BillingService {
    private final DiscountManager discountManager;
    private final TaxManager taxManager;

    public BillingService(DiscountManager discountManager, TaxManager taxManager) {
        this.discountManager = discountManager;
        this.taxManager = taxManager;
    }

    // It generates a final Bill from an Order.
    public Bill generateBill(Order order) {
        // Get the subtotal from the order
        double subtotal = order.getSubTotal();

        // Ask the DiscountManager for the total discount
        double discount = discountManager.getTotalDiscount(order.getItems());

        double tax = taxManager.calculateTotalTax(order);

        // Create the final Bill object
        return new Bill(subtotal, discount, tax);
    }

    // This method displays the bill to the user.
    public void displayBill(Order order, Bill bill) {
        System.out.println("\n--- BILL FOR ORDER #" + order.getOrderId() + " ---");
        order.getItems().forEach(i ->
                System.out.println(i.getDescription() + " : $" + String.format("%.2f", i.getPrice())));
        System.out.println("-------------------------");
        System.out.println("Subtotal: $" + String.format("%.2f", bill.getSubtotal()));
        System.out.println("Discount: -$" + String.format("%.2f", bill.getDiscountApplied()));
        System.out.println("Tax (14%): +$" + String.format("%.2f", bill.getTax()));
        System.out.println("-------------------------");
        System.out.println("Total: $" + String.format("%.2f", bill.getFinalTotal()));
        System.out.println("-------------------------");
    }
}
