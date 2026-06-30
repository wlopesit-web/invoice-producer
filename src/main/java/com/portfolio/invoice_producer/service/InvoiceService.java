package com.portfolio.invoice_producer.service;

import com.portfolio.invoice_producer.dto.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    // Nome do tópico (fila) que criaremos no Kafka
    private static final String TOPIC_NAME = "fila-faturamento";

    // Injeção da ferramenta do Spring para envio de mensagens
    private final KafkaTemplate<String, InvoiceDTO> kafkaTemplate;

    public InvoiceService(KafkaTemplate<String, InvoiceDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Método para enviar uma única nota fiscal
    public void sendInvoice(InvoiceDTO invoiceDTO) {
        log.info("Despachando Nota Fiscal nº {} para a fila do Kafka", invoiceDTO.getNumeroNota());

        // Envia para o Kafka usando o número da nota como chave (garante a ordem de processamento)
        this.kafkaTemplate.send(TOPIC_NAME, invoiceDTO.getNumeroNota(), invoiceDTO);
    }

    // Método para processar e enviar o lote de notas fiscais em loop
    public void sendInvoiceBatch(List<InvoiceDTO> invoices) {
        log.info("Iniciando o envio em lote de {} Notas Fiscais para o Kafka", invoices.size());

        for (InvoiceDTO invoice : invoices) {
            this.sendInvoice(invoice);
        }

        log.info("Todos os eventos do lote foram publicados com sucesso!");
    }
}