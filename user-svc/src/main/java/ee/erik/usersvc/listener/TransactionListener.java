package ee.erik.usersvc.listener;

import ee.erik.shared.transaction.dto.CreateTransactionDto;
import ee.erik.usersvc.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionListener {
    private final AccountService accountService;


    @KafkaListener(groupId = "transaction-group", topics = "transaction-complete")
    public void onComplete(CreateTransactionDto transaction) {
        this.accountService.changeBalance(transaction);
    }
}
