fn main() {
    let numbers = vec![3, 4, 1, 6, 8, 10];
    let chars = vec!['홍', '길', '동'];

    let result = smallest_i32(&numbers);
    println!("가장 작은 수는 {}", result);

    let result = smallest_char(&chars);
    println!("가장 작은(?) 글자는 {}", result);

    println!("==========");

    let result = smallest(&numbers);
    println!("가장 작은 수는 {}", result);

    let result = smallest(&chars);
    println!("가장 작은(?) 글자는 {}", result);

    println!("==========");

    let result = smallest(&["홍길동", "둘리", "도우너"]);
    println!("가장 작은(?) 이름은 {}", result);
}

fn smallest_i32(list: &[i32]) -> &i32 {
    let mut smallest = &list[0];

    for item in list {
        if item < smallest {
            smallest = item;
        }
    }

    smallest
}

fn smallest_char(list: &[char]) -> &char {
    let mut smallest = &list[0];

    for item in list {
        if item < smallest {
            smallest = item;
        }
    }

    smallest
}

// 제네릭 함수로 통합
fn smallest<T: PartialOrd>(list: &[T]) -> &T {
    let mut smallest = &list[0];

    for item in list {
        if item < smallest {
            smallest = item;
        }
    }

    smallest
}
