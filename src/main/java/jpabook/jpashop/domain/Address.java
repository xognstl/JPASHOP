package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        //Setter 를 안해서 변경이 불가 . 새로 등록만 가능.
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
