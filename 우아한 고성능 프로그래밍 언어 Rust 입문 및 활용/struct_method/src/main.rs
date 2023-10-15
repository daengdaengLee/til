fn main() {
    let rect = Rectangle {
        width: 20,
        height: 30,
    };

    println!("이 사각형의 면적은 {} 입니다.", rect.area());
    // dbg!(rect); // 소유권 문제
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}

impl Rectangle {
    fn area(self: Self) -> u32 {
        self.width * self.height
    }
}
