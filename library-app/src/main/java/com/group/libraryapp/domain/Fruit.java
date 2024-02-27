package com.group.libraryapp.domain;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @Column(nullable = false, length = 20)
    String name;

    @Column(nullable = false, name = "warehousing_date")// @Colume은 자동 매핑되기 때문에 생략도 가능
    LocalDate warehousingDate = LocalDate.of(1996, 12, 27);

    @Column(nullable = false)
    long price;

    @Column(nullable = false, name = "is_sale") // 명명 규칙
    boolean isSale = true;

    protected Fruit() {}

    public Fruit(String name, LocalDate warehousingDate, long price){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("이름이 null");
        }
        if (warehousingDate == null){
            throw new IllegalArgumentException("날짜가 null");
        }
        if (price < 0){
            throw new IllegalArgumentException("가격이 음수");
        }
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
    }

    public String getName(){
        return name;
    }
    public void updateIsSale(boolean isSale){
        this.isSale = isSale;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }

    public long getPrice() {
        return price;
    }

    public boolean isSale() {
        return isSale;
    }
}
