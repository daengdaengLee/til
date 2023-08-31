package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        var hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // 체크
        var hasMemberType = parameter.getParameterType().isAssignableFrom(Member.class);

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        log.info("resolveArgument 실행");

        Object nativeRequest = webRequest.getNativeRequest();
        if (!(nativeRequest instanceof HttpServletRequest request)) {
            return null;
        }
        var session = request.getSession(false);
        if (session == null) {
            return null;
        }

        var member = session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (!(member instanceof Member)) {
            return null;
        }

        return member;
    }
}
