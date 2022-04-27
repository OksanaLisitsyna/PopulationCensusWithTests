import java.util.*;
import java.util.stream.Collectors;

public class PopulationCensus {

    public static void main(String[] args) {
        Collection<Person> persons = createNewPopulation(10_000_000);

        System.out.println("Всего населения: " + persons.size());
        System.out.println("Количество несовершеннолетних: " + countOfUnderAge(persons));
        System.out.println("Количество призывников: " + familiesOfConscripts(persons).size());
        System.out.println("Количество потенциально работоспособных людей: " + workablePeople(persons).size());
    }


    //создать население
    public static Collection<Person> createNewPopulation(long populationCount) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        if (populationCount <= 0) {
            throw new RuntimeException("Неверно указан \"populationCount\" (должно быть число больше 0)");
        } else {
            for (int i = 0; i < populationCount; i++) {
                persons.add(new Person(
                        names.get(new Random().nextInt(names.size())),
                        families.get(new Random().nextInt(families.size())),
                        new Random().nextInt(100),
                        Sex.values()[new Random().nextInt(Sex.values().length)],
                        Education.values()[new Random().nextInt(Education.values().length)])
                );
            }
        }
        return persons;
    }

    //количество несовершеннолетних
    public static long countOfUnderAge(Collection<Person> persons) {
        return persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
    }

    //отсортированный по фамилии список
    //потенциально работоспособных людей с высшим образованием
    public static List<Person> workablePeople(Collection<Person> persons) {
        return persons.stream()
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> (person.getAge() <= 65 && person.getSex().equals(Sex.MAN)) || (person.getAge() <= 60 && person.getSex().equals(Sex.WOMAN)))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }

    //список фамилий призывников
    public static List<String> familiesOfConscripts(Collection<Person> persons) {
        return persons.stream()
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
    }
}


