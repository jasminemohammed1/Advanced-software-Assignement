import java.util.List;

public class TaxManager {

    private final List<TaxStrategy> strategies;

    public TaxManager(List<TaxStrategy> strategies) {
        this.strategies = strategies;
    }

     // Calculates the total tax for an order
    // param order to be taxed
    public double calculateTotalTax(Order order) {
        return strategies.stream()
                .mapToDouble(strategy -> strategy.calculateTax(order))
                .sum();
    }
}