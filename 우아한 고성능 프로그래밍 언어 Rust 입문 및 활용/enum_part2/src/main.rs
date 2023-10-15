fn main() {
    handle_message(&Message::StartGame);
    handle_message(&Message::WinPoint { who: String::from("홍길동") });
    handle_message(&Message::ChangePlayerName(String::from("둘리")));
}

enum Message {
    StartGame,
    WinPoint { who: String },
    ChangePlayerName(String),
}

fn handle_message(message: &Message) {
    match message {
        Message::StartGame => println!("게임시작!"),
        Message::WinPoint { who } => println!("{}의 득점", who),
        Message::ChangePlayerName(_) => println!("플레이어 이름변경 요청"),
    }
}
