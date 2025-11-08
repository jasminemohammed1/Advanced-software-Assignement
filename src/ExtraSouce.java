//this extra adding its cost=ordinary+0.5
//its description = base description+ extra souce
// this is a specific decorator
public class ExtraSouce extends AddonDecorator{
    public ExtraSouce(MenuItem wrapped)
    {
        super(wrapped,wrapped.getName()+" + with extra souce",wrapped.getCategory(),0.5);
    }
@Override
    public String getDescription(){
        return wrapped.getDescription()+" + with extra souce.";
    }
    public double getPrice(){
        return wrapped.getPrice()+0.5;
    }
}
