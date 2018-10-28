package com.kafka.avro.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.data.avro.LogLine;
import com.data.avro.LogLineSerializer;
/**
 * Created by Shrikanth Shetty on 28/10/2018.
 */
public class LogLineEventProducer {

	public static void main(String[] args)  {
		long events = 100L;		
		
		if (args.length > 0) {
			events = Long.parseLong(args[0]);			
		}
		
		System.out.println(String.format("Pushing %d LogLine events to kafka cluster..", events));

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

		String topic = "clicks";

		Producer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
		try {

			LogLineSerializer serializer = new LogLineSerializer();

			for (long nEvents = 0; nEvents < events; nEvents++) {
				LogLine event = EventGenerator.getNext();

				ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(topic,
						event.getIp().toString(), serializer.serializeMessage(event));
				System.out.println("Sending Message.."+event.getIp());
				producer.send(record).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
}
