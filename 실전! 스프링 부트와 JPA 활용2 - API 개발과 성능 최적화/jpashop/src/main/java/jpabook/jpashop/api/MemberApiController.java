package jpabook.jpashop.api;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member) {
        var id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        var member = new Member();
        member.setName(request.getName());

        var id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @Data
    public static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @AllArgsConstructor
    @Data
    public static class CreateMemberResponse {
        private Long id;
    }
}
