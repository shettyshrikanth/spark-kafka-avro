package com.spark.kafka.avro.consumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import com.data.avro.LogLine;
import com.data.avro.LogLineDeserializer;


/**
 * Created by Shrikanth Shetty on 28/10/2018.
 */
public final class SparkAvroConsumer {

	public static void main(String[] args) throws Exception {

		JavaSparkContext jsc = new JavaSparkContext("local", "SparkAvroConsumer", System.getenv("SPARK_HOME"),
				JavaSparkContext.jarOfClass(SparkAvroConsumer.class));

		JavaStreamingContext ssc = new JavaStreamingContext(jsc, Durations.seconds(1));
		
		Map<String, Object> props = new HashMap<>();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		props.put("group.id", "clicks");
		props.put("auto.offset.reset", "latest");
		props.put("enable.auto.commit", true);		
		
		JavaInputDStream<ConsumerRecord<String,byte[]>> createDirectStream = KafkaUtils.createDirectStream(ssc, 
				LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, byte[]>Subscribe(Arrays.asList("clicks"), props));
		
		createDirectStream.foreachRDD(rdd -> rdd.foreach(record -> {
			LogLine eventMessage = new LogLineDeserializer().DeserializeEvent(record.value());
			System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(),
					eventMessage);
		}));
		
		ssc.start();
		ssc.awaitTermination();
	}
}
