# Restaurant Ordering & Billing System

This is a modular and extensible restaurant ordering system built in Java, designed as a project for the Advanced Software Engineering course.

The primary goal of this project is to demonstrate the practical application of **SOLID design principles** and core **object-oriented design patterns**. The system is built with a clean, decoupled architecture that is easy to maintain and extend.

## Features

* **Interactive Menu:** A command-line interface (CLI) for browsing menus and creating orders.
* **Multiple Menu Types:** Supports different menus (e.g., Veg, Non-Veg, Kids) using the **Abstract Factory** pattern.
* **Customizable Items:** Allows users to add toppings (e.g., Extra Cheese, Bacon) to any menu item using the **Decorator** pattern.
* **Dynamic Billing:** Automatically calculates subtotals, applies complex discounts, and adds taxes using the **Strategy** pattern.
* **Decoupled Notifications:** Notifies the kitchen and waiters of new orders using the **Observer** pattern.
* **Multiple Payment Methods:** Supports various payment types (Cash, Credit Card) using the **Strategy** pattern.
* **Clean API:** A **Facade** provides a simple, unified entry point for all system operations.

## Prerequisites

* Java Development Kit (JDK) 8 or newer
* Java Compiler (`javac`)

## How to Run

The project is designed to be compiled and run directly from the command line without any external dependencies.

1.  **Open your Terminal:** Navigate to the root directory containing all 30 `.java` files.

2.  **Compile all Java files:**

    ```bash
    javac *.java
    ```

    (Note: This command assumes all files are in the default package).

3.  **Run the Main class:**

    ```bash
    java Main
    ```

    This will start the interactive menu.

## Example Test Cases

Here are a few scenarios to test the system's functionality.

### Test Case 1: Simple Order (No Discount)

This test validates the **Abstract Factory** and **Builder** patterns, as well as the basic billing.

1.  Run `java Main`.
2.  Select **2. Create New Order**.
3.  Select **1. Add Item**.
4.  Select **1. Veg Menu**.
5.  Select **2. Veg Salad**.
6.  The system will ask for toppings. Select **4. Done Adding Toppings**.
7.  Select **2. Finalize and Place Order**.
8.  Select **3. Takeaway** for the order type.
9.  **Observe:** The system will print a final bill. The discount will be $0.00, and the total will be the salad price + 14% tax.
10. Select **1. Cash** to complete the order.

### Test Case 2: Complex Order (Decorator + Discount)

This test validates the **Decorator**, **Strategy** (Discount), and **Observer** patterns.

1.  Run `java Main`.
2.  Select **2. Create New Order**.
3.  Select **1. Add Item**.
4.  Select **2. NonVeg Menu**.
5.  Select **1. Chicken Pizza**.
6.  The system will ask for toppings. Select **1. Add Extra Cheese**.
7.  The system will loop. Select **2. Add Bacon Topping**.
8.  The system will loop. Select **4. Done Adding Toppings**.
9.  **Observe:** The item being added will be "Chicken Pizza + With extra cheese + with extra bacon topping".
10. Select **2. Finalize and Place Order**.
11. Select **1. Dine-In**.
12. **Observe:** The system will immediately print:
    * `Processing Order #...`
    * `Kitchen notified: Prepare Order #...`
    * `Waiter notified: Serve Order #...`
13. **Observe:** The final bill will be printed. It will include:
    * A **Subtotal** for the (Pizza + Cheese + Bacon).
    * A **Discount** of 10% (from the `PizzaDiscount` strategy).
    * A **Tax** of 14%.
14. Select **2. Credit Card** to complete the order.

## Discount Scenarios

The system's discount logic is controlled by the **Strategy** pattern. The `DiscountManager` applies all relevant strategies to the items in an order.

* **Scenario 1: Pizza Discount (10%)**

    * **Trigger:** Add any item with the `Category.PIZZA`.
    * **How to Test:** Order the "Veg Pizza", "NonVeg Pizza", or "Kids Pizza".
    * **Expected Result:** The bill will show a discount equal to 10% of that item's base price.

* **Scenario 2: Chicken Discount (15%)**

    * **Trigger:** Add any item with the `Category.CHICKEN`.
    * **How to Test:** *Note: Your current factories do not create an item with the `Category.CHICKEN`. The "Chicken Pizza" has the `PIZZA` category, so it gets the pizza discount.*
    * **To Test:** If you add a new item like `new SimpleMenuItem("Chicken Wings", Category.CHICKEN, 8.0)` to your `NonVegMenueuFactory`, ordering it will trigger this 15% discount.

* **Scenario 3: Combined Discounts**

    * **Trigger:** Add a `PIZZA` item and a `CHICKEN` item (see above) to the same order.
    * **Expected Result:** The `DiscountManager` will apply *both* strategies. The final "Discount" line on the bill will be the sum of the 10% pizza discount *and* the 15% chicken discount.

## Key Design Patterns Used

* **Facade (`RestaurantFacade`):** Provides a single, simplified interface to the complex sub-systems (billing, ordering, menus).
* **Singleton (`RestaurantFacade`):** Ensures only one instance of the `RestaurantFacade` exists, managing the system's state.
* **Strategy (`PaymentStategy`, `DiscountStrategy`, `TaxStrategy`):** Decouples algorithms for payments, discounts, and taxes, making them interchangeable and extensible (OCP).
* **Observer (`OrderProcessingService`, `OrderObserver`):** Allows `KitchenObserver` and `WaiterObserver` to subscribe to order events without coupling them to the order-placing logic.
* **Decorator (`AddonDecorator`):** Allows dynamically adding new "toppings" (both cost and description) to `MenuItem` objects at runtime.
* **Abstract Factory (`MenuFactory`):** Provides an interface for creating *families* of related menu items (Veg, NonVeg) without specifying their concrete classes.
* **Builder (`OrderBuilder`):** Simplifies the creation of a complex `Order` object by using a step-by-step, fluent interface.