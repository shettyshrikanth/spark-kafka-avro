This is a small example demostrating how to produce specific Avro objects to Kafka cluster and read those avro objects using Apache Spark.

Specific Avro classes mean that we use Avro's code generation to generate the LogLine object, then send those objects to kafka cluster as a byte array.

To build this project:

    $ cd LogLineEventProcessor
    $ mvn clean package
    
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
    
