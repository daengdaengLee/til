fn main() {
    let some_number = Some(2);
    let absent_number: Option<i32> = None;

    println!("some_number: {:?}", some_number);
    println!("absent_number: {:?}", absent_number);
}

// 기본으로 존재
// enum Option<T> {
//     None,
//     Some(T),
// }
