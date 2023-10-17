fn main() {
    let p1 = Point { x: 1, y: 2 };
    println!("p1.x() = {}", p1.x());

    let p2 = Point { x: "가", y: "A" };
    println!("p2.x() = {}", p2.x());
}

#[derive(Debug)]
struct Point<T> {
    x: T,
    y: T,
}

// 메소드 선언에 제네릭
impl<T> Point<T> {
    fn x(&self) -> &T {
        &self.x
    }
}