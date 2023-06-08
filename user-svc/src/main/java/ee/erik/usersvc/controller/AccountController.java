package ee.erik.usersvc.controller;

import ee.erik.shared.transaction.dto.CreateTransactionDto;
import ee.erik.shared.user.dto.*;
import ee.erik.usersvc.dto.WithSecretDto;
import ee.erik.usersvc.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/makeTransaction")
    public void makeTransaction(@RequestBody() WithSecretDto<CreateTransactionDto> transaction) {
        this.accountService.makeTransaction(transaction);
    }

    @PostMapping("/createAccount")
    public void createAccount(@RequestBody CreateAccountDto createAccountDto) {
        this.accountService.create(createAccountDto);
    }

    @PutMapping("/updateAccount")
    public void updateAccount(@RequestBody UpdateAccountDto updateAccountDto) {
        this.accountService.update(updateAccountDto);
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(@RequestBody DeleteAccountDto deleteAccountDto) {
        this.accountService.delete(deleteAccountDto);
    }

    @GetMapping("/account")
    public AccountDto getAccount(@RequestParam(name = "id") String id, @RequestParam(name = "secret") String password) {
        return this.accountService.findById(new FindAccountDto(UUID.fromString(id), password));
    }
}
