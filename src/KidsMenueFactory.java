import java.util.Arrays;
import java.util.List;

public class KidsMenueFactory implements MenueFactory {
    @Override
    public List<MenuItem> createMenue() {
        return Arrays.asList(
                new SimpleMenuItem("Mini Burger", Category.BURGER, 5.0),
                new SimpleMenuItem("Mini Pizza", Category.PIZZA, 5.5)
        );
    }

    @Override
    public MenuItem createItem(String name) {
        switch (name.toLowerCase()) {
            case "burger": return new SimpleMenuItem("Mini Burger", Category.BURGER, 5.0);
            case "pizza": return new SimpleMenuItem("Mini Pizza", Category.PIZZA, 5.5);
            default: return null;
        }
    }
}
