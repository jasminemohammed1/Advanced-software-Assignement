
import java.util.*;

// This is the facade class, which is responsible for managing all the menus and orders.
// A client doesn't need to know about the KitchenObserver or the DiscountManager; it just tells the facade to placeOrder and generateBill.
public class RestaurantFacade {

    private static RestaurantFacade instance;
//    private final List<OrderObserver> observers = new ArrayList<>();
    private final Map<String, MenuFactory> factories;
    private final BillingService billingService;
    private final OrderProcessingService orderProcessingService;

    private RestaurantFacade(Map<String, MenuFactory> factories,
                             BillingService billingService,
                             OrderProcessingService orderProcessingService) {
        this.factories = factories;
        this.billingService = billingService;
        this.orderProcessingService = orderProcessingService;
    }

    // Implements Singleton, to ensure only one "restaurant" managing all your orders, menus, and observers.
    public static RestaurantFacade getInstance(Map<String, MenuFactory> factories,
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
            v.createMenu().forEach(i ->
                    System.out.println("  - " + i.getDescription() + " : $" + i.getPrice()));
        });
        System.out.println("-------------------------");
    }

    public MenuItem createMenuItem(String menuType, String itemName) {
        MenuFactory factory = factories.get(menuType);
        return factory != null ? factory.createItem(itemName) : null;
    }
    public Bill checkout(Order order) {
        // bill generation
        Bill finalBill = billingService.generateBill(order);

        // bill display
        billingService.displayBill(order, finalBill);

        // Return the bill so the Main class can use it for payment
        return finalBill;
    }
    public void placeOrder(Order order) {
        orderProcessingService.notifyObserver(order);
    }
    public List<MenuItem> getMenuItemsForMenu(String menuType) {
        MenuFactory factory = factories.get(menuType);
        if (factory != null) {
            // This returns the list of items from the factory's createMenu() method
            return factory.createMenu();
        }
        return Collections.emptyList();
    }
    public void processPayment(PaymentStategy strategy, double amount) {
        strategy.pay(amount);
        System.out.println("Payment processed successfully.");
    }
}
