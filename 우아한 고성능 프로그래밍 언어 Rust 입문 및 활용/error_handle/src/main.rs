use std::fs;
use std::io::Error;

fn main() {
    let username = read_username();
    println!("username = {:?}", username);
}

fn read_username() -> Result<String, Error> {
    fs::read_to_string("hello.txt")
}
