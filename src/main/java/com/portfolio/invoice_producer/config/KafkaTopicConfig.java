package com.portfolio.invoice_producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // Cria automaticamente o tópico no Kafka local se ele não existir
    @Bean
    public NewTopic invoiceTopic() {
        return TopicBuilder.name("fila-faturamento")
                .partitions(1)
                .replicas(1)
                .build();
    }
}