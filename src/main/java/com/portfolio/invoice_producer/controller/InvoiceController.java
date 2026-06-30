package com.portfolio.invoice_producer.controller;

import com.portfolio.invoice_producer.dto.InvoiceDTO;
import com.portfolio.invoice_producer.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*") // Libera para o navegador mandar dados sem bloqueio
public class InvoiceController {

    private final InvoiceService invoiceService;

    // O Spring Boot injeta o nosso Service automaticamente aqui
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Endpoint 1: Recebe uma única Nota Fiscal em JSON bruto (Caixa de texto do Front)
    @PostMapping("/single")
    public ResponseEntity<String> receiveSingleInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        invoiceService.sendInvoice(invoiceDTO);
        // TODO: Enviar para o serviço do Kafka
        return ResponseEntity.accepted().body("Nota Fiscal recebida com sucesso e enviada para processamento!");
    }

    // Endpoint 2: Recebe um lote (Lista) de Notas Fiscais de uma vez
    @PostMapping("/batch")
    public ResponseEntity<String> receiveBatchInvoices(@RequestBody List<InvoiceDTO> invoices) {
        // TODO: Enviar o lote para o serviço do Kafka em loop
        invoiceService.sendInvoiceBatch(invoices);
        return ResponseEntity.accepted().body("Lote de " + invoices.size() + " notas recebido com sucesso!");
    }
}