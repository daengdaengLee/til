fn main() {
    let x = Some(2);
    println!("{:?}", increment(x));
    println!("{:?}", increment(None));
}

fn increment(x: Option<i32>) -> Option<i32> {
    match x {
        Some(i) => Some(i + 1),
        None => None
    }
}
