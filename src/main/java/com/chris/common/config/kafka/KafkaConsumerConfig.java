package com.chris.common.config.kafka;

import com.chris.common.config.kafka.deserializer.ProductInfoDeserializer;
import com.chris.common.constant.MessageEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.DelegatingByTopicDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers:#{'192.168.193.120:9092'}}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        String valueDeserializers = String.format("""
                ^%s.*: %s
                """,
                MessageEvent.PRODUCT_TOPIC, ProductInfoDeserializer.class.getName()
        );

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");
        props.put(DelegatingByTopicDeserializer.VALUE_SERIALIZATION_TOPIC_CONFIG,
                valueDeserializers
        );
        return props;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                null,
                new DelegatingByTopicDeserializer(
                        Map.of(
                                Pattern.compile("^"+MessageEvent.PRODUCT_TOPIC+".*"), new ProductInfoDeserializer()
                        ),
                        new JsonDeserializer<>())
        );
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
