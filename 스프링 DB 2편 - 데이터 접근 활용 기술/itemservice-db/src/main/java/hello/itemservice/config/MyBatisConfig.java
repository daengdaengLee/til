package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.mybatis.ItemMapper;
import hello.itemservice.repository.mybatis.MyBatisItemRepository;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new ItemServiceV1(itemRepository);
    }

    @Bean
    public ItemRepository itemRepository(ItemMapper itemMapper) {
        return new MyBatisItemRepository(itemMapper);
    }
}
