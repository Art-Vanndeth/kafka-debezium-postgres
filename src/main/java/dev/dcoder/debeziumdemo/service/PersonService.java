package dev.dcoder.debeziumdemo.service;

import dev.dcoder.debeziumdemo.model.Person;
import dev.dcoder.debeziumdemo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person personDetails) {
        return personRepository.findById(id).map(person -> {
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            return personRepository.save(person);
        }).orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}

