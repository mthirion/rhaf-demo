package com.redhat.demo;

import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.logging.Log;

@ApplicationScoped
public class EventConsumer {

    private static TreeMap<Long, Boolean> check = new TreeMap<>();
    private static Long last = 1L;
    private static Long duplicated = 0L;

    @Incoming("event")
    public synchronized void consume(ConsumerRecord<Long, String> consumerRecord) {
        Long key = consumerRecord.key(); // Can be `null` if the incoming record has no key
        String content = consumerRecord.value();

        // reset message
        if (key == 1) {
            last = 1L;
            duplicated = 0L;
            check.clear();
        }

        for (long i = last; i < key; i++) {
            check.put(i, false);
        }

        if (key < last && !check.containsKey(key))
            duplicated++;

        if (key >= last)
            last = key + 1L;

        check.remove(key);

        Log.infof("Key: %d, message: %s", key, content);
    }
}
