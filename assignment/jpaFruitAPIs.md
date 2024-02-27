# 7일차 문제 
1. 기존 JDBC코드를 JPA로 변경하기
2. [API작성] 쿼리 name을 주고 같은 과일 개수 반환하기
3. [API작성] 판매되지 않은 과일 중 주어진 금액보다 작거나 큰 List<Fruit> 반환

# 문제풀이
1. 우선 jdbc코드를 jpa로 변경하기 위해 기존에 있던 `fruitService`를 세개로 분리했다.
  인터페이스 `fruitService`를 작성하고, `fruitJPAService`와 `fruitJDBCService` 두 개 모두 implements(구현)했다. 
2. JPARepository인터페이스를 작성하고 쿼리를 작성해준다.

# 회고
- JPA문자열? 만으로 구현하는 것은 아직 무리인 것 같고, 또한 직관적이지도 않은 것 같다. 복잡한 것은 SQL Query로 작성하는게 편했다. 
- 찾아보니 단순한 SQL Query가 아닌, JPQL로 작성되어 여러 DB와 호환된다고 한다! 마음껏 써야지!
- Java가 초보라 null과의 싸움이 재밌다.

# 코드
### FruitJPARepository
- 기존에 jdbc를 구현했던게, 쿼리를 두번 날리고 합치는 식이라 시간이 많이 걸릴 것 같다. 

```java
public interface FruitJPARepository extends JpaRepository<Fruit, Long> {
        // JDBC -> JPA
        @Query("SELECT SUM(f.price) FROM Fruit f WHERE f.name = :name AND f.isSale = :isSale")
        Optional<Long> sumPriceByNameAndIsSale(@Param("name") String name, boolean isSale);

        // 문제 2번
        Long countByName(String name);

        // 문제 3번
        @Query("SELECT f FROM Fruit f WHERE f.isSale = TRUE AND f.price > :price")
        List<Fruit> findByPriceGTEAndIsSaleIsTrue(@Param("price") long price);

        // 문제 3번
        @Query("SELECT f FROM Fruit f WHERE f.isSale = TRUE AND f.price < :price")
        List<Fruit> findByPriceLTEAndIsSaleIsTrue(@Param("price") long price);
}
```

### FruitJPAService
- 아래가 3번 문제인데, 선언적으로 작성하고 싶었는데, 직관적인 예시를 못찾아서 아직 변경 못했다..
```java
// 3번 문제
@Override
public List<FruitResponse> isSaleByPriceFruit(String option, long price) {
    List<Fruit> fruits;

    if(option.equals("GTE")){
        fruits = fruitJPARepository.findByPriceGTEAndIsSaleIsTrue(price);
    } else if (option.equals("LTE")) {
        fruits = fruitJPARepository.findByPriceLTEAndIsSaleIsTrue(price);
    } else {
        throw new IllegalArgumentException("잘못된 옵션이 사용되었습니다.");
    }

    return fruits.stream().map(FruitResponse::new).collect(Collectors.toList());
}
```
FruitJPAService 전체 코드
```java
@Service
@Primary
public class FruitJPAService implements FruitService {
    private final FruitJPARepository fruitJPARepository ;

    public FruitJPAService(FruitJPARepository fruitJPARepository) {
        this.fruitJPARepository = fruitJPARepository;
    }

    // 기존 구현
    public void saveFruit(FruitRequest request){
        fruitJPARepository.save(new Fruit(request.getName(), request.getWarehousingDate(), request.getPrice()));
    }

    // 기존 구현
    public List<FruitResponse> getFruits(){
        return fruitJPARepository.findAll().stream().map(FruitResponse::new).collect(Collectors.toList());
    }

    // 기존 구현
    public void sellFruit(long id){
        Fruit fruit = fruitJPARepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        fruit.updateIsSale(false);
        fruitJPARepository.save(fruit);
    }

    // 기존 구현
    public Map<String, Long> findFruit(String name){
        long salesAmount = fruitJPARepository.sumPriceByNameAndIsSale(name, true).orElse(0L);
        long notSalesAmount = fruitJPARepository.sumPriceByNameAndIsSale(name, false).orElse(0L);
        Map<String, Long> response = new HashMap<>();
        response.put("salesAmount", salesAmount);
        response.put("notSalesAmount", notSalesAmount);

        return response;
    }

    // 2번 문제
    public Map<String, Long> countFruit(String name){
        Map<String, Long> response = new HashMap<>();
        response.put("count", fruitJPARepository.countByName(name));
        return response;
    }

    // 3번 문제
    @Override
    public List<FruitResponse> isSaleByPriceFruit(String option, long price) {
        List<Fruit> fruits;

        if(option.equals("GTE")){
            fruits = fruitJPARepository.findByPriceGTEAndIsSaleIsTrue(price);
        } else if (option.equals("LTE")) {
            fruits = fruitJPARepository.findByPriceLTEAndIsSaleIsTrue(price);
        } else {
            throw new IllegalArgumentException("잘못된 옵션이 사용되었습니다.");
        }

        return fruits.stream().map(FruitResponse::new).collect(Collectors.toList());
    }
}
```
