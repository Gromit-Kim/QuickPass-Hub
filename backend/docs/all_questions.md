- Kotlin의 기본 클래스는 final이다.
- 즉 아무 표시 하지 않으면 상속이 불가능하다.
- open 키워드를 붙이면 해당 클래스로 상속이 가능하다.
- Spring Boot + JPA(Hibernate)에서 Entity는 proxy 객체로 감싸질 수 있어야 한다.
  - 프록시는 상속을 활용해서 동작하므로 Entity 클래스는 open 이여야 한다.
- Kotlin의 val는 read-only로 immutable 하다.
- Kotlin의 var는  mutable하다.
- @Enumerated 어노테이션은 JPA에서 enum 타입을 DB 컬럼에 매핑할 때 사용한다.
  - 기본값은 EnumType.ORDINAL이다. 이는 enum 순서를 숫자로 저장하는 것이다.
  - 실무에서는 EnumType.STRING을 권장한다. 이름 그대로 저장되어야 가독성이 좋고, 안전하다?
- 1:N 관계의 경우 N 쪽(자식테이블)이 FK를 가지므로 JoinColumn이 자식 쪽에 붙는다.
  - mapped by 는 부모에 붙는다?
- cascade는 부모 엔티티의 작업이 자식 엔티티에도 전이(cascade)되도록 설정하는 옵션이다.
  - PERSIST
  - MERGE
  - REMOVE
  - REFRESH
  - DETACH
- orphanRemoval = true
  - 부모와 관계가 끊어진(고아 객체) 자식 엔티티를 자동으로 삭제하는 옵션이다.\
  - 즉, question.choices.remove(choice)하면 해당 choice가 DB에서도 DELETE된다.
  - cascade=REMOVE는 부모를 삭제하면 자식도 삭제되는 것이다.
  - orphanRemoval=true인 것은 부모와 자식의 관계가 해제되면 자식도 삭제되는 것이다.
- fetch=FetchType.LAZY
  - 연관 관계 엔티티를 언제 가져올지 조회 전략을 지정하는 것이다.
  - LAZY의 경우는 지연 로딩으로 1:N 관계에서 기본값이다.
  - 부모를 DB에서 먼저 조회하고, 자식이 실제로 접근될 때 추가로 SQL을 실행한다.
  - 이는 N+1 문제의 원인이 되기도 하지만, 성능 최적화에 유리하다.
- FetchType.EAGER(즉시로딩)
  - 부모를 조회할 때 자식도 전부 JOIn해서 가져온다.
  - 무조건 JOIN 이발생해서 불필요한 데이터가 많으면 성ㄴ능이 저하된다.
- 실무에선 LAZY를 기본으로 두고, 필요한 경우 fetch joinj이나 entity graph로 최적화함.

## N+1 문제란
```KOTLIN
val exams = examsRepository.findAll() // 1번 쿼리
for(exam in exams){ // exam 마다 1번씩 n번의 쿼리
    println(exam.questions.size)
}
```
특정 엔티티를 조회할 때 관련 엔티티를 LAZY 로딩으로 가져오면서 쿼리가 반복적으로 실행됨

해결 방법은 1) Fetch JOIN과 2) EntityGraph 3)Batch Size  조정
```kotlin
@Query("SELECT e FROM ExamsModel e JOIN FETCh e.questions")
fun findAllWithQuestions(): List<>
```


## @Transactional
- 원자성: 메서드 안에 여러 JPA 연산이 있으면 전부 성공하거나 롤백시킨다.
- 일관성/격리성: 같은 트랜잭션 안에서 영속성 컨텍스트 1개가 유지되며, 조회 > 수정 > 저장 흐름이 안정적
- 플러시/커밋 제어: 커밋 시점에 hibernate가 ㄱ자동 flush()를 수행한다. 이에 따라 DB 반영, 중간 실패 시 전체 롤백 수행한다.
- 지연로딩 보장: 지연로딩 필드에 접근할 때 트랜잭션이 열려 있어야 LazyInitializationException을 피한다.
- 명확한 경계: 비즈니스 유즈케이스하나 = 트랜잭션 하나가 유지되므로 보수 및 테스트가 쉬워진다.
- readOnly = true를 적어놓으면 hibnerate가 dirty check 최적화(쓰기 작업 비활성)으로 약간의 비용을 절감한ㄷㅏ.
- 