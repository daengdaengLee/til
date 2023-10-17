fn main() {
    let cat = Pet::Cat;
    let gildong = Person {
        name: String::from("홍길동"),
        active: true,
    };
    // 트레잇 바운드를 2개의 다른 제네릭에 각각 걸었기 때문에 서로 다른 타입이어도 인사 가능
    meet(&cat, &gildong);
    println!("==========");
    meet(&gildong, &cat);
}

trait Greet {
    fn greeting(&self) -> String;
}

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

fn meet<T: Greet, U: Greet>(one: &T, another: &U) {
    println!("첫번째가 인사합니다 = {}", one.greeting());
    println!("두번째가 인사합니다 = {}", another.greeting());
}
