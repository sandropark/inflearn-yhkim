package com.sandro.jpashop2.controller;

import com.sandro.jpashop2.domain.Member;
import com.sandro.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/new")
    public String createForm(Model model) {
        // validation 을 위해서 빈 객체를 넘겨준다.
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String createMember(@Valid MemberForm form, BindingResult result) {  // validation 기능
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member(form);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
