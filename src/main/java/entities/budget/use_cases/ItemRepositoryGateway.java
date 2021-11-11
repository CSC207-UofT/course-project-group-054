package entities.budget.use_cases;

import entities.budget.entities.Item;

public interface ItemRepositoryGateway {
    Item loadItem(String IUID);

    boolean save(Item item);

    boolean createItem(Item item); // TODO: Create a new data transfer object (see https://piazza.com/class/kt4hlydpsym1bz?cid=843)

    boolean changeQuantity(Item item, int newQuantity);

    boolean deleteItem(String IUID);
}
