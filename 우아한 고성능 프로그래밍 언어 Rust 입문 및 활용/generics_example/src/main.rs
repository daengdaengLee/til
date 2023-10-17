fn main() {
    let p1: Point<i32> = Point { x: 2, y: 3 };
    let p2: Point<f64> = Point { x: 5.0, y: 1.0 };
    // x, y 는 같은 타입으로 선언했기 때문에 불가능
    // let p3 = Point { x: 2, y: 3.0 };
    println!("p1 = {:?}, p2 = {:?}", p1, p2);
}

// struct 에 제네릭 선언
#[derive(Debug)]
struct Point<T> {
    x: T,
    y: T,
}
