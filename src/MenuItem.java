public  abstract class MenuItem {
    private final String name;
    private final Category category;
    private final double basePrice;

 protected  MenuItem(String name,Category category,double basePrice)
 {
     this.name=name;
     this.category=category;
     this.basePrice=basePrice;
 }
 public String getName(){
     return name;
 }
public Category getCategory(){
     return category;
}
public double getBasePrice(){
     return basePrice;
}
public abstract String getDescription();
public  abstract double getPrice();


}
