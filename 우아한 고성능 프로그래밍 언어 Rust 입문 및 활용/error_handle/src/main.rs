use std::fs::File;

fn main() {
    let file: File = File::open("hello.txt").unwrap();
}

// 기본 내장
// enum Result<T, E> {
//     OK(T),
//     Err(E),
// }
