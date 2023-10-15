fn main() {
    let rect = (20, 30);

    println!("해당 사각형의 면적은 {}.", area(rect));
}

fn area(rect: (u32, u32)) -> u32 {
    rect.0 * rect.1
}
