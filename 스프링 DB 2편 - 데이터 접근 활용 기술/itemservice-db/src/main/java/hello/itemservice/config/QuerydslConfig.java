package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV3;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {
    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new ItemServiceV1(itemRepository);
    }

    @Bean
    public ItemRepository itemRepository(EntityManager em) {
        return new JpaItemRepositoryV3(em);
    }
}
