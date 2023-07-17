package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //스프링 빈 등록
@RequiredArgsConstructor //final 만
public class MemberRepository {

//    @PersistenceContext // EntityManager 를 주입해준다.
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 올리고, transaction이 commit되면 insert
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
        //단건조회 (타입, pk)
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //jpql 은 DB 가아닌 entity 를 조회
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }
}
