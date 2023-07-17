package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/*
* 테스트 요구사항
* 1. 회원가입 성공
* 2. 회원가입 할 떄 중복되는 이름이 있으면 예외 발생
* */
@RunWith(SpringRunner.class) // 스프링과 테스트 통합
@SpringBootTest // 스프링 부트 띄우고 테스트 //이두가지가 있어야 스프링과 완전 연계해서 테스트 가능
@Transactional // 테스트 끝나고 트랙잭션 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Rollback(false)  // 이것을 넣으면 insert 가된다. 안넣으면 영속성 컨텍스트에만 올라가고 transaction 에서 롤백을 시킴
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);    // 예외 발생 해야함.

        //then
        fail("예외가 발생해야 한다.");   // 예외가 안뜨면 에러남.
    }
}