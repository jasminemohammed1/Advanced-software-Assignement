public class KitchenObserver implements OrderObserver{
    public void update(Order order){
        System.out.println("Kitchen notified: Prepare Order #" + order.getOrderId());
    }
}
