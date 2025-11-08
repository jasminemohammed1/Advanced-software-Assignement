
import java.util.*;

// This is the facade class, which is responsible for managing all the menus and orders.
// A client doesn't need to know about the KitchenObserver or the DiscountManager; it just tells the facade to placeOrder and generateBill.
public class RestaurantFacade {

    private static RestaurantFacade instance;
//    private final List<OrderObserver> observers = new ArrayList<>();
    private final Map<String, MenueFactory> factories;
    private final BillingService billingService;
    private final OrderProcessingService orderProcessingService;

    private RestaurantFacade(Map<String, MenueFactory> factories,
                             BillingService billingService,
                             OrderProcessingService orderProcessingService) {
        this.factories = factories;
        this.billingService = billingService;
        this.orderProcessingService = orderProcessingService;
    }

    // Implements Singleton, to ensure only one "restaurant" managing all your orders, menus, and observers.
    public static RestaurantFacade getInstance(Map<String, MenueFactory> factories,
                                               BillingService billingService,
                                               OrderProcessingService orderProcessingService) {
        if (instance == null) {
            instance = new RestaurantFacade(factories, billingService, orderProcessingService);
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
    public void checkout(Order order) {
        // bill generation
        Bill finalBill = billingService.generateBill(order);

        // bill display
        billingService.displayBill(order, finalBill);
    }
    public void placeOrder(Order order) {
        orderProcessingService.notifyObserver(order);
    }
}
