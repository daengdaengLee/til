fn main() {
    let red = color_to_rgb(Color::Red);
    println!("red rgb = {:?}", red);
}

#[derive(Debug)]
enum Color {
    Red,
    Green,
    Blue,
}

#[derive(Debug)]
struct RGB(u8, u8, u8);

fn color_to_rgb(color: Color) -> RGB {
    match color {
        Color::Red => RGB(255, 0, 0),
        Color::Green => RGB(0, 255, 0),
        Color::Blue => RGB(0, 0, 255),
    }
}
