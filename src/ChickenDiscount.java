public class ChickenDiscount implements DiscountStrategy{
    @Override
    public double calculateDiscount(MenuItem item) {
        return item.getCategory() == Category.CHICKEN ? item.getPrice() * 0.15 : 0;
    }
}
