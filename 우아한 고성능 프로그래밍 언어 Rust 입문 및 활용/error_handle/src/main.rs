use std::io::{Error, Read};
use std::fs::File;

fn main() {
    let username = read_username();
    println!("username = {:?}", username);
}

fn read_username() -> Result<String, Error> {
    let mut username = String::new();
    File::open("hello.txt")?.read_to_string(&mut username)?;
    Ok(username)
}
