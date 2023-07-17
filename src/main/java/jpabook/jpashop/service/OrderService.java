package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 파라미터 주문회원 ID, 상품 ID, 주문수량
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        /*
        * Order 안의 orderItem, delivery cascade 가 ALL이기 때문에 order를 persist 하면 나머지도 같이된다.
        * Order 만 orderItem , delivery 를 사용한다. persist를 해야하는 라이프사이클이 똑같기 떄문에 cascade사용
        * 참조하는게 다른곳에 있으면 안쓰는게 좋다.
        * */

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /** 검색 */
    /*
 public List<Order> findOrders(OrderSearch orderSearch) {
 return orderRepository.findAll(orderSearch);
 }
*/
}
