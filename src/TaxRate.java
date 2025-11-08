public class TaxRate implements TaxStrategy {

    private final double TAX_RATE = 0.14;

    @Override
    public double calculateTax(Order order) {
        // Calculate tax for the order
        return order.getSubTotal() * TAX_RATE;
    }
}