package ee.erik.shared.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDto {
    private Double amount;
    private UUID accountFromRefId;
    private int currencyIdRef;
    private UUID accountToRefId;
    private TransactionTypeDto type;
}
