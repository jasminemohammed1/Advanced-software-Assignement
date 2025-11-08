import java.util.Arrays;
import java.util.List;

public class DiscountManager {

    private  final List<DiscountStrategy>strategies= Arrays.asList(
     new PizzaDiscount(),
     new ChickenDiscount()
    );


    public double getTotalDiscount(List<MenuItem>items)
    {
        return items.stream()
                .mapToDouble(item -> strategies.stream()
                        .mapToDouble(s -> s.calculateDiscount(item))
                        .sum())
                .sum();
    }
}
