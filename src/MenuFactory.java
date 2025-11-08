import java.util.List;
//this is the abstract factory that we will create from spesific factories
public interface MenuFactory {
    List<MenuItem> createMenu();
    MenuItem createItem(String name);
}
