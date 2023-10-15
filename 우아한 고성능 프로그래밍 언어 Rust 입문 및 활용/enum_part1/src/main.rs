fn main() {
    let red: Color = Color::Red;
    let green = Color::Green;

    println!("red = {:?}", red);
    println!("green = {:?}", green);

    println!("red == green : {}", red == green);
    println!("red == red : {}", red == Color::Red);
}

#[derive(Debug, PartialEq)]
enum Color {
    Red,
    Green,
    Blue,
}
