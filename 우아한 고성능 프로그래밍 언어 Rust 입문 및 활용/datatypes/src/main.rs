fn main() {
    let t: (i32, bool, f64) = (32, true, 1.41); // 타입 선언 생략 가능
    let (x, y, z) = t;
    println!("x = {x}");
    println!("y = {y}");
    println!("z = {z}");

    let x = t.0;
    let y = t.1;
    let z = t.2;
    println!("x = {x}");
    println!("y = {y}");
    println!("z = {z}");

    // 인덱스 벗어나면 컴파일러 오류
    // let w = t.3;

    // 같은 타입만 담음 수 있다.
    // 타입 선언 생략 가능
    // 길이 고정
    let arr: [i32; 5] = [1, 2, 3, 4, 5];
    let x0 = arr[0];
    let x4 = arr[4];
    println!("x[0] = {x0}");
    println!("x[4] = {x4}");

    let threes = [3; 100]; // 특정 값을 정해진 개수만큼 초기화
    let last = threes[99];
    println!("last = {last}");

    let hellos = ["헬로"; 10];
    println!("hellos = {:?}", hellos);
}
