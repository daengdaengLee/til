use std::io::{Error, Read};
use std::fs::File;

fn main() {
    let username = read_username().unwrap();
    println!("username = {}", username);
}

fn read_username() -> Result<String, Error> {
    let mut file = File::open("hello.txt")?;
    let mut username = String::new();
    file.read_to_string(&mut username)?;
    Ok(username)
}
