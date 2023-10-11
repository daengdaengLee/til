package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    public void joinV1(String username) {
        var member = new Member(username);
        var logMessage = new Log(username);

        log.info("=== MemberRepository 호출 시작 ===");
        this.memberRepository.save(member);
        log.info("=== MemberRepository 호출 종료 ===");

        log.info("=== LogRepository 호출 시작 ===");
        this.logRepository.save(logMessage);
        log.info("=== LogRepository 호출 종료 ===");
    }

    public void joinV2(String username) {
        var member = new Member(username);
        var logMessage = new Log(username);

        log.info("=== MemberRepository 호출 시작 ===");
        this.memberRepository.save(member);
        log.info("=== MemberRepository 호출 종료 ===");

        log.info("=== LogRepository 호출 시작 ===");
        try {
            this.logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("log 저장에 실패했습니다. logMessage = {}", logMessage.getMessage());
            log.info("정상 흐름 반환");
        }
        log.info("=== LogRepository 호출 종료 ===");
    }
}
