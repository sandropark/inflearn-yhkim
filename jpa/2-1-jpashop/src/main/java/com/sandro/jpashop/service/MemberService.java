package com.sandro.jpashop.service;

import com.sandro.jpashop.domain.Member;
import com.sandro.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/* JPA에서 데이터의 변경은 trasactional 안에서 이뤄져야 한다.
    조회 로직에서는 readOnly = true를 걸어주는 게 성능 최적화에 좋다.
    데이터를 변경하는 로직은 따로 transactional을 걸어준다.
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor    // final인 필드만 생성자로 생성한다.
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    /**
     save를 하면 em에서 해당 객체를 PersistenceContext로 올린다.
     PC는 key, value로 이루어 져있다.
     이때 key는 해당 객체의 pk로 잡는다.
     객체에 값도 생성해준다.
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> foundMember = memberRepository.findByName(member.getName());
        if (!foundMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}

