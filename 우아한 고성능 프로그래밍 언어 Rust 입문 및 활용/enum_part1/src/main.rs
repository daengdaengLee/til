fn main() {
    let m1 = Message::StartGame;
    let m2 = Message::WinPoint { who: String::from("홍길동") };
    let m3 = Message::ChangePlayerName(String::from("둘리"));

    println!("m1 = {:?}", m1);
    println!("m2 = {:?}", m2);
    println!("m3 = {:?}", m3);
}

#[derive(Debug, PartialEq)]
enum Message {
    StartGame,
    WinPoint { who: String },
    ChangePlayerName(String),
}
