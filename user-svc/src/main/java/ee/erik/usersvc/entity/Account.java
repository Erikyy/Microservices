package ee.erik.usersvc.entity;

import ee.erik.shared.user.dto.AccountDto;
import ee.erik.shared.user.dto.CreateAccountDto;
import ee.erik.shared.user.dto.UpdateAccountDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Double balance;
    private String secret;

    public static Account fromDto(UpdateAccountDto account) {
        Account acc = new Account();
        acc.setId(account.getId());
        return getAccount(acc, account.getName(), account.getEmail(), account.getBalance(), account.getSecret(), account.getFullName(), account.getPhoneNumber());
    }

    public static Account fromDto(CreateAccountDto account) {
        Account acc = new Account();
        return getAccount(acc, account.getName(), account.getEmail(), account.getBalance(), account.getSecret(), account.getFullName(), account.getPhoneNumber());
    }

    public AccountDto toDto() {
        return new AccountDto(
                this.getId(),
                this.getName(),
                this.getFullName(),
                this.getPhoneNumber(),
                this.getEmail(),
                this.getBalance()
        );
    }

    private static Account getAccount(Account acc, String name, String email, Double balance, String secret, String fullName, String phoneNumber) {
        acc.setName(name);
        acc.setEmail(email);
        acc.setBalance(balance);
        acc.setSecret(secret);
        acc.setFullName(fullName);
        acc.setPhoneNumber(phoneNumber);
        return acc;
    }
}
