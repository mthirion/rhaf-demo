package com.redhat.demo;

import java.time.Duration;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
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

    @Inject
    Tracer tracer;

    @Outgoing("event-out")
    public Multi<KafkaRecord<Long, String>> generate() {
        return Multi.createFrom().ticks().every(Duration.ofMillis(tickFrequency))
                .map(x -> {
                    KafkaRecord<Long, String> kafkaRecord;
                    TracingMetadata tracingMetadata = TracingMetadata.withCurrent(Context.current());

                    Span span = tracer.spanBuilder("generate").startSpan();
                    try (Scope scope = span.makeCurrent()) {

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

                        kafkaRecord = KafkaRecord.of(lastKey, "demo message " + lastKey);

                        doFinancialStuff();
                    } finally {
                        span.end();
                    }
                    return OutgoingKafkaRecord.from(kafkaRecord.addMetadata(tracingMetadata.withSpan(span)));
                });
    }

    public void doFinancialStuff() {
        UUID uuid = UUID.randomUUID();

        Span span = tracer.spanBuilder("financial")
                .setAttribute("financial.reference", uuid.toString())
                .setSpanKind(SpanKind.INTERNAL)
                .startSpan();
        try (Scope scope = span.makeCurrent()) {
            // in a real scenario, here we can put the financial logic stuff
        } finally {
            span.end();
        }
    }
}
