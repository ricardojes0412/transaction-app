package com.transaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.entity.Transaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionEvents {

    @Autowired
    private ITransactionService service;
    private Logger log = LoggerFactory.getLogger(TransactionEvents.class);

    @Autowired
    private ObjectMapper objectMapper;

    public void processTransactionEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
        Transaction event = objectMapper.readValue(consumerRecord.value(), Transaction.class);
        log.info("transactionEvent : {}", event.getIdInvoice());
        service.save(event);
    }

}

