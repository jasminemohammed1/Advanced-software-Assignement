import java.util.List;

public class DiscountManager {

    private  final List<DiscountStrategy> strategies;

    public DiscountManager(List<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }
    public double getTotalDiscount(List<MenuItem>items)
    {
        return items.stream()
                .mapToDouble(item -> strategies.stream()
                        .mapToDouble(s -> s.calculateDiscount(item))
                        .sum())
                .sum();
    }
}
