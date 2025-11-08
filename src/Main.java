import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * This is the "Composition Root". It builds all dependencies
     * and injects them into the Facade.
     */
    private static RestaurantFacade setupRestaurant() {
        System.out.println("Setting up restaurant services");

        // Setup Discount Strategies
        List<DiscountStrategy> allDiscounts = Arrays.asList(
                new PizzaDiscount(),
                new ChickenDiscount()
        );
        DiscountManager discountManager = new DiscountManager(allDiscounts);

        // Setup Tax Strategies
        List<TaxStrategy> allTaxes = Arrays.asList(
                new TaxRate()
        );
        TaxManager taxManager = new TaxManager(allTaxes);

        // Setup Billing Service
        BillingService billingService = new BillingService(discountManager, taxManager);

        // Setup Menu Factories
        Map<String, MenuFactory> allFactories = new HashMap<>();
        allFactories.put("Veg", new VegMenuFactory());
        allFactories.put("NonVeg", new NonVegMenuFactory());
        allFactories.put("Kids", new KidsMenuFactory());

        // Setup Observers
        List<OrderObserver> allObservers = Arrays.asList(
                new KitchenObserver(),
                new WaiterObserver()
        );

        // 6. Setup Order Processing Service
        OrderProcessingService orderProcessingService = new OrderProcessingService();
        allObservers.forEach(orderProcessingService::registerObserver);

        // 7. Inject all services into the Facade
        return RestaurantFacade.getInstance(
                allFactories,
                billingService,
                orderProcessingService
        );
    }

    /**
     * Main program
     */
    public static void main(String[] args) {
        // Setup the restaurant
        RestaurantFacade restaurant = setupRestaurant();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to our Restaurant!");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Display All Menus");
            System.out.println("2. Create New Order");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    restaurant.displayMenus();
                    break;
                case "2":
                    createNewOrder(restaurant, scanner);
                    break;
                case "3":
                    System.out.println("Thank you for visiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createNewOrder(RestaurantFacade restaurant, Scanner scanner) {
        OrderBuilder builder = new OrderBuilder();
        System.out.println("\n--- Create New Order ---");

        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. Place my Order");
            System.out.println("3. Cancel Order");
            System.out.print("Select an option: ");

            String orderChoice = scanner.nextLine();

            switch (orderChoice) {
                case "1":
                    // Add Item Sub-Menu
                    MenuItem item = selectItem(restaurant, scanner);
                    if (item != null) {
                        // Decorator Sub-Menu
                        item = decorateItem(item, scanner);
                        builder.addItem(item);
                        System.out.println("Added to order: " + item.getDescription());
                    }
                    break;
                case "2":
                    // Finalize Sub-Menu
                    // Check if the order is empty before proceeding
                    if (builder.getItems().isEmpty()) {
                        System.out.println("Can't place an empty order. Please add items first.");
                        continue;
                    }
                    OrderType type = selectOrderType(scanner);
                    Order order = builder.setOrderType(type).build();

                    restaurant.placeOrder(order);
                    Bill finalBill = restaurant.checkout(order); // Triggers BillingService

                    selectPayment(restaurant, scanner, finalBill);
                    System.out.println("\nOrder placed. Thank you for your order.");
                    return;
                case "3":
                    System.out.println("Order cancelled.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static void selectPayment(RestaurantFacade restaurant, Scanner scanner, Bill bill) {
        System.out.println("\n--- Process Payment ---");
        System.out.println("Final Total: $" + String.format("%.2f", bill.getFinalTotal()));

        while (true) {
            System.out.println("Select Payment Method:");
            System.out.println("1. Cash");
            System.out.println("2. Credit Card");
            System.out.println("3. Mobile Wallet");
            System.out.print("Select an option: ");

            String paymentChoice = scanner.nextLine();
            PaymentStategy strategy = null;

            switch (paymentChoice) {
                case "1":
                    strategy = new CashPayment();
                    break;
                case "2":
                    strategy = new CreditCardPayment();
                    break;
                case "3":
                    strategy = new MobilePayment();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (strategy != null) {
                // Use the facade to process the payment
                restaurant.processPayment(strategy, bill.getFinalTotal());
                return;
            }
        }
    }
    private static MenuItem selectItem(RestaurantFacade restaurant, Scanner scanner) {
        // This map links the menu type to the item creation keys used in your factories.
        Map<String, String[]> menuKeys = new HashMap<>();
        menuKeys.put("Veg", new String[]{"pizza", "salad"});
        menuKeys.put("NonVeg", new String[]{"pizza", "burger"});
        menuKeys.put("Kids", new String[]{"burger", "pizza"});

        List<String> menuTypes = Arrays.asList("Veg", "NonVeg", "Kids");

        while (true) {
            System.out.println("\n--- Select a Menu ---");
            for (int i = 0; i < menuTypes.size(); i++) {
                System.out.println((i + 1) + ". " + menuTypes.get(i) + " Menu");
            }
            System.out.println("0. Back to Order Menu");
            System.out.print("Please select a menu: ");

            int menuChoice;
            try {
                menuChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (menuChoice == 0) {
                return null;
            }

            // Restart menu selection
            if (menuChoice < 1 || menuChoice > menuTypes.size()) {
                System.out.println("Invalid number. Please select from the list.");
                continue;
            }

            // Valid Menu Selected
            String menuType = menuTypes.get(menuChoice - 1);

            // Get items for the selected menu
            List<MenuItem> displayItems = restaurant.getMenuItemsForMenu(menuType);
            String[] itemKeys = menuKeys.get(menuType);

            if (displayItems.isEmpty() || displayItems.size() != itemKeys.length) {
                System.out.println("Error: Menu items are not configured correctly. Returning.");
                continue;
            }

            while (true) {
                System.out.println("\n--- " + menuType + " Menu ---");
                for (int i = 0; i < displayItems.size(); i++) {
                    MenuItem item = displayItems.get(i);
                    System.out.println((i + 1) + ". " + item.getDescription() + " - $" + String.format("%.2f", item.getPrice()));
                }
                System.out.println("0. Back to Menu Selection");
                System.out.print("Please select an item: ");

                int itemChoice;
                try {
                    itemChoice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                if (itemChoice == 0) {
                    break;
                }

                if (itemChoice > 0 && itemChoice <= displayItems.size()) {
                    String itemKey = itemKeys[itemChoice - 1];
                    // Create and return the new item
                    return restaurant.createMenuItem(menuType, itemKey);
                } else {
                    System.out.println("Invalid number. Please select from the list.");
                }
            }
        }
    }

    private static MenuItem decorateItem(MenuItem item, Scanner scanner) {
        MenuItem currentItem = item;
        while (true) {
            System.out.println("\n-->Add Toppings for: " + currentItem.getName());
            System.out.println("Current Price: $" + String.format("%.2f", currentItem.getPrice()));
            System.out.println("1. Add Extra Cheese ($1.00)");
            System.out.println("2. Add Bacon Topping ($2.00)");
            System.out.println("3. Add Extra Sauce ($0.50)");
            System.out.println("4. Done Adding Toppings");
            System.out.print("Select an option: ");

            String toppingChoice = scanner.nextLine();

            switch (toppingChoice) {
                case "1":
                    currentItem = new ExtraCheese(currentItem);
                    System.out.println("Added Extra Cheese. New item: " + currentItem.getDescription());
                    break;
                case "2":
                    currentItem = new BaconTopping(currentItem);
                    System.out.println("Added Bacon Topping. New item: " + currentItem.getDescription());
                    break;
                case "3":
                    currentItem = new ExtraSouce(currentItem);
                    System.out.println("Added Extra Sauce. New item: " + currentItem.getDescription());
                    break;
                case "4":
                    return currentItem; // Return the item after decorating
                default:
                    System.out.println("Invalid topping choice.");
            }
        }
    }

    private static OrderType selectOrderType(Scanner scanner) {
        while (true) {
            System.out.println("\nSelect Order Type:");
            System.out.println("1. Dine-In");
            System.out.println("2. Delivery");
            System.out.println("3. Takeaway");
            System.out.print("Select an option: ");

            String typeChoice = scanner.nextLine();
            switch (typeChoice) {
                case "1":
                    return OrderType.DINE_IN;
                case "2":
                    return OrderType.DELIVERY;
                case "3":
                    return OrderType.TAKEAWAY;
                default:
                    System.out.println("Invalid type. Please enter a valid number");
            }
        }
    }
}