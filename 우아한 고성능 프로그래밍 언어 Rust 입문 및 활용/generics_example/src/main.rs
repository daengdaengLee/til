fn main() {}

// enum 에 제네릭 선언
// 기본으로 선언되어 있음

enum Option<T> {
    Some(T),
    None,
}

enum Result<T, E> {
    Ok(T),
    Err(E),
}
