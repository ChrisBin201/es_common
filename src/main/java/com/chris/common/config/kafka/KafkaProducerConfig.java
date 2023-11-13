package com.chris.common.config.kafka;

import com.chris.common.config.kafka.serializer.ProductInfoSerializer;
import com.chris.common.constant.MessageEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.DelegatingByTopicSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers:#{'192.168.193.120:9092'}}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        String valueSerializers = String.format("""
                ^%s.*: %s
                """,
                MessageEvent.PRODUCT_TOPIC, ProductInfoSerializer.class.getName()
        );

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
        props.put(DelegatingByTopicSerializer.VALUE_SERIALIZATION_TOPIC_CONFIG,
                valueSerializers
                );
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(),
                null,
                new DelegatingByTopicSerializer(
                        Map.of(
                                Pattern.compile("^"+MessageEvent.PRODUCT_TOPIC+".*"), new ProductInfoSerializer()
                        ),
                        new StringSerializer())
                );
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
