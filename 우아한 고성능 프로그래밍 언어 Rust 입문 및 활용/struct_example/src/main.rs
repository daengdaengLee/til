fn main() {
    let rect = Rectangle {
        width: 20,
        height: 30,
    };

    println!("해당 사각형의 면적은 {}.", area(&rect));
}

struct Rectangle {
    width: u32,
    height: u32,
}

fn area(rect: &Rectangle) -> u32 {
    rect.width * rect.height
}
