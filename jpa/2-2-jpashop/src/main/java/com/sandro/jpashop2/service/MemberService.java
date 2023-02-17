package com.sandro.jpashop2.service;

import com.sandro.jpashop2.domain.Member;
import com.sandro.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)     /* 조회만 하는 경우 최적화된다.
                                    기본값으로 해두고 데이터를 변경하는 곳에서만 @transaction 을 따로 적용한다. */
@RequiredArgsConstructor            // 생성자 주입
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional  // 데이터 변경은 transaction 안에서 이뤄져야 한다.
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.listAll();
    }

    // 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member) {
        /* 중복회원이라면 예외발생
        이렇게 해도 멀티쓰레드로 동시에 같은 이름의 회원이 가입을 하면 이 로직을 통과할 수 있다.
        확실히 하기 위해서는 DB에서 회원명을 유니크로 설정한다.
         */
        List<Member> members = memberRepository.findAllByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalArgumentException("같은 이름의 회원이 존재합니다.");
        }
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.updateName(name);
    }
}
