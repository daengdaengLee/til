package hello.itemservice.domain;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

//    @Autowired
//    PlatformTransactionManager transactionManager;
//    TransactionStatus status;

//    @BeforeEach
//    void beforeEach() {
//        // 트랜잭션 시작
//        this.status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
//    }

    @AfterEach
    void afterEach() {
        //MemoryItemRepository 의 경우 제한적으로 사용
        if (this.itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) this.itemRepository).clearStore();
        }
        // 트랜잭션 롤백
//        this.transactionManager.rollback(this.status);
    }

    //    @Commit
    @Test
    void save() {
        //given
        var item = new Item("itemA", 10000, 10);

        //when
        var savedItem = this.itemRepository.save(item);

        //then
        var findItem = this.itemRepository.findById(item.getId()).orElseThrow();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        //given
        var item = new Item("item1", 10000, 10);
        var savedItem = this.itemRepository.save(item);
        var itemId = savedItem.getId();

        //when
        var updateParam = new ItemUpdateDto("item2", 20000, 30);
        this.itemRepository.update(itemId, updateParam);

        //then
        var findItem = itemRepository.findById(itemId).orElseThrow();
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void findItems() {
        //given
        var item1 = new Item("itemA-1", 10000, 10);
        var item2 = new Item("itemA-2", 20000, 20);
        var item3 = new Item("itemB-1", 30000, 30);

        this.itemRepository.save(item1);
        this.itemRepository.save(item2);
        this.itemRepository.save(item3);

        //둘 다 없음 검증
        this.test(null, null, item1, item2, item3);
        this.test("", null, item1, item2, item3);

        //itemName 검증
        this.test("itemA", null, item1, item2);
        this.test("temA", null, item1, item2);
        this.test("itemB", null, item3);

        //maxPrice 검증
        this.test(null, 10000, item1);

        //둘 다 있음 검증
        this.test("itemA", 10000, item1);
    }

    void test(String itemName, Integer maxPrice, Item... items) {
        var result = this.itemRepository.findAll(new ItemSearchCond(itemName, maxPrice));
        assertThat(result).containsExactly(items);
    }
}
