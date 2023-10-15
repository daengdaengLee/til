fn main() {
    let user = User {
        name: String::from("홍길동"),
        email: String::from("gildong@example.com"),
        active: true,
    };

    println!("이용자의 이름은 = {}", user.name);
}

struct User {
    name: String,
    email: String,
    active: bool,
}
