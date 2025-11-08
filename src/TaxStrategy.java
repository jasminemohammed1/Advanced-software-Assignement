// calculate tax for an order and return the amount
public interface TaxStrategy {
    double calculateTax(Order order);
}