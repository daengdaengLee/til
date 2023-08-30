package hello.login.domain.item;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {
    @Test
    void save() {
        //given
        var itemRepository = new ItemRepository();
        var item = new Item("itemA", 10000, 10);

        //when
        var savedItem = itemRepository.save(item);

        //then
        var findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        var itemRepository = new ItemRepository();
        var item1 = new Item("item1", 10000, 10);
        var item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        var result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        //given
        var itemRepository = new ItemRepository();
        var item = new Item("item1", 10000, 10);

        var savedItem = itemRepository.save(item);
        var itemId = savedItem.getId();

        //when
        var updateParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);

        var findItem = itemRepository.findById(itemId);

        //then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}
