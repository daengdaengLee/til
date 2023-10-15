// 튜플과 비슷하지만 Color, Point 가 서로 명확하게 구분됨.
// 서로 바뀔 걱정이 없음
fn main() {
    let color = Color(1, 2, 3);
    let point = Point(1, 2, 3);

    println!("color {} {} {}", color.0, color.1, color.2);
    println!("point {} {} {}", point.0, point.1, point.2);
}

struct Color(i32, i32, i32);

struct Point(i32, i32, i32);
