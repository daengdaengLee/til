package jpabook.jpashop.api;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberResponse>> membersV2() {
        var findMembers = memberService.findMembers().stream()
                .map(m -> new MemberResponse(m.getName()))
                .toList();
        return new Result<>(findMembers);
    }

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Validated UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        var findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @AllArgsConstructor
    @Data
    public static class Result<T> {
        private T data;
    }

    @AllArgsConstructor
    @Data
    public static class MemberResponse {
        private String name;
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

    @Data
    public static class UpdateMemberRequest {
        private String name;
    }

    @AllArgsConstructor
    @Data
    public static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
