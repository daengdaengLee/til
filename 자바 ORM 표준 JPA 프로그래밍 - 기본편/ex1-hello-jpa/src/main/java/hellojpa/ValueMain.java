package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        var a = 10;
        var b = 10;

        System.out.println("a == b: " + (a == b));

        var address1 = new Address("city", "street", "zipcode");
        var address2 = new Address("city", "street", "zipcode");
        System.out.println("address1 == address2: " + (address1 == address2));
        System.out.println("address1 equals address2: " + address1.equals(address2));
    }
}
