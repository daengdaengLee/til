use std::fs::File;
use std::io::ErrorKind;

fn main() {
    let file = File::open("hello.txt");
    let file = match file {
        Ok(f) => Ok(f),
        Err(e) => match e.kind() {
            ErrorKind::NotFound => File::create("hello.txt"),
            _ => panic!("파일 접근 실패"),
        },
    };
}

// 기본 내장
// enum Result<T, E> {
//     OK(T),
//     Err(E),
// }
