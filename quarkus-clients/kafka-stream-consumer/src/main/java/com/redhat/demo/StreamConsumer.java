package com.redhat.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.BranchedKStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.Arrays;

@ApplicationScoped
public class StreamConsumer {

    @ConfigProperty(name = "kstream.source") String source;
    @ConfigProperty(name = "kstream.sink") String sink;

    @Produces 
    public Topology streamConsume() {
        StreamsBuilder b = new StreamsBuilder();

        KStream<String,String> stream = b.stream(source);
        //KStream<String,String> stream = b.stream(source, Consumed.with(Serdes.String(), Serdes.String());

        //stream.map( (key, value) -> {return new KeyValue<>(key,value);} );
        stream.mapValues( value -> value );
        //stream.flatMap( (key,value) -> {return new ArrayList<KeyValue<String,String>>();});
        stream.flatMapValues( value -> Arrays.asList(value.split(",")) );

        stream.selectKey((key,value) -> value );
        //BranchedKStream<String, String> branches = stream.split();
        

        KGroupedStream groups = stream.groupByKey();
        KTable<String,Long> result = groups.count();

        result.toStream().to(sink);

        //.aggregate()
        //.toStream()
        //.to()

        return b.build();
    }

    /*
    @Produces 
    public Topology tableConsume() {
        KTable<String,String> table = b.table(source);
        GlobalKTable<String,String> global = b.globalTable(source);
    }
    */
    
}
