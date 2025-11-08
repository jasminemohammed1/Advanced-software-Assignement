// here this is the interface to use the observer DP
public interface OrderSubject {
    void registerObserver(OrderObserver observer);
    void notifyObserver(Order order);
}
