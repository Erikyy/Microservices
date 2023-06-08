package ee.erik.usersvc.service;

import ee.erik.shared.transaction.dto.CreateTransactionDto;
import ee.erik.shared.user.dto.*;
import ee.erik.usersvc.dto.WithSecretDto;
import ee.erik.usersvc.entity.Account;
import ee.erik.usersvc.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository repository;
    private final KafkaTemplate<String, CreateTransactionDto> kafkaTemplate;

    public AccountDto findById(FindAccountDto account) {
        return this.findUserIfExistis(account, acc -> acc).toDto();
    }

    public void create(CreateAccountDto account) {
        this.repository.save(Account.fromDto(account));
    }

    public void update(UpdateAccountDto account) {
        this.findUserIfExistis(account.getId(), acc -> {
            if (Objects.equals(acc.getSecret(), account.getSecret())) {
                this.repository.save(Account.fromDto(account));
            } else {
                log.error("Unauthorized");
            }
        });
    }

    public void delete(DeleteAccountDto account) {
        this.findUserIfExistis(account.getId(), acc -> {
            if (Objects.equals(acc.getSecret(), account.getSecret())) {
                this.repository.deleteById(account.getId());
            } else {
                log.error("Unauthorized");
            }
        });
    }

    public void changeBalance(CreateTransactionDto transaction) {
        var from = this.repository.findById(transaction.getAccountFromRefId());
        var to = this.repository.findById(transaction.getAccountToRefId());

        if (from.isPresent() && to.isPresent()) {
            var _from = from.get();
            var _to = to.get();

            var fromBalance = _from.getBalance();
            var toBalance = _to.getBalance();

            fromBalance = fromBalance - transaction.getAmount();
            toBalance = toBalance + transaction.getAmount();

            _from.setBalance(fromBalance);
            _to.setBalance(toBalance);
            this.repository.saveAll(List.of(_from, _to));
        } else {
            log.error("Failed to change accounts balance");
        }
    }

    public void makeTransaction(WithSecretDto<CreateTransactionDto> transaction) {
        var data = transaction.getData();
        var secret = transaction.getSecret();

        this.findUserIfExistis(data.getAccountFromRefId(), acc -> {
            if (Objects.equals(acc.getSecret(), secret)) {
                this.findUserIfExistis(data.getAccountToRefId(), acc2 -> {
                    this.kafkaTemplate.send("transaction", data);
                });
            } else {
                log.error("Unauthorized");
            }
        });
    }

    private void findUserIfExistis(UUID id, Consumer<Account> f) {
        var acc = this.repository.findById(id);
        if (acc.isPresent()) {
            f.accept(acc.get());
        } else {
            log.error("User {} not found", id);
        }
    }

    private Account findUserIfExistis(FindAccountDto accountDto, Function<Account, Account> f) {
        var acc = this.repository.findById(accountDto.getId());
        if (acc.isPresent()) {
            return f.apply(acc.get());
        } else {
            log.error("User {} not found", accountDto.getId());
            return null;
        }
    }

}
