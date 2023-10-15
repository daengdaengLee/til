fn main() {
    let mut user = User {
        name: String::from("홍길동"),
        email: String::from("gildong@example.com"),
        active: true,
    };

    user.email = String::from("gd.hong@example.com");

    println!("이용자의 이메일 = {}", user.email);
}

struct User {
    name: String,
    email: String,
    active: bool,
}
