package com.sandro.jpashop.controller;

import com.sandro.jpashop.domain.Address;
import com.sandro.jpashop.domain.Member;
import com.sandro.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createFrom(Model model) {
        log.info("createForm");
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        /** @Valid : 객체에 지정해둔 validation 조건을 자동으로 검증해준다.
         * BindingResult : Valid에서 예외발생시 예외를 핸들링하는 객체
         *
         * Member 객체를 바로 사용해도 되지만 MemberForm 객체를 사용하는 이유는
         * 입력을 받을 때와 DB에 저장할 때 검증해야 하는 로직이 다를 수도 있는 것처럼
         * 미묘하게 필요한 로직이 다를 수 있다. 때문에 비슷하지만 다른 객체를 사용한다.
         */

        if (result.hasErrors()) {
            // BindingResult 객체도 함께 넘어간다.
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        /**
         * 여기서 화면에 데이터를 넘기기 위해서 엔티티를 그대로 사용했지만 더 복잡한 로직에서는
         * 엔티티를 바로 사용하기 보다는 DTO등을 사용해서 필요한 데이터만 넘겨야 한다.
         */
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}
