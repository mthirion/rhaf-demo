package com.redhat.demo;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.redhat.demo.avro.schema.Message;

@ApplicationScoped
public class EventConsumer {

    private static TreeMap<Long, Boolean> check = new TreeMap<>();
    private static Long last = 1L;
    private static Long duplicated = 0L;
    
    @Incoming("event")
    public void consume(ConsumerRecord<Long, Message> record) {
        Long key = record.key(); // Can be `null` if the incoming record has no key
        Message m = (Message)record.value();
        String orderdate = m.getOrderdate();
        String quantity = m.getQuantity();
        String pref = m.getProductid();
        String cref = m.getConsumerid();

        String content = "on " + orderdate + " : " + quantity + " products (id = " + pref + ")";

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

        if (key >= last) last=key+1L;

        check.remove(key);


        //System.out.println(String.format("Current Key: %d, Missing messages: %d, Duplicated msg: %d", key, check.size(), duplicated));
        System.out.println(String.format("Key: %d, avro message: %s", key, content) );
    }
}
