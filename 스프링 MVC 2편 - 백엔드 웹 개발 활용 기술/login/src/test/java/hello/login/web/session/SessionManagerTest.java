package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        // 세션 생성
        var response = new MockHttpServletResponse();
        var member = new Member();
        this.sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장
        var request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        var result = this.sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        this.sessionManager.expire(request);
        var expired = this.sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}