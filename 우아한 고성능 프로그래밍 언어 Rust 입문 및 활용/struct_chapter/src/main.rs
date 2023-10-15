fn main() {
    let mut user = build_user(
        String::from("홍길동"),
        String::from("gildong@example.com"));

    user.email = String::from("gd.hong@example.com");

    println!("이용자의 이메일 = {}", user.email);
}

struct User {
    name: String,
    email: String,
    active: bool,
}

fn build_user(name: String, email: String) -> User {
    User {
        name, // name: name 축약
        email, // email: email 축약
        active: true,
    }
}
