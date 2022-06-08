## PopulationCensusWithTests

В программе "Численность населения" отрабатывался навык работы с модульными тестами с помощью средств библиотеки **JUnit Jupiter**.</br></br>
Работоспособность кода проверялась обычными тестами (аннотация `@Test`), а также параметризированным (`@ParameterizedTest`).</br>
Для сложных проверок использовались дополнительные возможности **Hamcrest**. Например:
```
// проверим, что список не содержит женщин старше 60
assertThat(actualList, not(hasItem(allOf(hasProperty("sex", equalTo(Sex.WOMAN)), hasProperty("age", greaterThan(60))))));
```
