package com.sandro.servlet.web.frontcontroller.v3.controller;

import com.sandro.servlet.domain.member.Member;
import com.sandro.servlet.domain.member.MemberRepository;
import com.sandro.servlet.web.frontcontroller.ModelView;
import com.sandro.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save");
        mv.getModel().put("member", member);
        return mv;
    }
}
