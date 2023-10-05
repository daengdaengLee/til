package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * NamedParameterJdbcTemplate
 * ---
 * SqlParameterSource
 * - BeanPropertySqlParameterSource
 * - MapSqlParameterSource
 * Map
 * ---
 * BeanPropertyRowMapper
 */
@Slf4j
@Repository
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {
    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        var sql = """
                INSERT INTO item(item_name, price, quantity)
                VALUES(:itemName, :price, :quantity)""";
        var param = new BeanPropertySqlParameterSource(item);
        var keyHolder = new GeneratedKeyHolder();
        this.template.update(sql, param, keyHolder);

        var key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        item.setId(key);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        var sql = """
                UPDATE item 
                SET item_name = :itemName, price = :price, quantity = :quantity 
                WHERE id = :id""";
        var param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId); // 이 부분이 별도로 필요하다.
        this.template.update(sql, param);
    }

    @Override
    public Optional<Item> findById(Long id) {
        var sql = "SELECT id, item_name, price, quantity FROM item WHERE id = :id";
        try {
            var param = Map.of("id", id);
            var item = this.template.queryForObject(sql, param, this.itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        var itemName = cond.getItemName();
        var maxPrice = cond.getMaxPrice();

        var param = new BeanPropertySqlParameterSource(cond);

        var sql = "SELECT id, item_name, price, quantity FROM item";
        // 동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " WHERE";
        }

        var andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name LIKE CONCAT('%', :itemName, '%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " AND";
            }
            sql += " price <= :maxPrice";
        }

        log.info("sql={}", sql);
        return this.template.query(sql, param, this.itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        // camel 변환 지원
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
