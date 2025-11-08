import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger idCounter = new AtomicInteger(100);
    private final int orderID;
    private final List<MenuItem> items = new ArrayList<>();
    private final OrderType orderType;
    // Represents the total cost of the order before discounts and tax are applied
    private double sub_total;
    public Order(List<MenuItem>items,OrderType orderType) {
        this.orderType=orderType;
        this.orderID = idCounter.getAndIncrement();
        this.items.addAll(items);
        this.sub_total = calculateSubTotal();
    }

    private double calculateSubTotal(){
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }
    public List<MenuItem> getItems() { return items; }
    public OrderType getType() { return orderType; }
    public double getSubTotal() { return sub_total; }
    public int getOrderId() { return orderID; }

    // Apply a discount to the order
//    public void applyDiscount(double discount){
//       total-=discount;
//    }

}
