package uk.co.huntersix.spring.rest.referencedata;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

@Service
public class PersonDataService {

  public static final List<Person> PERSON_DATA = Arrays.asList(
      new Person("Mary", "Smith"),
      new Person("Brian", "Archer"),
      new Person("Collin", "Brown")
  );

  public Optional<Person> findPerson(String lastName, String firstName) {
    return PERSON_DATA.stream()
        .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
            && p.getLastName().equalsIgnoreCase(lastName))
        .findAny();
  }

  public List<Person> findPersonBySurname(String lastName) {
    return PERSON_DATA.stream().filter(p -> p.getLastName().equalsIgnoreCase(lastName))
        .collect(Collectors.toList());
  }

  public ResponseEntity<Person> addPerson(String lastName, String firstName) {
    Optional<Person> person = PERSON_DATA.stream().filter(
        p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName()
            .equalsIgnoreCase(lastName))
        .findAny();

    if (!person.isPresent()) {
      Person newPerson = new Person(firstName, lastName);
      PERSON_DATA.add(newPerson);
      return new ResponseEntity<Person>(newPerson, HttpStatus.OK);
    } else {
      return new ResponseEntity<Person>(person.get(), HttpStatus.CONFLICT);

    }
  }


}
