package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            var member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street1", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street1", "10000"));
            member.getAddressHistory().add(new Address("old2", "street1", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==========");

            var findMember = em.find(Member.class, member.getId());
            var addressHistory = findMember.getAddressHistory();
            for (var address : addressHistory) {
                System.out.println("address.city = " + address.getCity());
            }
            var favoriteFoods = findMember.getFavoriteFoods();
            for (var favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            System.out.println("==========");

            // homeCity -> newCity
            var homeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 쿼리 잘 확인
            // 전부 다 지우고 현재 상태로 다시 insert 함
            // 실무에서는 값 타입 컬렉션 대신 1:N 관계를 고려
            findMember.getAddressHistory().remove(new Address("old1", "street1", "10000"));
            findMember.getAddressHistory().add(new Address("new1", "street1", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
