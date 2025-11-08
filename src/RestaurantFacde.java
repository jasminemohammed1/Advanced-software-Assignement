
import java.util.*;

public class RestaurantFacde implements OrderSubject {

    private static RestaurantFacde instance;
    private final List<OrderObserver> observers = new ArrayList<>();
    private final Map<String, MenueFactory> factories = new HashMap<>();
    private final DiscountManager discountManager = new DiscountManager();

    private RestaurantFacde() {
        factories.put("Veg", new VegMenueFactory());
        factories.put("NonVeg", new NonVegMenueuFactory());
        factories.put("Kids", new KidsMenueFactory());
        registerObserver(new KitchenObserver());
        registerObserver(new WaiterObserver());
    }

    public static RestaurantFacde getInstance() {
        if (instance == null) {
            instance = new RestaurantFacde();
        }
        return instance;
    }

    public void displayMenus() {
        System.out.println("--- Restaurant Menus ---");
        factories.forEach((k, v) -> {
            System.out.println(k + " Menu:");
            v.createMenue().forEach(i ->
                    System.out.println("  - " + i.getDescription() + " : $" + i.getPrice()));
        });
        System.out.println("-------------------------");
    }

    public MenuItem createMenuItem(String menuType, String itemName) {
        MenueFactory factory = factories.get(menuType);
        return factory != null ? factory.createItem(itemName) : null;
    }

    public void applyDiscounts(Order order) {
        double discount = discountManager.getTotalDiscount(order.getItems());
        order.applyDiscount(discount);
        System.out.println("💰 Discount applied: $" + discount);
    }

    public void placeOrder(Order order) {
        notifyObserver(order);
    }

    public void generateBill(Order order) {
        System.out.println("\n--- BILL ---");
        order.getItems().forEach(i ->
                System.out.println(i.getDescription() + " : $" + i.getPrice()));
        System.out.println("Total: $" + order.getTotal());
        System.out.println("-------------");
    }

    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObserver(Order order) {
        observers.forEach(o -> o.update(order));
    }
}
