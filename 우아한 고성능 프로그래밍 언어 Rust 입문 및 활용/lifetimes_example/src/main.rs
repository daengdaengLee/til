fn main() {
    ok_lifetime();

    let s1 = String::from("가나다");
    let s2 = "하나둘셋";

    let res = longest(s1.as_str(), s2);
    println!("더 긴 문자열은 {}", res);

    let s1 = String::from("가나다");
    {
        let s2 = "하나둘셋";

        let res = longest(s1.as_str(), s2);
        println!("이번에도 더 긴 문자열은 {}", res);
    }

    let s1 = String::from("가나다라마바사");
    {
        let s2 = String::from("하나둘셋");

        let res = longest(s1.as_str(), s2.as_str());
        println!("이번에도 더 긴 문자열은 {}", res);
    }

    // 임대 체커에서 실패 -> s2 의 수명이 s1, res 의 수명보다 짧아짐
    // let s1 = String::from("가나다라마바사");
    // let res: &str;
    // {
    //     let s2 = String::from("하나둘셋");
    //
    //     res = longest(s1.as_str(), s2.as_str());
    // }
    // println!("이번에도 더 긴 문자열은 {}", res);
}

// x 에 y 의 임대값을 넣음
// 하지만 y 의 수명이 x 보다 짧기 때문에 임대 검사에서 에러 발생
// fn short_lifetime() {
//     let x;
//
//     {
//         let y = 5;
//         x = &y;
//     }
//
//     println!("x = {}", x);
// }

fn ok_lifetime() {
    let y = 5;

    let x = &y;

    println!("x = {}", x);
}

fn longest<'a>(s1: &'a str, s2: &'a str) -> &'a str {
    if s1.len() > s2.len() {
        s1
    } else {
        s2
    }
}

struct ImportantPart<'a> {
    part: &'a str,
}

fn lifetime_in_struct() {
    let sentences = String::from("안녕하세요. 러스트의 참조 수명에 대해 알아볼게요.");
    let first_sentence = sentences
        .split(".")
        .next()
        .expect("마침표 '.' 를 찾을 수 없어요.");
    let i = ImportantPart {
        part: first_sentence,
    };
}

impl<'a> ImportantPart<'a> {
    fn notice(&self, text: &str) -> &str {
        println!("공지사항: {}", text);
        self.part
    }
}

fn static_lifetime() {
    let s: &'static str = "프로그램 실행 중 내내 유효한 수명";
}