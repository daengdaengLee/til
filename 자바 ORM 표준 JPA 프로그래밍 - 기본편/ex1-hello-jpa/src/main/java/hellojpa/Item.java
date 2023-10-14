package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn // SINGLE_TABLE 전략이면 DiscriminatorColumn 애노테이션 없어도 자동으로 생김. 항상 써두는 게 운영상 좋음
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
