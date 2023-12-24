package Social.Media.Project.NotificationService.service.kafka;

import Social.Media.Project.NotificationService.config.WebSocketEventListener;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NotificationKafkaConsumer {

//    private static final Logger logger = LoggerFactory.getLogger(NotificationKafkaConsumer.class);
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @PostConstruct
//    public void init() {
//        logger.info("Initializing Kafka Consumer Service...");
//        new Thread(this::pollKafka).start();
//    }
//
//    private void pollKafka() {
//        logger.info("Setting up Kafka consumer properties...");
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//
//        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
//            logger.info("Subscribing to the topic 'albendemu_notifications'...");
//            consumer.subscribe(Pattern.compile("albendemu_notifications"));
//
//            while (true) {
//                logger.info("Polling for messages...");
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//                for (var record : records) {
//                    logger.info("Received message: Key = {}, Value = {}", record.key(), record.value());
//                }
//            }
//        } catch (Exception e) {
//            logger.error("An error occurred in the Kafka consumer: ", e);
//        }
//    }

    private static final Logger logger = LoggerFactory.getLogger(NotificationKafkaConsumer.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final WebSocketEventListener webSocketEventListener;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @PostConstruct
    public void init() {
        // Start a fixed number of consumer threads
        int numberOfThreads = 2; // Adjust the number of threads as needed
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(this::pollKafka).start();
        }
    }

    private void pollKafka() {
        logger.info("Setting up Kafka consumer properties...");
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "true");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Pattern.compile(".*_notifications"));

            while (true) {
//                logger.info("Polling for messages...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Received message: Key = {}, Value = {}", record.key(), record.value());
                    processRecord(record, consumer);
                }
            }
        }
    }

    private void processRecord(ConsumerRecord<String, String> record, Consumer<String, String> consumer) {
        try {
            JsonNode jsonNode = objectMapper.readTree(record.value());
            String userId = jsonNode.get("toUserEmail").asText();

            if (isUserOnline(userId)) {
                sendMessageToUser(userId, record.value());
                TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(record.offset() + 1)));
            }
        } catch (Exception e) {
            logger.error("Error processing record: ", e);
        }
    }

    public boolean isUserOnline(String userId) {
        return webSocketEventListener.isUserOnline(userId);
    }

    public void sendMessageToUser(String userId, String message) {
        if (isUserOnline(userId)) {
            logger.info("Sending message to user: {} with message: {}", userId, message);
            simpMessagingTemplate.convertAndSendToUser(userId, "/topic", message);
        } else {
            logger.info("User not online: {}", userId);
        }
    }
}
