package ee.erik.transactionsvc.service;

import ee.erik.shared.transaction.dto.CreateTransactionDto;
import ee.erik.transactionsvc.entity.Transaction;
import ee.erik.transactionsvc.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, CreateTransactionDto> kafkaTemplate;

    public void saveTransaction(CreateTransactionDto transaction) {
        log.info("Recievied new transaction {} from {}", transaction.getType(), transaction.getAccountFromRefId());
        var t = Transaction.fromDto(transaction);
        t.setDatetime(Timestamp.from(Instant.now()));
        var res = this.kafkaTemplate.send("transaction-complete", transaction);
        res.whenComplete((stringCreateTransactionDtoSendResult, throwable) -> {
            t.setSignature(DigestUtils.shaHex(transaction.getAccountFromRefId().toString()));
            t.setCompleted(Timestamp.from(Instant.now()));
            this.transactionRepository.save(t);
        });
    }

}
