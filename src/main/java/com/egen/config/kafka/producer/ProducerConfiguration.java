package com.egen.config.kafka.producer;

import com.egen.dto.OrderDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.client-id}")
    private String clientId;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.retries}")
    private String retries;

    @Value("${spring.kafka.producer.batch-size}")
    private String batchSize;

    @Value("${spring.kafka.producer.buffer-memory}")
    private String bufferMemory;

    @Value("${spring.kafka.producer.linger-ms}")
    private String lingerMs;

    @Value("${spring.kafka.producer.enable-Idempotence}")
    private String enableIdempotence;

    @Value("${spring.kafka.producer.transactionalId}")
    private String transactionalId;


    /**
     *
     * Setting Producer properties
     * Create Producer Instance
     *
     * */
    @Bean
    public ProducerFactory<String, OrderDto> producerFactoryJson() throws UnknownHostException {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId + "_" + InetAddress.getLocalHost().getHostName() + "json");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        configProps.put(ProducerConfig.ACKS_CONFIG, acks);
        configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId);

        DefaultKafkaProducerFactory<String, OrderDto> factory = new DefaultKafkaProducerFactory<>(configProps);
        if (transactionalId != null) {
            factory.setTransactionIdPrefix(transactionalId + "_" + InetAddress.getLocalHost().getHostName());
            factory.setProducerPerConsumerPartition(false);
        }

        return factory;
    }

    @Bean
    public KafkaTemplate<String, OrderDto> kafkaTemplateJson() throws UnknownHostException {
        return new KafkaTemplate<>(producerFactoryJson());
    }
}
