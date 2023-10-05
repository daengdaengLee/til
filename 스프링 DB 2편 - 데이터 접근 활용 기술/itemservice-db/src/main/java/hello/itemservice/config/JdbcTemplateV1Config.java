package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jdbctemplate.JdbcTemplateItemRepositoryV1;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateV1Config {
    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new ItemServiceV1(itemRepository);
    }

    @Bean
    public ItemRepository itemRepository(DataSource dataSource) {
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }
}
