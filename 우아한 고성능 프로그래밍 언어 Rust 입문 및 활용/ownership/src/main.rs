fn main() {
    // 블록 안에서면 변수 유효
    {
        let s = "헬로";
        println!("s = {}", s);
    }
    // println!("s = {}", s); // s 에 접근 불가

    // 문자열 리터럴, &str 타입
    let s = "헬로";
    println!("s = {}", s);

    // 문자열 리터럴 -> String 타입
    let s: String = String::from("헬로");
    println!("s = {}", s);

    // 문자열 리터럴은 불변, String 은 가변
    let mut s: String = String::from("헬로");
    s.push_str(", 월드!");
    println!("s = {}", s);

    {
        let x = 3;
        let y = x; // 기본 데이터 타입은 Copy 로 동작함. Stack 에서 관리. 소유권 문제 안 생김
        println!("x = {x}, y = {y}");
    }

    {
        let s1 = String::from("헬로"); // Heap
        println!("s1 = {}", s1);
        let s2 = s1;
        println!("s2 = {}", s2);
        // println!("s1 = {}", s1); // owner 가 s2 로 바뀌었기 때문에 s1 에서 접근 불가능
    } // 블럭이 끝날 때 s2 의 생명주기가 끝남 -> 문자열 데이터도 메모리에서 해제

    {
        let s1 = String::from("헬로"); // Heap
        println!("s1 = {}", s1);
        let s2 = s1.clone();
        println!("s1 = {}", s1); // Heap 메모리 데이터까지 모두 복사해버린 경우
        println!("s2 = {}", s2);
    }

    {
        let s = String::from("헬로");
        string_length(s); // 함수로 s 소유권 넘어감
        // println!("s = {}", s); // 소유권 없어서 실행 불가
    }

    {
        let x = 3;
        double(x); // 기본 타입은 Copy 로 처리. 소유권 문제 없음
        println!("x = {}", x);
    }

    {
        let s1 = String::from("헬로");
        let s2 = string_length2(s1); // 함수 내부로 소유권 넘겼다가, 리턴받는 순간 다시 소유권 받아옴
        println!("s1 = {}", s2);
    }

    // 불필요한 소유권 이동 문제
    {
        let s = String::from("헬로");
        let (len, s) = string_length3(s);
        println!("문자열 s: {} 의 길이는: {}", s, len);
    }

    // 불필요한 소유권 이동 문제 해결 - 임대
    {
        let s = String::from("헬로");
        let len = string_length4(&s);
        println!("문자열 s: {} 의 길이는: {}", s, len);
    }

    // 참조는 기본으로 불변, 가변 참조는 mut 키워드로 표시
    {
        let mut s = String::from("헬로");
        append_word(&mut s);
        println!("s = {}", s);
    }

    // 가변 참조가 존재하면 다른 참조(가변, 불변 모두)는 못 쓴다.
    // 데이터 레이스 컨디션 방지하기 위해
    {
        let mut s = String::from("헬로");

        let r1 = &mut s; // mut 참조 1
        let r2 = &mut s; // mut 참조 2

        // 컴파일 에러
        // println!("r1 = {}, r2 = {}", r1, r2);
    }

    {
        let mut s = String::from("헬로");

        {
            let r1 = &mut s; // mut 참조 1
            println!("r1 = {}", r1);
        } // 여기서 r1 생명 주기가 끝나서 아래에서 r2 사용에 문제 없음
        let r2 = &mut s; // mut 참조 2
        println!("r2 = {}", r2);
    }

    {
        let mut s = String::from("헬로");

        let r1 = &s; // 불변 참조 1
        let r2 = &s; // 불변 참조 2
        println!("r1 = {}, r2 = {}", r1, r2);

        let r3 = &mut s; // 가변 참조
        // 컴파일 에러
        // r1, r2 를 여기까지 쓰면 생명 주기가 겹치기 때문
        // println!("r1 = {}, r2 = {}, r3 = {}", r1, r2, r3);
        // 생명 주기가 안 겹치면 OK. 러스트 컴파일러가 단순히 블록만 체크하지 않고 실제 사용되는 범위를 체크하기 때문
        println!("r3 = {}", r3);
    }

    // 문자열 슬라이스 &str
    {
        let s = String::from("헬로 월드");
        let len = s.len();

        let hello: &str = &s[0..6];
        println!("hello = {}", hello);
        let hello: &str = &s[..6];
        println!("hello = {}", hello);

        let world: &str = &s[7..13];
        println!("world = {}", world);
        let world: &str = &s[7..len];
        println!("world = {}", world);
        let world: &str = &s[7..];
        println!("world = {}", world);

        let all = &s[..];
        println!("all = {}", all);
        let all: &str = &s;
        println!("all = {}", all);


        let s = String::from("헬로 월드");
        let word = first_word(&s);
        println!("word = {}", word);
    }

    // 문자열 말고 다른 슬라이스
    {
        let a = [1, 2, 3, 4, 5];
        let slice = &a[1..3];
        println!("a = {:?}, slice = {:?}", a, slice);
    }
}

fn string_length(s: String) {
    println!("문자열 s의 길이는: {}", s.len());
}

fn double(x: i32) {
    println!("x = {}", x);
}

fn string_length2(s: String) -> String {
    println!("문자열 s의 길이는: {}", s.len());
    s
}

fn string_length3(s: String) -> (usize, String) {
    println!("s = {}", s);
    (s.len(), s)
}

fn string_length4(s: &String) -> usize {
    let length = s.len();
    length
}

fn append_word(s: &mut String) {
    s.push_str(", 월드!");
}

// &String 인자 타입보다는 &str 타입으로.
// 어처피 &str 자리에 &String 타입 값을 넣어도 호환됨
fn first_word(s: &str) -> &str {
    let bytes = s.as_bytes();

    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[..i];
        }
    }

    &s[..]
}
