fn main() {
    let mut rect = Rectangle {
        width: 20,
        height: 30,
    };

    println!("이 사각형의 면적은 {} 입니다.", rect.area());
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}

impl Rectangle {
    // 가변 임대
    fn area(self: &mut Self) -> u32 {
        self.width * self.height
    }
}
