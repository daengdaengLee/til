fn main() {
    let x = 3;
    if x % 2 == 0 {
        println!("x는 짝수입니다.");
    } else {
        println!("x는 홀수입니다.");
    }

    let x = 5;
    if x % 3 == 0 {
        println!("x는 3으로 나누어 떨아집니다.");
    } else if x % 3 == 1 {
        println!("x를 3으로 나눈 나머지는 1입니다.");
    } else {
        println!("x를 3으로 나눈 나머지는 2입니다.");
    }

    let condition = false;
    let y = if condition { 3 } else { 5 };
    println!("y = {y}");

    // 무한 루프
    // loop {
    //     println!("반복!");
    // }

    let mut counter = 0;
    let x = loop {
        println!("반복!");
        counter += 1;
        if counter == 3 {
            break counter;
        }
    };
    println!("x = {x}");

    let mut counter = 0;
    while counter < 3 {
        println!("반복!");
        counter += 1;
    }

    let xs = [1, 2, 3, 4, 5];
    let mut idx = 0;
    while idx < xs.len() {
        println!("xs[{idx}] = {}", xs[idx]);
        idx += 1;
    }
    println!("완료");

    let xs = [1, 2, 3, 4, 5];
    for x in xs {
        println!("x = {x}");
    }
    println!("완료");

    for i in 0..5 {
        println!("i = {i}");
    }
    println!("완료");

    let xs = [1, 2, 3, 4, 5];
    let l = xs.len();
    for i in (0..l).rev() {
        println!("xs[{i}] = {}", xs[i]);
    }
    println!("완료");
}