package com.sandro.login.web.member;

import com.sandro.login.domain.member.Member;
import com.sandro.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "members/addMemberForm";
        }

        Member savedMember = memberRepository.save(member);

        redirectAttributes.addAttribute("memberId", savedMember.getId());
        return "redirect:/";
    }
}
