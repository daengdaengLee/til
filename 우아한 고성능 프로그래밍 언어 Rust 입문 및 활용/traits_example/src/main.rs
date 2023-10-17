use std::fmt::Debug;

fn main() {
    let cat = Pet::Cat;
    let gildong = Person {
        name: String::from("홍길동"),
        active: true,
    };
    meet(&cat, &Pet::Dog);
}

trait Greet {
    fn greeting(&self) -> String;
}

#[derive(Debug)]
enum Pet {
    Dog,
    Cat,
    Tiger,
}

impl Greet for Pet {
    fn greeting(&self) -> String {
        match self {
            Pet::Dog => String::from("멍멍"),
            Pet::Cat => String::from("야옹"),
            Pet::Tiger => String::from("어흥"),
        }
    }
}

struct Person {
    name: String,
    active: bool,
}

impl Greet for Person {
    fn greeting(&self) -> String {
        String::from("안녕")
    }
}

// 여러 트레잇 바운드 적용
fn meet<T: Greet + Debug>(one: &T, another: &T) {
    println!("{:?} 인사합니다 = {}", one, one.greeting());
    println!("{:?} 인사합니다 = {}", another, another.greeting());
}
