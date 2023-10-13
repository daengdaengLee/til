fn main() {
    println!("헬로, 월드!");

    a_function();

    print_number(3);

    add_numbers(5, 7);

    let x = 3;
    // y = (x = 3); 불가능
    let y = {
        let x = 3;
        5 + x
    };
    println!("x = {x}");
    println!("y = {y}");

    let a = circle_area(2.0);
    println!("반지름이 2.0인 원의 면적은 {a}입니다.");
}

fn a_function() {
    println!("다른 함수입니다.");
}

fn print_number(x: i32) {
    println!("x = {x}");
}

fn add_numbers(a: i32, b: i32) {
    let sum = a + b;
    println!("a + b = {sum}");
}

const PI: f64 = 3.141592;

fn circle_area(radius: f64) -> f64 {
    println!("circle_area 가 받은 redius : {radius}");
    let r2 = radius * radius;
    PI * r2
}
