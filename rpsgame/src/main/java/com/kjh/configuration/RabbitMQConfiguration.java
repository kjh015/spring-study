package com.kjh.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
	@Bean
	TopicExchange rpsExchange(@Value("${spring.rabbitmq.template.exchange}") final String exchangeName) {
		return new TopicExchange(exchangeName);
	}

	// JSON 메시지 컨버터를 가지는 RabbitTemplate 생성
	@Bean
	RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());	//java 객체를 json으로 시리얼라이징. 받는 쪽에서는 디시리얼라이징 해야함. 다른 언어의 객체를 json으로 주고받을 수 있음
		return rabbitTemplate;
	}

	// Java 객체를 JSON으로 직렬화
	@Bean
	Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
