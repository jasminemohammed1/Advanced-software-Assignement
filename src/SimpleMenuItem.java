public class SimpleMenuItem extends MenuItem{
    public SimpleMenuItem(String name,Category category,double price)
    {
        super(name,category,price);
    }
    @Override
    public  String getDescription(){
        return getName();
    }
    @Override
    public double getPrice(){
        return  getBasePrice();
    }

}
