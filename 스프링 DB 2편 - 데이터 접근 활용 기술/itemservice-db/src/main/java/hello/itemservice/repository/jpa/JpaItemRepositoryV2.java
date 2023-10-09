package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional
public class JpaItemRepositoryV2 implements ItemRepository {
    private final SpringDataJpaItemRepository repository;

    @Override
    public Item save(Item item) {
        return this.repository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        var findItem = this.repository.findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        var itemName = cond.getItemName();
        var maxPrice = cond.getMaxPrice();

        if (StringUtils.hasText(itemName) && maxPrice != null) {
            // return this.repository.findByItemNameLikeAndPriceLessThanEqual("%" + itemName + "%", maxPrice);
            return this.repository.findItems("%" + itemName + "%", maxPrice);
        } else if (StringUtils.hasText(itemName)) {
            return this.repository.findByItemNameLike("%" + itemName + "%");
        } else if (maxPrice != null) {
            return this.repository.findByPriceLessThanEqual(maxPrice);
        } else {
            return this.repository.findAll();
        }
    }
}
