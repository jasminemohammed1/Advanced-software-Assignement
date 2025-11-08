//this extra adding its cost=ordinary+2.0
//its description = base description+ ith extra bacon topping
// this is a specific decorator
public class BaconTopping extends AddonDecorator{
    public BaconTopping(MenuItem wrapped)
    {
        super(wrapped, wrapped.getName()+" + with extra bacon topping ",wrapped.getCategory(),2.0);
    }

    public String getDescription(){
        return wrapped.getDescription()+" + with extra bacon topping.";
    }
  public   double getPrice(){
        return wrapped.getPrice()+2.0;
  }
}
