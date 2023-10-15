fn main() {
    let width = 20;
    let height = 30;

    println!("해당 사각형의 면적은 {}.", area(width, height));
}

fn area(width: u32, height: u32) -> u32 {
    width * height
}
