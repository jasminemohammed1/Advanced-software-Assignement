public class PizzaDiscount implements  DiscountStrategy{
    @Override
    public double calculateDiscount(MenuItem item) {
      return item.getCategory()==Category.PIZZA?item.getPrice()*0.1:0;
    }
}
