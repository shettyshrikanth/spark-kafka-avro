This is a small example to demostrate how to publish Avro objects to a Kafka cluster and read those avro objects using Apache Spark using spark streaming.

LogLine class (Avro Object) generated from LongLine.avsc schema using Avro's code generation utility.

To build this project:

    $ cd LogLineEventProcessor
    $ mvn clean package
    
Class Summary  
	
	LogLine.java - Avro generated class, represents a Avro message
	LogLineSerializer.java - Custom serializer for serializing avro objects to byte[]
	LogLineDeserializer.java - Custom deserializer for deserializing byte[] to avro object
	EventGenerator.java - LogLine avro message/object generator
	LogLineEventProducer.java - Message publisher to publish avro messages to kafka cluster.
	SparkAvroConsumer.java - Message Consumer to read the message from kafka cluster to deserialise and print them.
    
Quickstart
-----------

Before running the examples, make sure that Zookeeper and Kafka are running. In what follows, 
we assume that Zookeeper and Kafka started with the default settings.
Refer kafka quickstart guide for more info - https://kafka.apache.org/quickstart


    # Start Zookeeper
    $ bin/zookeeper-server-start config/zookeeper.properties

    # Start Kafka
    $ bin/kafka-server-start config/server.properties

Then create a topic called clicks:

    # Create clicks topic
    $ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic clicks

Then now run spark consumer
	
	# Run the consumer from IDE
	Right click on com.spark.kafka.avro.consumer.SparkAvroConsumer class and select Run As Java Application

	# Run the consumer from terminal
	$ java -cp target/uber-LogLineEventProcessor-1.0-SNAPSHOT.jar com.spark.kafka.avro.consumer.SparkAvroConsumer 
	

Then run the producer to push LogLine avro messages to kafka cluster(by default producer produce 100 events,
if you need more events then pass the number as command line argument to the producer):-

	# Run the producer from IDE
	Right click on com.kafka.avro.producer.LogLineEventProducer class and select Run As Java Application

	# Run the from terminal
	$ java -cp target/uber-LogLineEventProcessor-1.0-SNAPSHOT.jar com.kafka.avro.producer.LogLineEventProducer 
    
