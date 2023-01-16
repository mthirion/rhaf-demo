package com.redhat.demo;

import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecordMetadata;


@ApplicationScoped
public class KafkaProducer {

    private static Long lastKey=0L;
    
    @ConfigProperty(name = "producer.tick-frequency", defaultValue="1000") 
    private Long tickFrequency;

    @Outgoing("event-out")
    public Multi<KafkaRecord<Long,String>> generate() {
        return Multi.createFrom().ticks().every(Duration.ofMillis(tickFrequency))
            .map(x -> {
                // workaround to avoid other events to surpass the first
                if(lastKey == 1)
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                lastKey++;
                System.out.println("Generating message key: "+lastKey);

                KafkaRecord record = KafkaRecord.of(lastKey, "demo message "+lastKey);

                return record;
            });
    }
}
