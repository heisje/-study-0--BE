package com.group.libraryapp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FruitJPARepository extends JpaRepository<Fruit, Long> {
        @Query("SELECT SUM(f.price) FROM Fruit f WHERE f.name = :name AND f.isSale = :isSale")
        Optional<Long> sumPriceByNameAndIsSale(@Param("name") String name, boolean isSale);

        Long countByName(String name);

        @Query("SELECT f FROM Fruit f WHERE f.isSale = TRUE AND f.price > :price")
        List<Fruit> findByPriceGTEAndIsSaleIsTrue(@Param("price") long price);

        @Query("SELECT f FROM Fruit f WHERE f.isSale = TRUE AND f.price < :price")
        List<Fruit> findByPriceLTEAndIsSaleIsTrue(@Param("price") long price);

}
