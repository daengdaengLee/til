package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jdbctemplate.JdbcTemplateItemRepositoryV3;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateV3Config {
    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new ItemServiceV1(itemRepository);
    }

    @Bean
    public ItemRepository itemRepository(DataSource dataSource) {
        return new JdbcTemplateItemRepositoryV3(dataSource);
    }
}
