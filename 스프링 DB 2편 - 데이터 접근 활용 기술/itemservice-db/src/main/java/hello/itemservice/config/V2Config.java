package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV3;
import hello.itemservice.repository.v2.ItemQueryRepositoryV2;
import hello.itemservice.repository.v2.ItemRepositoryV2;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV2;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class V2Config {
    @Bean
    public ItemService itemService(
            ItemRepositoryV2 itemRepositoryV2,
            ItemQueryRepositoryV2 itemQueryRepositoryV2) {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2);
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepositoryV2(EntityManager em) {
        return new ItemQueryRepositoryV2(em);
    }

    @Bean
    public ItemRepository itemRepository(EntityManager em) {
        return new JpaItemRepositoryV3(em);
    }
}
