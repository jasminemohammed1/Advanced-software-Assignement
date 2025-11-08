import java.util.ArrayList;
import java.util.List;

// This class's ONLY responsibility is to manage and notify order observers.
public class OrderProcessingService implements OrderSubject {

    private final List<OrderObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObserver(Order order) {
        System.out.println("Processing Order #" + order.getOrderId() + "...");
        observers.forEach(o -> o.update(order));
    }
}