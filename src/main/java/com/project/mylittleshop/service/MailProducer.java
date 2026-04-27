package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.MailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailProducer.class);
    
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.queue.name}")
    private String queueName;
    
    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void publish(MailDTO mailDTO) {
        LOGGER.info("Published mail to {} ", mailDTO.email());
        rabbitTemplate.convertAndSend(queueName, mailDTO);
    }
}

