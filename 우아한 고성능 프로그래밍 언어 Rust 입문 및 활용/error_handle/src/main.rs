use std::io::{Error, Read};
use std::fs::File;

fn main() {
    let username = read_username().unwrap();
    println!("username = {}", username);
}

fn read_username() -> Result<String, Error> {
    let file_result = File::open("hello.txt");

    let mut file = match file_result {
        Ok(file) => file,
        Err(e) => return Err(e),
    };

    let mut username = String::new();

    match file.read_to_string(&mut username) {
        Ok(_) => Ok(username),
        Err(e) => Err(e),
    }
}
