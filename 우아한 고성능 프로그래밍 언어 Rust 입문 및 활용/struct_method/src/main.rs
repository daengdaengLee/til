fn main() {
    let square = Rectangle::square(20);
    println!("정사각형 = {:?}", square);
    println!("정사각형 넓이 = {}", square.area());
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}

// impl 블록 여러개 있어도 OK
impl Rectangle {
    fn square(size: u32) -> Rectangle {
        Rectangle {
            width: size,
            height: size,
        }
    }
}

impl Rectangle {
    fn area(&self) -> u32 {
        self.width * self.height
    }
}
