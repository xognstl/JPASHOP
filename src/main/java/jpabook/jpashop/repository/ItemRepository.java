package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){   //신규
            em.persist(item);   // 처음 저장할떄는 아이디가 없다.
        }else { // 수정
            em.merge(item); //merge == update 정도로 생각하면 된다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    // 단건은 find 를 쓰면 되지만 여러개 찾는거는 jpql 을 작성해야한다.
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
