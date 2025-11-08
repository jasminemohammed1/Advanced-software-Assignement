import java.util.Arrays;
import java.util.List;

// this is concrete factory type of menue that has specific items in it
public class VegMenuFactory implements MenuFactory {
    public List<MenuItem> createMenu(){
        return Arrays.asList(
        new SimpleMenuItem("Veg Pizza",Category.PIZZA,8.0),
                new SimpleMenuItem("Veg Salad",Category.SALAD,6.0)
        );
    }

    @Override
    public MenuItem createItem(String name) {
        switch (name.toLowerCase())
        {
            case "pizza":return new SimpleMenuItem("Veg Pizza",Category.PIZZA,8.0);
            case "salad":return new SimpleMenuItem("Veg Salad",Category.SALAD,6.0);
            default:return  null;

        }

    }
}
