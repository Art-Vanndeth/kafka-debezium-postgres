package dev.dcoder.debeziumdemo.config;

import dev.dcoder.debeziumdemo.model.Person;
import dev.dcoder.debeziumdemo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonChangeListener {

    private final PersonRepository db1PersonRepository;
    private final PersonRepository db2PersonRepository;

    // Listener for DB1 changes
    @KafkaListener(topics = "db1.public.person", groupId = "sync-group")
    public void handleDb1Changes(String message) {
        // Parse the message (Debezium message contains old and new values)
        Person person = parseDebeziumMessage(message);

        // Update corresponding record in db2
        db2PersonRepository.save(person);
    }

    // Listener for DB2 changes
    @KafkaListener(topics = "db2.public.person", groupId = "sync-group")
    public void handleDb2Changes(String message) {
        // Parse the message
        Person person = parseDebeziumMessage(message);

        // Update corresponding record in db1
        db1PersonRepository.save(person);
    }

    private Person parseDebeziumMessage(String message) {
        // Implement logic to parse Debezium JSON message into Person entity
        return new Person();
    }
}

