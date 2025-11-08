public class WaiterObserver implements OrderObserver {

    public void update(Order order)
    {
        System.out.println("Waiter notified: Serve Order #" + order.getOrderId());
    }
}
