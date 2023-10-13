const PI: f32 = 3.141592;

fn main() {
    let mut x = 3; // 기본은 불변
    println!("x의 값은 {x}입니다.");
    x = 7;
    println!("x의 값은 {x}입니다.");

    // 상수
    println!("PI상수값은 {PI}입니다.");

    // shadowing
    let y = 3;
    println!("y의 값은 {y}입니다.");
    let y = y + 1;
    println!("y의 값은 {y}입니다.");
    {
        let y = y * 2;
        println!("안쪽 범위에서 y의 값은 {y}입니다.");
    }
    println!("y의 값은 {y}입니다.");
}
