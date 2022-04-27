import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PopulationCensusTest {

    //проверяем, что метод createNewPopulation возвращает то количество населения, которое нам нужно
    @Test
    public void createNewPopulationSizeTest() {
        long a = 5_000_000, expextedPopulationSize = 5_000_000;

        long actualPopulationSize = PopulationCensus.createNewPopulation(a).size();

        Assertions.assertEquals(expextedPopulationSize, actualPopulationSize);
    }


    //проверяем что метод createNewPopulation выбрасывает Exception,
    //если пытаемся создать население ноль или отрицательное
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    public void populationZeroOrNegativeTest(long a) {
        Assertions.assertThrows(RuntimeException.class, () -> PopulationCensus.createNewPopulation(a));
    }


    //проверяем количество несовершеннолетних, используя другой способ работы с коллекцией
    @Test
    public void countOfUnderAgeTest() {
        long expextedCount, actualCount;

        Collection<Person> persons = PopulationCensus.createNewPopulation(100);
        actualCount = PopulationCensus.countOfUnderAge(persons);

        expextedCount = 0;
        for (Person person : persons) {
            if (person.getAge() < 18) expextedCount++;
        }

        Assertions.assertEquals(expextedCount, actualCount);
    }


    //Hamcrest
    //проверим что метод workablePeople возвращает людей только с высшим образованием и нужного возраста:
    // мужчин от 18 до 65 и женщин от 18 до 60
    @Test
    public void workablePeopleTest() {
        Collection<Person> persons = PopulationCensus.createNewPopulation(100);
        List<Person> actualList = PopulationCensus.workablePeople(persons);
        //проверим, что все person в списке имеют высшее образование
        assertThat(actualList, everyItem(hasProperty("education", equalTo(Education.HIGHER))));
        //проверим, что все person в списке старше 18 лет
        assertThat(actualList, everyItem(hasProperty("age", greaterThanOrEqualTo(18))));
        // проверим, что список не содержит мужчин старше 65
        assertThat(actualList, not(hasItem(allOf(hasProperty("sex", equalTo(Sex.MAN)), hasProperty("age", greaterThan(65))))));
        // проверим, что список не содержит женщин старше 60
        assertThat(actualList, not(hasItem(allOf(hasProperty("sex", equalTo(Sex.WOMAN)), hasProperty("age", greaterThan(60))))));
    }

    //проверим, что метод createNewPopulation не создает null Person
    @Test
    public void createNewPopulationHasNotNullsTest() {
        Collection<Person> persons = PopulationCensus.createNewPopulation(100);
        assertThat(persons, everyItem(not(equalTo(null))));
    }
}