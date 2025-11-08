import java.util.ArrayList;
import java.util.List;

//use builder DP
// It allows you to build an order step-by-step
public class OrderBuilder {
    private  final List<MenuItem> items=new ArrayList<>();
    private OrderType orderType=OrderType.DINE_IN;


    public OrderBuilder addItem(MenuItem item) {
        items.add(item);
        return this;
    }
    public OrderBuilder setOrderType(OrderType type) {
        this.orderType=type;
        return this;
    }
    public Order build()
    {
        return new Order(items,orderType);
    }
    public List<MenuItem> getItems() {
        return items;
    }
}
