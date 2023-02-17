package com.sandro.jpashop2.api;

import com.sandro.jpashop2.domain.Member;
import com.sandro.jpashop2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController     // @Controller 와 @ResponseBody 가 합쳐진 애노테이션이다. API 컨트롤러에서 쓰인다.
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/v1/members")     // 엔티티를 그대로 사용
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/v2/members")     // DTO 사용
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMember(@RequestBody @Valid UpdateMemberRequest request, @PathVariable Long id) {
        memberService.update(id, request.getName());
        Member foundMember = memberService.findOne(id);
        return new UpdateMemberResponse(foundMember.getId(), foundMember.getName());
    }

    @GetMapping("/v1/members")
    public List<Member> membersV1() {
        return List.of(memberService.findOne(15L));
    }

    @GetMapping("/v2/members")
    public Result membersV2() {
        List<MemberDto> memberDtos = memberService.findMembers().stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(memberDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data       // 등록 요청 DTO
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data       // 등록 응답 DTO
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data       // 수정 요청 DTO
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data       // 수정 응답 DTO
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

}
