import java.util.ArrayList;
import java.util.List;

public class Order {
    private  static int idCounter=100;
    private final int orderID;
    private final List<MenuItem> items=new ArrayList<>();
    private final OrderType orderType;
    private  double total;
    public Order(List<MenuItem>items,OrderType orderType)
    {
        this.orderType=orderType;
        this.orderID=idCounter++;
         this.items.addAll(items);
         calculateTotal();

    }
    private void calculateTotal(){
      total=items.stream().mapToDouble(MenuItem::getPrice).sum();
    }
    public List<MenuItem> getItems() { return items; }
    public OrderType getType() { return orderType; }
    public double getTotal() { return total; }
    public int getOrderId() { return orderID; }

    public void applyDiscount(double discount){
       total-=discount;
    }

}
