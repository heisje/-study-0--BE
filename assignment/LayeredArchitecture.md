# 문제: 기존 코드 Layered Architecture로 분리하기

1. Controller - Service - Repository로 분리하기
2. FruitRepository를 FruitSqlRepository와 FruitMemoryRepository로 분리 후 @Primary나 @Qualifier로 매핑하기

# 제출 코드

### [레포지토리 바로가기](../library-app/src/main/java/com/group/libraryapp/)

# 풀이 과정

- 스프링빈이 자동으로 생성해주는 것을 이용해 생성자로 자동 매핑시켰다.
- interface를 사용해 두개의 레포지토리를(memory, sql)을 한가지 워딩(`FruitRepository`)로 작성할 수 있다는 것 유념하기로 했다. 응용할 곳이 많을 것 같다!
- 어노테이션을 활용한 의존성 주입을 중심으로 코드를 작성했다.

### Controller

```java
package com.group.libraryapp.controller;

@RestController
@RequestMapping("/api/v2")
public class FruitController {

    JdbcTemplate jdbcTemplate;
    FruitService fruitService;

    public FruitController(JdbcTemplate jdbcTemplate, FruitService fruitService) {
        this.jdbcTemplate = jdbcTemplate;
        this.fruitService = fruitService;
    }

    @PostMapping("/fruit")
    public void saveFruit(@RequestBody FruitRequest request){
        fruitService.saveFruit(request);
    }

    @PutMapping("/fruit")
    public void sellFruit(@RequestParam long id){
        fruitService.sellFruit(id);
    }

    @GetMapping("/fruit")
    public Map<String, Long> getFruit(@RequestParam String name){
        return fruitService.findFruit(name);
    }

}

```

### Service

```java
package com.group.libraryapp.service;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(@Qualifier("sql") FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public void saveFruit(FruitRequest request){
        fruitRepository.saveFruit(request.getName(), request.warehousingDate(), request.getPrice());
    }

    public void sellFruit(long id){
        if (fruitRepository.isExistFruit(id)){
            throw new IllegalArgumentException();
        }
        fruitRepository.sellFruit(id);
    }

    public Map<String, Long> findFruit(String name){
        long salesAmount = fruitRepository.calculateSalesAmount(name);
        long notSalesAmount = fruitRepository.calculateNotSalesAmount(name);
        Map<String, Long> response = new HashMap<>();
        response.put("salesAmount", salesAmount);
        response.put("notSalesAmount", notSalesAmount);

        return response;
    }
}
```

## Repository

### FruitRepository

```java
package com.group.libraryapp.repository;

public interface FruitRepository {

    boolean isExistFruit(long id);

    void saveFruit(String name, LocalDate warehousingDate, long price);

    void sellFruit(long id);

    long calculateSalesAmount(String name);

    long calculateNotSalesAmount(String name);
}
```

### FruitSqlRepository

```java
package com.group.libraryapp.repository;

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
        String sql = "INSERT INTO fruit (name, warehousingDate, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, warehousingDate, price);
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
```
