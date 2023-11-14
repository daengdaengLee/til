package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item2;

@SpringBootTest
class Item2RepositoryTest {
    @Autowired
    Item2Repository item2Repository;

    @Test
    void save() {
        var item2 = new Item2("A");
        item2Repository.save(item2);
    }
}