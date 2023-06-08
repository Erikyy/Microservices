package ee.erik.transactionsvc.entity;

import ee.erik.shared.transaction.dto.CreateTransactionDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "b_transaction")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "account_from_ref_id")
    private UUID accountFromRefId;
    @Column(name = "signature")
    private String signature;
    @Column(name = "currency_id_ref")
    private int currencyIdRef;
    @Column(name = "account_to_ref_id")
    private UUID accountToRefId;
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    @Column(name = "datetime")
    private Timestamp datetime;
    @Column(name = "completed")
    private Timestamp completed;

    public static Transaction fromDto(CreateTransactionDto transaction) {
        Transaction t = new Transaction();
        t.setAmount(transaction.getAmount());
        t.setAccountFromRefId(transaction.getAccountFromRefId());
        t.setAccountToRefId(transaction.getAccountToRefId());

        switch (transaction.getType()) {
            case Payment -> {
                t.setTransactionType(TransactionType.Payment);
            }
            case Transfer -> {
                t.setTransactionType(TransactionType.Transfer);
            }
        }

        return t;
    }
}
