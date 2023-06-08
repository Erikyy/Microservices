package ee.erik.transactionsvc.listeners;

import ee.erik.shared.transaction.dto.CreateTransactionDto;
import ee.erik.transactionsvc.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionListener {
    private final TransactionService service;

    @KafkaListener(groupId = "transaction-group", topics = "transaction")
    public void getTransaction(CreateTransactionDto transaction) {
        this.service.saveTransaction(transaction);
    }
}
