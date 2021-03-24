package uk.co.huntersix.spring.rest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RestController
public class PersonController {

  private PersonDataService personDataService;

  public PersonController(@Autowired PersonDataService personDataService) {
    this.personDataService = personDataService;
  }

  @GetMapping("/person/{lastName}/{firstName}")
  public ResponseEntity<Person> person(@PathVariable(value = "lastName") String lastName,
      @PathVariable(value = "firstName") String firstName) {
    return personDataService.findPerson(lastName, firstName)
        .map(resp -> ResponseEntity.ok().body(resp))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found!"));
  }

  @GetMapping("/personBySurname/{lastName}")
  public ResponseEntity<List<Person>> personBySurname(
      @PathVariable(value = "lastName") String lastName) {
    List<Person> result = personDataService.findPersonBySurname(lastName);
    if (result.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with lastname!");
    } else {
      return ResponseEntity.ok(result);
    }
  }

  @PostMapping("/personAdd/{lastName}/{firstName}")
  public ResponseEntity<Person> personBySurname(@PathVariable(value = "lastName") String lastName,
      @PathVariable(value = "firstName") String firstName) {
    return personDataService.addPerson(lastName, firstName);
  }



}