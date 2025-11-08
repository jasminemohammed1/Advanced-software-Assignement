//use of decorator design pattern this is abstract decorator and every
// specific decortator component will extend from this
public abstract class AddonDecorator extends MenuItem{
    protected final MenuItem wrapped;
    protected AddonDecorator(MenuItem wrapped,String name,Category category,double price)
    {
        super(name,category,price);
        this.wrapped=wrapped;

    }
    public Category getCategory(){
        return wrapped.getCategory();
    }
}
