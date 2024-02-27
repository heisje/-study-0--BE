package com.group.libraryapp.repository;

import com.group.libraryapp.domain.Fruit;
import com.group.libraryapp.dto.FruitResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


//@Primary
@Qualifier("sql")
@Repository
public class FruitSqlRepository implements FruitRepository {

    JdbcTemplate jdbcTemplate;

    public FruitSqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean isExistFruit(long id){
        String readSql = "SELECT * FROM fruit WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void saveFruit(String name, LocalDate warehousingDate, long price){
        String sql = "INSERT INTO fruit (name, warehousing_date, price, is_sale) VALUES (?, ?, ?, TRUE)";
        jdbcTemplate.update(sql, name, warehousingDate, price);
    }

    public List<FruitResponse> getFruits(){
        String sql = "SELECT * FROM fruit";

        return jdbcTemplate.query(sql, new RowMapper<FruitResponse>() {
            @Override
            public FruitResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

                return new FruitResponse(new Fruit(rs.getString("name"), rs.getDate("warehousing_date").toLocalDate(),rs.getLong("price")));
            }
        });
    }

    public void sellFruit(long id){
        String sql = "UPDATE fruit set is_sale = false where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public long calculateSalesAmount(String name) {
        String sql = "SELECT SUM(price) FROM fruit WHERE name = ? AND is_sale = true";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Long.class, name))
                .orElse(0L);

    }

    public long calculateNotSalesAmount(String name) {
        String sql = "SELECT SUM(price) FROM fruit WHERE name = ? AND is_sale = false";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Long.class, name))
                .orElse(0L);
    }
}
