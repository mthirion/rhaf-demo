package com.redhat.demo;

import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.opentelemetry.context.Context;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.TracingMetadata;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecord;

@ApplicationScoped
public class KafkaProducer {

    private static Long lastKey = 0L;
    private static final Object lock = new Object();

    @ConfigProperty(name = "producer.tick-frequency", defaultValue = "1000")
    private Long tickFrequency;

    @Outgoing("event-out")
    public Multi<KafkaRecord<Long, String>> generate() {
        return Multi.createFrom().ticks().every(Duration.ofMillis(tickFrequency))
                .map(x -> {
                    // workaround to avoid other events to surpass the first
                    if (lastKey == 1)
                        try {
                            Thread.sleep(3000L);
                        } catch (InterruptedException e) {
                            Log.warn("Thread Interrupted", e);
                            Thread.currentThread().interrupt();
                        }
                    synchronized (lock) {
                        lastKey++;
                    }

                    Log.infof("Generating message key: %s", lastKey);

                    KafkaRecord<Long, String> kafkaRecord = KafkaRecord.of(lastKey, "demo message " + lastKey);
                    TracingMetadata tracingMetadata = TracingMetadata.withCurrent(Context.current());

                    return OutgoingKafkaRecord.from(kafkaRecord.addMetadata(tracingMetadata));
                });
    }
}