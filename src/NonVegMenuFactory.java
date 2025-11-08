import java.util.Arrays;
import java.util.List;

public class NonVegMenuFactory implements MenuFactory {
    @Override
    public List<MenuItem> createMenu() {
        return Arrays.asList(
                new SimpleMenuItem("Chicken Pizza", Category.PIZZA, 10.0),
                new SimpleMenuItem("Beef Burger", Category.BURGER, 9.0)
        );
    }

    @Override
    public MenuItem createItem(String name) {
        switch (name.toLowerCase()) {
            case "pizza": return new SimpleMenuItem("Chicken Pizza", Category.PIZZA, 10.0);
            case "burger": return new SimpleMenuItem("Beef Burger", Category.BURGER, 9.0);
            default: return null;
        }
    }
}
