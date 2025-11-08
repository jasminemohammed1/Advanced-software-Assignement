import java.util.List;
//this is the abstract factory that we will create from spesific factories
public interface MenueFactory {
    List<MenuItem> createMenue();
    MenuItem createItem(String name);
}
