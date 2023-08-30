package hello.login.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<Long, Item> store = new HashMap<>();
    private long sequence = 0L;

    public Item save(Item item) {
        item.setId(++this.sequence);
        this.store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return this.store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(this.store.values());
    }

    public void update(Long itemId, Item updateParam) {
        var findItem = this.findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
}
