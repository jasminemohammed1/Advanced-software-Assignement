//this extra adding its cost=ordinary+1.0
//its description = base description+ extra chees
// this is a specific decorator
public class ExtraCheese extends AddonDecorator {
    public ExtraCheese(MenuItem wrapped)
    {
        super(wrapped,wrapped.getName()+" + With extra cheese",wrapped.getCategory(),1.0);

    }
    @Override
  public String getDescription(){
        return  wrapped.getDescription()+" + With extra cheese";
    }
    public  double getPrice(){
        return wrapped.getPrice()+1.0;
    }
}
