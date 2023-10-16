use std::fs::File;

fn main() {
    let file: File = File::open("hello.txt").expect("파일을 열 수 없음");
}

// 기본 내장
// enum Result<T, E> {
//     OK(T),
//     Err(E),
// }
