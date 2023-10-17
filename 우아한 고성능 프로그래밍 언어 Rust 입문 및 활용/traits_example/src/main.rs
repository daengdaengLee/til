fn main() {
    let cat = Pet::Cat;
    let gildong = Person {
        name: String::from("홍길동"),
        active: true,
    };
    // 트레잇 바운드로 같은 타입으로 한정했기 때문에 Pet, Person 끼리 인사 못 함
    // meet(&cat, &gildong);
    // println!("==========");
    // meet(&gildong, &cat);

    // Greet 구현하고 있는 "같은" 타입끼리만 인사 가능
    meet(&cat, &Pet::Dog);
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

fn meet<T: Greet>(one: &T, another: &T) {
    println!("첫번째가 인사합니다 = {}", one.greeting());
    println!("두번째가 인사합니다 = {}", another.greeting());
}
