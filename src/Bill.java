public class Bill {
    private final double subtotal;
    private final double discountApplied;
    private final double tax;
    private final double finalTotal;

    public Bill(double subtotal, double discountApplied, double tax) {
        this.subtotal = subtotal;
        this.discountApplied = discountApplied;
        this.tax = tax;
        this.finalTotal = subtotal - discountApplied + tax;
    }

    public double getSubtotal() { return subtotal; }
    public double getDiscountApplied() { return discountApplied; }
    public double getTax() { return tax; }
    public double getFinalTotal() { return finalTotal; }
}
