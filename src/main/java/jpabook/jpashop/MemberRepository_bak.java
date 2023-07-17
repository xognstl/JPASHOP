package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository_bak {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member_bak member) {
        em.persist(member);
        return member.getId();
    }

    public Member_bak find(Long id) {
        return em.find(Member_bak.class, id);
    }
}
