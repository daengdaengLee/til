fn main() {
    let user1 = User {
        name: String::from("홍길동"),
        email: String::from("gildong@example.com"),
        active: true,
    };

    let user2 = User {
        active: false,
        ..user1
    };

    println!("user2.email = {}", user2.email);
    // println!("user1.email = {}", user1.email); // 소유권 이전으로 사용 불가
}

struct User {
    name: String,
    email: String,
    active: bool,
}
